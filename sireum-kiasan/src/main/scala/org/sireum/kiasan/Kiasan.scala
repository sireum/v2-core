/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan

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
trait KiasanBfs[S <: Kiasan.KiasanState[S], R] extends Kiasan {
  import State._

  def parallelThreshold : Int

  def locationProvider : KiasanLocationProvider[S]

  def evaluator : Evaluator[S, R, ISeq[S]]

  def initialStatesProvider : KiasanInitialStateProvider[S]

  def reporter : KiasanReporter[S]

  def topi : org.sireum.topi.Topi

  def search {
    var workList : GenSeq[S] = initialStatesProvider.initialStates

    while (!workList.isEmpty) {
      val ps = inconNextStatesPairs(workList)
      val inconsistencyCheckRequested = ps.exists(first2)
      val nextStates = ps.flatMap(second2)

      workList =
        filterTerminatingStates(par(inconsistencyCheckRequested, nextStates))
    }
  }

  protected def checkInconsistent(s : S) : Boolean =
    topi.check(s.pathConditions) == org.sireum.topi.TopiResult.UNSAT

  @inline
  private def par[T](shouldParallize : Boolean, l : GenSeq[T]) =
    if (shouldParallize && parallelThreshold < l.size) l.par else l

  @inline
  private def inconNextStatesPairs(l : GenSeq[S]) =
    par(true, l).map { s =>
      var inconsistencyCheckRequested = false
      var l = ilistEmpty[S]
      for ((s, t) <- evaluator.transitions(s, locationProvider.location(s)).enabled)
        for (nextS <- evaluator.evalTransformation(s, t)) {
          l = nextS :: l
          inconsistencyCheckRequested =
            inconsistencyCheckRequested || s.inconsistencyCheckRequested
        }

      (inconsistencyCheckRequested, l)
    }

  @inline
  private def filterTerminatingStates(l : GenSeq[S]) =
    l.filter { s =>
      if (s.callStack.isEmpty) {
        reporter.foundEndState(s)
        false
      } else if (s.assertionViolation.isDefined) {
        reporter.foundAssertionViolation(s)
        false
      } else
        !checkInconsistent(s)
    }
}