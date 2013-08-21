/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan

import scala.concurrent.forkjoin._
import scala.collection.parallel._

import org.sireum.pilar.state._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.util._
import org.sireum.kiasan.state._

import com.typesafe.scalalogging.slf4j._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Kiasan {
  type KiasanState[S <: KiasanState[S]] = State[S] with KiasanStatePart[S] with ScheduleRecordingState[S]

  case class TopiCache(state : org.sireum.topi.TopiState,
                       lastCompiledLength : Int) extends Immutable
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanInitialStateProvider[S <: Kiasan.KiasanState[S]] {
  def initialStates : ISeq[S]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanLocationProvider[S <: Kiasan.KiasanState[S]] {
  def location(s : S) : LocationDecl
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanReporter[S <: Kiasan.KiasanState[S]] {
  def foundAssertionViolation(s : S) : S
  def foundEndState(s : S) : S
  def foundDepthBoundExhaustion(s : S) : S
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Kiasan {
  def search
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanBfs[S <: Kiasan.KiasanState[S], R, C] extends Kiasan with Logging {
  import Kiasan._
  import State._

  var dpTime = 0l

  var dpTotalTime = 0l

  def parallelMode : Boolean

  def parallelThreshold : Int

  def parallelismLevel : Int

  def locationProvider : KiasanLocationProvider[S]

  def evaluator : Evaluator[S, R, C, ISeq[S]]

  def initialStatesProvider : KiasanInitialStateProvider[S]

  def reporter : KiasanReporter[S]

  def topi : org.sireum.topi.Topi

  def depthBound : Int

  lazy val taskSupport = new ForkJoinTaskSupport(new ForkJoinPool(parallelismLevel))

  def search {
    var workList : GenSeq[S] = initialStatesProvider.initialStates

    var i = 0
    val depth = depthBound

    var searchTime = System.currentTimeMillis
    while (!workList.isEmpty && i < depth) {
      val ps = inconNextStatesPairs(workList)
      val inconsistencyCheckRequested = ps.exists(first2)
      val nextStates = ps.flatMap(second2)

      workList =
        filterTerminatingStates(inconsistencyCheckRequested, nextStates)

      i += 1
    }

    if (i >= depth) {
      workList.foreach(reporter.foundDepthBoundExhaustion)
    }

    searchTime = System.currentTimeMillis - searchTime

    logger.debug((({ pLevel : String =>
      var parInfo =
        if (parallelMode)
          s"""
Parallelism level: ${pLevel}
Parallel threshold: ${parallelThreshold}"""
        else ""
      s"""
Parallel mode: ${parallelMode}${parInfo}
Depth bound: ${depthBound}
Search time: ${searchTime} ms
DP time: ${dpTime} (${Math.round(dpTime * 100d / searchTime)}%) ms"""
    })(
      if (parallelismLevel >= 2) parallelismLevel.toString
      else "default")))

  }

  @inline
  protected def check(s : S) = {
    val topiCachePropKey = "Topi Cache"
    val tc = s.getPropertyOrElse[TopiCache](topiCachePropKey, TopiCache(topi.newState, 0))
    var pcs = s.pathConditions
    val size = pcs.length - tc.lastCompiledLength
    var conjuncts = List[Exp]()
    var i = 0
    while (i < size) {
      conjuncts = pcs.head :: conjuncts
      pcs = pcs.tail
      i += 1
    }
    val newTc = TopiCache(topi.compile(conjuncts, tc.state), pcs.length)
    val s2 = s.setProperty(topiCachePropKey, newTc)

    (s2, topi.check(newTc.state))
  }

  @inline
  private def par[T](shouldParallize : Boolean, l : GenSeq[T]) =
    if (parallelMode && shouldParallize && parallelThreshold < l.size) {
      val pl = l.par
      if (parallelismLevel >= 2)
        pl.tasksupport = taskSupport
      (pl, true)
    } else (l.seq, false)

  @inline
  private def inconNextStatesPairs(l : GenSeq[S]) =
    first2(par(true, l)).map { s =>
      var inconsistencyCheckRequested = false
      val nextStates =
        for {
          (s2, l, t) <- evaluator.transitions(s, locationProvider.location(s)).enabled
          nextS <- evaluator.evalTransformation(s2, l, t)
        } yield {
          inconsistencyCheckRequested =
            inconsistencyCheckRequested || nextS.inconsistencyCheckRequested
          nextS
        }

      (inconsistencyCheckRequested, nextStates)
    }

  @inline
  private def filterTerminatingStates(inconsistencyCheckRequested : Boolean,
                                      states : GenSeq[S]) = {
    val (gs, isPar) = par(inconsistencyCheckRequested, states)
    val stateTimePairs =
      gs.flatMap { s =>
        var time = 0l
        if (s.callStack.isEmpty) {
          reporter.foundEndState(s)
          None
        } else {
          val s3Opt =
            if (s.inconsistencyCheckRequested) {
              time = System.currentTimeMillis
              val (s2, tr) = check(s)
              time = System.currentTimeMillis - time
              if (tr == org.sireum.topi.TopiResult.UNSAT) None
              else Some(s2.requestInconsistencyCheck(false))
            } else Some(s.requestInconsistencyCheck(false))

          if (s3Opt.isDefined) {
            val s3 = s3Opt.get
            if (s3.assertionViolation.isDefined) {
              reporter.foundAssertionViolation(s3)
              None
            } else {
              Some(s3, time)
            }
          } else None
        }
      }
    if (!stateTimePairs.isEmpty) {
      dpTime += stateTimePairs.map(second2).reduce(
        if (isPar) dpTimeMaxF else dpTimeTotalF
      )
      dpTotalTime += stateTimePairs.map(second2).reduce(dpTimeTotalF)
    }

    stateTimePairs.map(first2)
  }

  def dpTimeMaxF(t1 : Long, t2 : Long) = if (t1 < t2) t2 else t1
  def dpTimeTotalF(t1 : Long, t2 : Long) = t1 + t2
}