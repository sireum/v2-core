/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.kiasan.eval.transformation

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.junit.ShouldMatchersForJUnit
import org.sireum.extension._
import org.sireum.kiasan.extension._
import org.sireum.konkrit.extension._
import org.sireum.kiasan.state._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.util._
import org.sireum.util.math._
import org.sireum.test._
import org.sireum.test.framework.pilar.eval._
import org.sireum.test.konkrit.eval.exp._
import org.sireum.test.kiasan.eval._
import org.sireum.test.konkrit.eval.action.KonkritActionEvaluatorTestCases
import org.sireum.test.konkrit.eval.transformation.KonkritTransformationEvaluatorTestCases
import org.sireum.test.konkrit.eval.jump.KonkritJumpEvaluatorTestCases

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class KiasanTransformationEvaluatorTest
    extends TransformationEvaluatorTestFramework[BasicKiasanState, BasicKiasanState, ISeq[BasicKiasanState]]
    with KonkritActionEvaluatorTestCases[BasicKiasanState, BasicKiasanState, ISeq[BasicKiasanState]]
    with KonkritJumpEvaluatorTestCases[BasicKiasanState, BasicKiasanState, ISeq[BasicKiasanState]]
    with KonkritTransformationEvaluatorTestCases[BasicKiasanState, BasicKiasanState, ISeq[BasicKiasanState]] {

  type S = BasicKiasanState
  type V = Value
  type Re = (BasicKiasanState, Value)
  type R = ISeq[Re]
  type C = ISeq[(S, Boolean)]
  type Se = S
  type SR = ISeq[S]

  import language.implicitConversions
  
  implicit def se2sr(f : Se => Unit) : SR => Unit = { sr : SR =>
    sr.foreach { f(_) }
  }

  implicit def se2s(se : Se) : S = se

  import org.sireum.test.konkrit.eval.KonkritEvaluatorTestUtil._

  def emptyState = BasicKiasanState()

  def state = emptyState.enterCallFrame("proc", Some("init"), 0,
    imapEmpty, None, -1, None)

  def action(code : String) = transformation(code)

  def jump(code : String) = transformation(code)

  def newTransformationEvaluator(s : S) = 
    KiasanEvaluatorTestUtil.newEvaluator(
      None,
      KiasanBooleanExtension,
      KiasanIntegerExtension,
      KonkritVariableAccessExtension,
      KonkritBooleanExtension,
      KonkritIntegerExtension)
}
