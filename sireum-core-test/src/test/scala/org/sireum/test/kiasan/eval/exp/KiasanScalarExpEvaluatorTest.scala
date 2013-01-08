/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.kiasan.eval.exp

import java.io._
import org.junit.runner._
import org.scalatest.junit._
import org.sireum.kiasan.extension._
import org.sireum.kiasan.extension.KiasanBooleanExtension._
import org.sireum.kiasan.extension.KiasanIntegerExtension._
import org.sireum.kiasan.state._
import org.sireum.konkrit.extension._
import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.test.framework.pilar.eval._
import org.sireum.test.kiasan.eval._
import org.sireum.topi._
import org.sireum.util._
import org.sireum.util.math._
import scala.util.Random

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class KiasanScalarExpEvaluatorTest
    extends ExpEvaluatorTestFramework[BasicKiasanState, (BasicKiasanState, Value), ISeq[(BasicKiasanState, Value)]] {

  type S = BasicKiasanState
  type V = Value
  type Re = (S, Value)
  type R = ISeq[Re]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]

  import language.implicitConversions

  implicit def re2result(p : (S, V)) = ExpEvalResult(p._1, p._2)
  implicit def rf2erf(f : ((S, V)) => Unit) =
    { rs : ISeq[(S, V)] => rs.foreach { f(_) } }
  implicit def any2is(any : Any) = IsMatcher(any)
  implicit def i2ii(n : Int) = SireumNumber(n)

  val extensions = ilist(KiasanIntegerExtension, KonkritIntegerExtension,
    KiasanBooleanExtension, KonkritBooleanExtension)
  val topi = Topi.create(TopiSolver.Z3, TopiMode.Process, 10000, extensions : _*)

  val (state, alpha, beta, gamma, b_alpha, b_beta, b_gamma) = {
    val s = BasicKiasanState()
    val (s2, a) = KiasanIntegerExtension.fresh(s)
    val (s3, b) = KiasanIntegerExtension.fresh(s2)
    val c = KI(3)
    val (s4, ba) = KiasanBooleanExtension.fresh(s3)
    val (s5, bb) = KiasanBooleanExtension.fresh(s4)
    val bc = KB(3)
    (s5, a, b, c, ba, bb, bc)
  }

  val rewriter = Rewriter.build[Exp]({
    case NameExp(NameUser("alpha"))   => ValueExp(alpha)
    case NameExp(NameUser("beta"))    => ValueExp(beta)
    case NameExp(NameUser("gamma"))   => ValueExp(gamma)
    case NameExp(NameUser("b_alpha")) => ValueExp(b_alpha)
    case NameExp(NameUser("b_beta"))  => ValueExp(b_beta)
    case NameExp(NameUser("b_gamma")) => ValueExp(b_gamma)
    case LiteralExp(_, n : BigInt, _) =>
      ValueExp(KonkritIntegerExtension.CI(SireumNumber(n)))
  })

  val numOfTests = 1
  val random = new Random()

  // test integer arith binop
  val integerBinopInputs = ilist(("alpha", "c"), ("c", "alpha"), ("beta", "alpha"))
  for (op <- ilist("+", "-", "*", "/", "%"))
    for ((i1, i2) <- integerBinopInputs) {
      val numOfIts = if (i1 == "c" || i2 == "c") numOfTests else 1
      for (i <- 0 until numOfIts) {
        val s = state
        val (s2, e1) = randomize(false, s)(i1)
        val (s3, e2) = randomize(op == "/" || op == "%", s2)(i2)
        val exp = e1 + " " + op + " " + e2
        makeTest(exp, Some(gamma), gamma.toString, s3)
      }
    }

  // test integer rel binop
  for (op <- ilist("==", "!=", ">", ">=", "<=", "<"))
    for ((i1, i2) <- integerBinopInputs) {
      val numOfIts = if (i1 == "c" || i2 == "c") numOfTests else 1
      for (i <- 0 until numOfIts) {
        val s = state
        val (s2, e1) = randomize(false, s)(i1)
        val (s3, e2) = randomize(false, s2)(i2)
        val exp = e1 + " " + op + " " + e2
        makeTest(exp, None, "true,false", s3)
      }
    }

  // test integer unop
  {
    makeTest("+ beta", Some(beta), beta.toString, state)
    makeTest("- beta", Some(gamma), gamma.toString, state)
  }

  // test boolean binop
  val booleanBinopInputs = ilist(("b_alpha", "true"), ("b_alpha", "false"),
    ("true", "b_alpha"), ("false", "b_alpha"), ("b_beta", "b_alpha"))
  for (
    op <- ilist("==", "!=", "&&", "||", "==>", "<==", "&&&", "|||", "===>",
      "<===")
  ) for ((b1, b2) <- booleanBinopInputs) {
    val s = state
    val exp = b1 + " " + op + " " + b2
    makeTest(exp, None, "true,false", s)
  }

  // test boolean unop
  {
    makeTest("! b_alpha", None, "true,false", state)
  }

  def makeTest(exp : String, result : Option[Any], resultString : String, s : S) =
    Evaluating expression exp gives resultString on s satisfying { r : Re =>
      if (result.isDefined) {
        r.value is result.get
      }
      check(r.state) match {
        case TopiResult.SAT =>
          val (m, cs) = checkConcretizedState(r.state)
          val getValue : Value --> Value = {
            case KI(num) if m.contains("ii!" + num) => m("ii!" + num)
            case KB(num) if m.contains("b!" + num)  => m("b!" + num)
            case v                                  => v
          }
          val rwr = Rewriter.build[Exp]({
            case ValueExp(v) => ValueExp(getValue(v))
          })
          val e = rwr(pc(exp)(0))
          val s = BasicKiasanState()
          val result = newExpEvaluator(s).evalExp(s, e)
          result should have size 1
          result.foreach(_.value is getValue(r.value))
        case TopiResult.UNKNOWN =>
        case tr =>
          assert(false, "Expecting either SAT or UNKNOWN")
      }
    }

  override def expRewriter(exp : Exp) = rewriter(exp)

  def randomize(isNonZero : Boolean, s : S) : String --> (S, String) = {
    case "c" =>
      var l = random.nextLong
      while (isNonZero && l == 0)
        l = random.nextLong
      (s, l.toString + "II")
    case v =>
      if (isNonZero)
        (s.addPathCondition(BinaryExp("!=", ValueExp(alpha),
          ValueExp(KonkritIntegerExtension.CI(0)))), v)
      else (s, v)
  }

  def pc(exp : String*) : Seq[Exp] = exp.map { source =>
    import org.sireum.test.framework.TestUtil._

    val (eOpt, errors) = parse(Left(source), classOf[Exp])

    assert(errors == "", "Expecting no parse error, but found:\n" + errors)

    rewriter(eOpt.get)
  }

  def check(s : S) = topi.check(s.pathConditions.reverse)

  def concretizeState(s : S) = {
    val mOpt = topi.getModel(s.pathConditions.reverse)
    mOpt.map { m => (m, Rewriter.build[S](topi.stateRewriter(m))(s)) }
  }

  def checkConcretizedState(s : S) = {
    val mcsOpt = concretizeState(s)
    assert(mcsOpt.isDefined)
    val (m, cs) = mcsOpt.get
    val is = BasicKiasanState()
    val evaluator = newExpEvaluator(is)
    for (pc <- cs.pathConditions)
      evaluator.evalExp(is, pc).foreach(_.value is true)
    (m, cs)
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  case class IsMatcher(o : Any) {
    def is(other : Any) {
      import KonkritBooleanExtension._
      (o, other) match {
        case (pcs : Seq[Exp] @unchecked, s : String)  => pcs should equal(pc(s))
        case (pcs : Seq[Exp] @unchecked, s : Product) => pcs should equal(pc(toSeq(s) : _*))
        case (TT, b : Boolean)                        => b should equal(true)
        case (FF, b : Boolean)                        => b should equal(false)
        case _                                        => o should equal(other)
      }
    }
  }

  def toSeq(p : Product) : Seq[String] = {
    var l = ilistEmpty[String]
    for (o <- p.productIterator) {
      l = o.toString :: l
    }
    l.reverse
  }

  def newExpEvaluator(s : S) =
    KiasanEvaluatorTestUtil.newEvaluator(
      extensions : _*)

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  case class ExpEvalResult(state : S, value : V)
}