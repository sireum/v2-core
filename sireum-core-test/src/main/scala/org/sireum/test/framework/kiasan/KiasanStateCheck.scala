/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.framework.kiasan

import org.sireum.kiasan.state._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.topi._
import org.sireum.util._
import org.sireum.kiasan.extension.KiasanIntegerExtension
import org.sireum.kiasan.extension.KiasanBooleanExtension

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanStateCheck {

  def check[S <: KiasanStatePart[S]](topi : Topi, s : S) =
    topi.check(s.pathConditions)

  def concretizeState[S <: KiasanStatePart[S]](
    topi : Topi, s : S,
    before : Option[RewriteFunction] = None,
    after : Option[RewriteFunction] = None) = {
    val mOpt = topi.getModel(s.pathConditions)
    mOpt.map { m =>
      import Rewriter._
      (before, after) match {
        case (Some(f), Some(g)) =>
          (m, buildEnd[S](all(f, topi.stateRewriter(m)), g)(s))
        case (Some(f), None) =>
          (m, build[S](all(f, topi.stateRewriter(m)))(s))
        case (None, Some(g)) =>
          (m, buildEnd[S](topi.stateRewriter(m), g)(s))
        case _ =>
          (m, build[S](topi.stateRewriter(m))(s))
      }
    }
  }

  def checkConcretizedState[S <: KiasanStatePart[S]](
    topi : Topi, s : S, s0 : S, trueValue : Value,
    evaluator : ExpEvaluator[S, ISeq[(S, Value)]]) : (IMap[String, Value], S) = {
    val mcsOpt = concretizeState(topi, s)
    assert(mcsOpt.isDefined)
    val mcs = mcsOpt.get
    checkConcretizedState(topi, s, s0, trueValue, evaluator, mcs)
    mcs
  }

  def checkConcretizedState[S <: KiasanStatePart[S]](
    topi : Topi, s : S, s0 : S, trueValue : Value,
    evaluator : ExpEvaluator[S, ISeq[(S, Value)]],
    mcs : (IMap[String, Value], S)) {
    val (m, cs) = mcs
    for (pc <- cs.pathConditions)
      evaluator.evalExp(s0, pc).foreach {
        re => assert(second2(re) == trueValue)
      }
  }

  def checkConcExe[S <: KiasanStatePart[S]](
    topi : Topi, s : S, s0 : S, trueValue : Value,
    evaluator : ExpEvaluator[S, ISeq[(S, Value)]], exp : String, v : Value,
    rewriter : Exp => Exp, mcsOpt : Option[(IMap[String, Value], S)] = None) =
    check(topi, s) match {
      case TopiResult.SAT =>
        val (m, cs) =
          mcsOpt match {
            case Some(mcs) =>
              checkConcretizedState(topi, s, s0, trueValue, evaluator, mcs)
              mcs
            case _ =>
              checkConcretizedState(topi, s, s0, trueValue, evaluator)
          }
        val getValue : Value --> Value = {
          case KiasanIntegerExtension.KI(num) if m.contains("ii!" + num) => m("ii!" + num)
          case KiasanBooleanExtension.KB(num) if m.contains("b!" + num) => m("b!" + num)
          case v => v
        }
        val rwr = Rewriter.build[Exp]({
          case ValueExp(v) => ValueExp(getValue(v))
        })
        val e = rwr(pc(rewriter, exp)(0))
        val result = evaluator.evalExp(s0, e)
        assert(result.size == 1)
        result.foreach { re => assert(second2(re) == getValue(v)) }
        TopiResult.SAT
      case TopiResult.UNKNOWN =>
        TopiResult.UNKNOWN
      case tr =>
        tr
    }

  def pc(rewriter : Exp => Exp, exp : String*) : Seq[Exp] = exp.map { source =>
    import org.sireum.test.framework.TestUtil._

    val (eOpt, errors) = parse[Exp](Left(source))

    assert(errors == "", "Expecting no parse error, but found:\n" + errors)

    rewriter(eOpt.get)
  }
}