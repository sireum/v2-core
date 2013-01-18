/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
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

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Kiasan {
  type KiasanState[S <: KiasanState[S]] = State[S] with KiasanStatePart[S] with ScheduleRecordingState[S]
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
  def foundAssertionViolation(s : S)
  def foundEndState(s : S)
  def foundDepthBoundExhaustion(s : S)
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
trait KiasanBfs[S <: Kiasan.KiasanState[S], R, C] extends Kiasan {
  import State._
  
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

    while (!workList.isEmpty && i < depth) {
      val ps = inconNextStatesPairs(workList)
      val inconsistencyCheckRequested = ps.exists(first2)
      val nextStates = ps.flatMap(second2)

      workList =
        filterTerminatingStates(par(inconsistencyCheckRequested, nextStates))

      i += 1
    }

    if (i >= depth) {
      workList.foreach(reporter.foundDepthBoundExhaustion)
    }
  }

  case class TopiCache(state : org.sireum.topi.TopiState,
                       lastCompiledLength : Int) extends Immutable

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
      pl
    } else l

  @inline
  private def inconNextStatesPairs(l : GenSeq[S]) =
    par(true, l).map { s =>
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
  private def filterTerminatingStates(gs : GenSeq[S]) =
    gs.flatMap { s =>
      if (s.callStack.isEmpty) {
        reporter.foundEndState(s)
        None
      } else {
        val s3Opt =
          if (s.inconsistencyCheckRequested) {
            val (s2, tr) = check(s)
            if (tr == org.sireum.topi.TopiResult.UNSAT) None
            else Some(s2.requestInconsistencyCheck(false))
          } else Some(s.requestInconsistencyCheck(false))

        if (s3Opt.isDefined) {
          val s3 = s3Opt.get
          if (s3.assertionViolation.isDefined) {
            reporter.foundAssertionViolation(s3)
            None
          } else s3Opt
        } else None
      }
    }
}