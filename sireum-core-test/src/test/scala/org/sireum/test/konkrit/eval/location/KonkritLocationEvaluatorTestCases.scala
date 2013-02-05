/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.konkrit.eval.location

import org.sireum.pilar.state._
import org.sireum.test.framework.pilar.eval._
import org.sireum.test.konkrit.eval._
import org.sireum.test.konkrit.eval.action.KonkritActionEvaluatorTestCases
import org.sireum.pilar.ast.Transformation
import org.sireum.pilar.eval.Transitions
import org.sireum.pilar.ast.LocationDecl

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritLocationEvaluatorTestCases[S <: State[S]] {
  self : EvaluatorTestFramework[S] =>

  case class ST(state : S, location : LocationDecl, transformation : Transformation)

  import language.implicitConversions

  implicit def t2ST(t : (S, LocationDecl, Transformation)) = ST(t._1, t._2, t._3)

  def state : S
  def emptyState : S
  def location(code : String) : EvalConfiguration[Transitions[S]]

  import org.sireum.test.konkrit.eval.KonkritEvaluatorTestUtil._

  Case("AssignX1").
    Evaluating location "# @@x := 1II;" on (state()) gives "x==1" satisfying {
      trans : Transitions[S] =>
        trans.enabled.foreach { t =>
          t.state should equal(state())
        }
        assert(trans.disabled.isEmpty)
    }
}