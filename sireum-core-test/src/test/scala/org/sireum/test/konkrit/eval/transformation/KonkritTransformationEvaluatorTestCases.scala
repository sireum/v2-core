/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.konkrit.eval.transformation

import org.sireum.pilar.state._
import org.sireum.test.framework.pilar.eval._
import org.sireum.test.konkrit.eval._
import org.sireum.test.konkrit.eval.action.KonkritActionEvaluatorTestCases

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritTransformationEvaluatorTestCases[S <: State[S], Se, SR] {
  self : EvaluatorTestFramework[S] =>

  import language.implicitConversions
    
  implicit def se2s(se : Se) : S
  
  def state : S
  def emptyState : S
  def transformation(code : String) : EvalConfiguration[SR]
}