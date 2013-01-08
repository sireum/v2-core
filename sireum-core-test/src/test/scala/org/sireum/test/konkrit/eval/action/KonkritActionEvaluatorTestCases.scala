/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.konkrit.eval.action

import org.sireum.pilar.state._
import org.sireum.test.framework.pilar.eval._
import org.sireum.test.konkrit.eval._

trait KonkritActionEvaluatorTestCases[S <: State[S], Se, SR] {
  self : EvaluatorTestFramework[S] =>

  def action(code : String) : EvalConfiguration[SR]

  import language.implicitConversions
  
  implicit def se2sr(f : Se => Unit) : SR => Unit
  implicit def se2s(se : Se) : S
  
  def state : S

  import org.sireum.test.konkrit.eval.KonkritEvaluatorTestUtil._

  Case("AssignX1").
    Evaluating action "@@x := 1II;" on (state("@@x" -> 0)) gives "x==1" satisfying {
      s : Se =>
        s("@@x") should equal(n(1))
    }

  Case("AssignXTrue").
    Evaluating action "@@x := true;" on (state("@@x" -> false)) gives "x==1" satisfying {
      s : Se =>
        s("@@x") should equal(b(true))
    }

  Case("AssertTrue").
    Evaluating action "assert true;" on (state()) gives
    "an empty state without assertion violation" satisfying {
      s : Se => s.assertionViolation.isDefined should equal(false)
    }

  Case("AssertFalse").
    Evaluating action "assert false;" on (state()) gives
    "an empty state with assertion violation" satisfying {
      s : Se => s.assertionViolation.isDefined should equal(true)
    }

  Case("AssumeTrue").
    Evaluating action "assume true;" on (state()) gives "unchanged"

  Case("AssertFalse").
    Evaluating action "assume false;" on (state()) gives
    "nothing" satisfying {
      s : Se => assert(false)
    }

}