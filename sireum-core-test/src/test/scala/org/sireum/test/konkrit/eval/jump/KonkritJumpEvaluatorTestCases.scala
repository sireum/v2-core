/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.konkrit.eval.jump

import org.sireum.pilar.state._
import org.sireum.test.framework.pilar.eval._
import org.sireum.test.konkrit.eval._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritJumpEvaluatorTestCases[S <: State[S], Se, SR] {
  self : EvaluatorTestFramework[S] =>

  import language.implicitConversions
  
  implicit def se2sr(f : Se => Unit) : SR => Unit
  implicit def se2s(se : Se) : S
  
  def state : S
  def emptyState : S
  def jump(code : String) : EvalConfiguration[SR]

  import org.sireum.test.konkrit.eval.KonkritEvaluatorTestUtil._

  Case("GotoTarget").
    Evaluating jump "goto target;" on state gives "target location" satisfying {
      s : Se => s.callStack.head.locationUri should equal(Some("target"))
    }

  Case("IfTarget").
    Evaluating jump "if true then goto target;" on state gives
    "target location" satisfying {
      s : Se => s.callStack.head.locationUri should equal(Some("target"))
    }

  Case("IfNext").
    Evaluating jump "if false then goto target;" on state gives
    "next location" satisfying {
      s : Se => s.callStack.head.locationUri should equal(Some("next"))
    }

  Case("IfElseTarget").
    Evaluating jump "if false then goto target else goto target2;" on
    state gives "target2 location" satisfying {
      s : Se => s.callStack.head.locationUri should equal(Some("target2"))
    }

  Case("IfTarget2").
    Evaluating jump """if false then goto target 
                        else if true then goto target2 
                        else goto target3;""" on
    state gives "target2 location" satisfying {
      s : Se => s.callStack.head.locationUri should equal(Some("target2"))
    }

  Case("SwitchTarget").
    Evaluating jump """switch true 
                       | true => goto target;""" on state gives
    "target location" satisfying {
      s : Se => s.callStack.head.locationUri should equal(Some("target"))
    }

  Case("SwitchNext").
    Evaluating jump """switch false 
                       | true => goto target;""" on state gives
    "next location" satisfying {
      s : Se => s.callStack.head.locationUri should equal(Some("next"))
    }

  Case("SwitchTarget2").
    Evaluating jump """switch false 
                       | true => goto target
                       | false => goto target2;""" on state gives
    "target2 location" satisfying {
      s : Se => s.callStack.head.locationUri should equal(Some("target2"))
    }

  //  Case("CallFoo").
  //    Evaluating jump """call foo 0II;""" on (state("foo" -> 1)) gives
  //    "target2 location" satisfying {
  //      s : Se => 
  //    }

  Case("ReturnEmpty").
    Evaluating jump "return;" on state gives "empty state" satisfying {
      s : Se => s should equal(emptyState)
    }

}