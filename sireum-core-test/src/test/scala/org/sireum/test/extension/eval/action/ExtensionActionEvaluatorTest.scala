/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.extension.eval.action

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.sireum.test.framework.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.extension._
import org.sireum.konkrit.extension._
import org.sireum.util._
import org.sireum.test.konkrit.eval._
import org.sireum.pilar.eval._
import org.sireum.extension._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class ExtensionActionEvaluatorTest
    extends ActionEvaluatorTestFramework[BasicState, BasicState, ISeq[BasicState]] {

  type S = BasicState
  type V = Value
  type Re = (S, Value)
  type R = ISeq[Re]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]

  import language.implicitConversions

  implicit def se2sr(se : S => Unit) : SR => Unit = { sr =>
    for (s <- sr)
      se(s)
  }

  import KonkritEvaluatorTestUtil._

  Evaluating action "nop();" gives "Nothing"

  def state = BasicState()

  def newActionEvaluator(s : S) =
    KonkritEvaluatorTestUtil.newEvaluator[S](None,
      TestExtensionImpl,
      UriValueExtensionImpl,
      KonkritVariableAccessExtension,
      KonkritIntegerExtension)
}

object TestExtensionImpl extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new TestExtensionImpl(config)
}

final class TestExtensionImpl[S <: State[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {
  import KonkritEvaluatorTestUtil._

  type V = Value
  type R = ISeq[(S, Value)]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]

  def uriPath = "org/sireum/test/extension/action/Test"

  @ActionExt
  @TopLevel
  def nop : S --> SR = {
    case s => ivector(s)
  }
}
