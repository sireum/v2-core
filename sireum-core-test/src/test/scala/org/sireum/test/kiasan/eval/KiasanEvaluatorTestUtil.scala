/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.kiasan.eval

import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.kiasan.Kiasan._
import org.sireum.kiasan.state._
import org.sireum.kiasan.extension._
import org.sireum.konkrit.extension._
import org.sireum.util._
import org.sireum.pilar.eval._
import org.sireum.extension._
import org.sireum.pilar.symbol._
import org.sireum.test.util.pilar.eval.PilarEvalUtil._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanEvaluatorTestUtil {
  type KResult[S <: KiasanState[S]] = {
    def state : S
    def value : Value
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  case class RK[Self <: KiasanState[Self]](state : Self, value : Value)

  def kb(n : Int) = KiasanBooleanExtension.KB(n)
  def ki(n : Int) = KiasanIntegerExtension.KI(n)
  def kb(c : Char) = KiasanBooleanExtension.KB(c - 'i' + 1)
  def ki(c : Char) = KiasanIntegerExtension.KI(c - 'i' + 1)

  def exp(e : Exp) : Exp = Rewriter.build({
    case TupleExp(List(e)) => e
    case CallExp(NameExp(NameUser("kb")), LiteralExp(_, i : Int, _)) =>
      ValueExp(kb(i))
    case CallExp(NameExp(NameUser("ki")), LiteralExp(_, i : Int, _)) =>
      ValueExp(ki(i))
    case CallExp(NameExp(NameUser("kb")), NameExp(NameUser(name))) =>
      assert(name.length == 1)
      ValueExp(kb(name.charAt(0)))
    case CallExp(NameExp(NameUser("ki")), NameExp(NameUser(name))) =>
      assert(name.length == 1)
      ValueExp(ki(name.charAt(0)))
  }, Rewriter.TraversalMode.BOTTOM_UP)(e)

  def exp(source : String) : Exp = {
    import org.sireum.test.framework.TestUtil._

    val (eOpt, errors) = parse(Left(source), classOf[Exp])

    assert(errors == "", "Expecting no parse error, but found:\n" + errors)

    exp(eOpt.get)
  }

  def newConfig[S <: State[S]](st : Option[SymbolTable],
                               extCompanions : ExtensionCompanion*) =
    new EvaluatorConfigurationImpl[S](st, new EvaluatorImpl,
      new KiasanSemanticsExtensionModule[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {
        val sei = new KiasanSemanticsExtensionInitImpl[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {}
        val miners = ivector(KiasanExtensionMiner.mine[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] _,
          ExtensionMiner.mine[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] _)
      },
      extCompanions : _*)

  def newConfig[S <: State[S]](extCompanions : ExtensionCompanion*) =
    new EvaluatorConfigurationImpl[S](None, new EvaluatorImpl,
      new KiasanSemanticsExtensionModule[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {
        val sei = new KiasanSemanticsExtensionInitImpl[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {}
        val miners = ivector(KiasanExtensionMiner.mine[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] _,
          ExtensionMiner.mine[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] _)
      },
      extCompanions : _*)

  def newEvaluator[S <: State[S]](
    extCompanions : ExtensionCompanion*) =
    newConfig[S](None, extCompanions : _*).evaluator.mainEvaluator

  def newEvaluator[S <: State[S]](
    st : Option[SymbolTable],
    extCompanions : ExtensionCompanion*) =
    newConfig[S](st, extCompanions : _*).evaluator.mainEvaluator
}