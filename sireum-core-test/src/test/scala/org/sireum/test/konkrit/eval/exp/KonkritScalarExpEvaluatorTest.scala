/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.konkrit.eval.exp

import org.junit.runner._
import org.scalatest.junit._
import scala.util._
import org.sireum.extension._
import org.sireum.konkrit.extension._
import org.sireum.pilar.state._
import org.sireum.util._
import org.sireum.util.math._
import org.sireum.test.framework.pilar.eval._
import org.sireum.test.konkrit.eval._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class KonkritScalarExpEvaluatorTest
    extends ExpEvaluatorTestFramework[BasicState, (BasicState, Value), ISeq[(BasicState, Value)]] {

  type S = BasicState
  type V = Value
  type Re = (S, Value)
  type R = ISeq[Re]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]

  import KonkritEvaluatorTestUtil._

  import language.implicitConversions
  
  implicit def p2rc(p : Re) : Result = RC(p._2)
  implicit def rf2erf(rf : Re => Unit) : R => Unit = { r => r.foreach { rf(_) } }
  implicit def any2is(o : Any) = IsMatcher(o)
  implicit def l2bi(l : Long) = BigInt(l)
  implicit def i2bi(i : Int) = BigInt(i)

  val numOfTests = 100
  val random = new Random()
  val state = BasicState()

  val integerOpInputs = ilist[BigInt](Int.MinValue, Int.MaxValue,
    Long.MinValue, Long.MaxValue, 0, 1, -1)

  // test arith binop corners
  for (op <- ilist("+", "-", "*", "/", "%"))
    for (n1 <- integerOpInputs)
      for (n2 <- integerOpInputs if !(op == "/" || op == "%") || n2 != 0) {
        val result = binopASem(op)(n1, n2)
        val exp = n1 + "ii " + op + " " + n2 + "ii"
        makeTest(exp, result)
      }

  // test integer arith binop from long
  for (op <- ilist("+", "-", "*", "/", "%"))
    for (i <- 0 until numOfTests) {
      val n1 = random.nextLong
      var n2 = random.nextLong
      if (op == "/" || op == "%")
        while (n2 == 0) {
          n2 = random.nextLong
        }
      val result = binopASem(op)(n1, n2)
      val exp = n1 + "ii " + op + " " + n2 + "ii"

      makeTest(exp, result)
    }

  // test integer arith binop from int
  for (op <- ilist("+", "-", "*", "/", "%"))
    for (i <- 0 until numOfTests) {
      val n1 = random.nextInt
      var n2 = random.nextInt
      if (op == "/" || op == "%")
        while (n2 == 0) {
          n2 = random.nextInt
        }
      val result = binopASem(op)(n1, n2)
      val exp = n1 + "ii " + op + " " + n2 + "ii"

      makeTest(exp, result)
    }

  // test integer arith unop corners
  for (op <- ilist("+", "-"))
    for (n <- integerOpInputs) {
      val result = unopASem(op)(n)
      val exp = op + " " + n + "ii"

      makeTest(exp, result)
    }

  // test integer arith unop
  for (op <- ilist("+", "-"))
    for (i <- 0 until numOfTests) {
      val n = random.nextLong
      val result = unopASem(op)(n)
      val exp = op + " " + n + "ii"

      makeTest(exp, result)
    }

  // test integer rel binop
  for (op <- ilist("==", "!=", ">", ">=", "<", "<="))
    for (i <- 0 until numOfTests) {
      val n1 = random.nextLong
      val n2 =
        if (random.nextBoolean && (op == "==" || op == "!=")) n1
        else random.nextLong
      val result = binopRSem(op)(n1, n2)
      val exp = n1 + "ii " + op + " " + n2 + "ii"

      makeTest(exp, result)
    }

  // test int arith binop
  for (op <- ilist("+", "-", "*", "/", "%", "^>>", "^>", "^<"))
    for (i <- 0 until numOfTests) {
      val n1 = random.nextInt
      var n2 = random.nextInt
      if (op == "/" || op == "%")
        while (n2 == 0) {
          n2 = random.nextInt
        }
      val result = binopASemInt(op)(n1, n2)
      val exp = n1 + " " + op + " " + n2

      makeTest(exp, result)
    }

  // test integer arith unop
  for (op <- ilist("+", "-"))
    for (i <- 0 until numOfTests) {
      val n = random.nextInt
      val result = unopASemInt(op)(n)
      val exp = op + " " + n

      makeTest(exp, result)
    }

  // test integer rel binop
  for (op <- ilist("==", "!=", ">", ">=", "<", "<="))
    for (i <- 0 until numOfTests) {
      val n1 = random.nextInt
      val n2 =
        if (random.nextBoolean && (op == "==" || op == "!=")) n1
        else random.nextInt
      val result = binopRSemInt(op)(n1, n2)
      val exp = n1 + " " + op + " " + n2

      makeTest(exp, result)
    }

  // test long arith binop
  for (op <- ilist("+", "-", "*", "/", "%", "^>>", "^>", "^<"))
    for (i <- 0 until numOfTests) {
      val n1 = random.nextLong
      var n2 = random.nextLong
      if (op == "/" || op == "%")
        while (n2 == 0) {
          n2 = random.nextLong
        }
      val result = binopASemLong(op)(n1, n2)
      val exp = n1 + "l " + op + " " + n2 + "l"

      makeTest(exp, result)
    }

  // test long arith unop
  for (op <- ilist("+", "-"))
    for (i <- 0 until numOfTests) {
      val n = random.nextLong
      val result = unopASemLong(op)(n)
      val exp = op + " " + n + "l"

      makeTest(exp, result)
    }

  // test long rel binop
  for (op <- ilist("==", "!=", ">", ">=", "<", "<="))
    for (i <- 0 until numOfTests) {
      val n1 = random.nextLong
      val n2 =
        if (random.nextBoolean && (op == "==" || op == "!=")) n1
        else random.nextLong
      val result = binopRSemLong(op)(n1, n2)
      val exp = n1 + "l " + op + " " + n2 + "l"

      makeTest(exp, result)
    }

  // test boolean binop
  val boolBinopInputs = ilist((true, true), (true, false), (false, true), (false, false))
  for (op <- Seq("&&&", "|||", "===>", "<===", "&&", "||", "==>", "<=="))
    for ((b1, b2) <- boolBinopInputs) {
      val result = binopBoolSem(op)(b1, b2)
      val exp = b1 + " " + op + " " + b2
      makeTest(exp, result)
    }

  // test boolean unop
  for (op <- ilist("!"))
    for (b <- ilist(true, false)) {
      val result = unopBoolSem(op)(b)
      val exp = op + " " + b
      makeTest(exp, result)
    }

  def makeTest(exp : String, result : Any) =
    Evaluating expression exp gives result.toString satisfying { r : Re =>
      import language.reflectiveCalls
      
      r.value is result
    }

  def newExpEvaluator(s : S) =
    if (random.nextBoolean)
      KonkritEvaluatorTestUtil.newEvaluator(None,
        KonkritBooleanExtension,
        KonkritIntegerBExtension,
        KonkritIntBExtension,
        KonkritLongBExtension)
    else
      KonkritEvaluatorTestUtil.newEvaluator(None,
        KonkritBooleanExtension,
        KonkritIntegerIExtension,
        KonkritIntIExtension,
        KonkritLongIExtension)

  def binopASem : String --> ((BigInt, BigInt) => BigInt) = {
    case "+" => { (n1, n2) => n1 + n2 }
    case "-" => { (n1, n2) => n1 - n2 }
    case "*" => { (n1, n2) => n1 * n2 }
    case "/" => { (n1, n2) => n1 / n2 }
    case "%" => { (n1, n2) => n1 % n2 }
  }

  def binopRSem : String --> ((BigInt, BigInt) => Boolean) = {
    case "==" => { (n1, n2) => n1 == n2 }
    case "!=" => { (n1, n2) => n1 != n2 }
    case ">"  => { (n1, n2) => n1 > n2 }
    case ">=" => { (n1, n2) => n1 >= n2 }
    case "<"  => { (n1, n2) => n1 < n2 }
    case "<=" => { (n1, n2) => n1 <= n2 }
  }

  def unopASem : String --> (BigInt => BigInt) = {
    case "-" => { n => -n }
    case "+" => { n => n }
  }

  def binopASemInt : String --> ((Int, Int) => Int) = {
    case "+"   => { (n1, n2) => n1 + n2 }
    case "-"   => { (n1, n2) => n1 - n2 }
    case "*"   => { (n1, n2) => n1 * n2 }
    case "/"   => { (n1, n2) => n1 / n2 }
    case "%"   => { (n1, n2) => n1 % n2 }
    case "^>>" => { (n1, n2) => n1 >>> n2 }
    case "^>"  => { (n1, n2) => n1 >> n2 }
    case "^<"  => { (n1, n2) => n1 << n2 }
  }

  def binopRSemInt : String --> ((Int, Int) => Boolean) = {
    case "==" => { (n1, n2) => n1 == n2 }
    case "!=" => { (n1, n2) => n1 != n2 }
    case ">"  => { (n1, n2) => n1 > n2 }
    case ">=" => { (n1, n2) => n1 >= n2 }
    case "<"  => { (n1, n2) => n1 < n2 }
    case "<=" => { (n1, n2) => n1 <= n2 }
  }

  def unopASemInt : String --> (Int => Int) = {
    case "-" => { n => -n }
    case "+" => { n => n }
  }

  def binopASemLong : String --> ((Long, Long) => Long) = {
    case "+"   => { (n1, n2) => n1 + n2 }
    case "-"   => { (n1, n2) => n1 - n2 }
    case "*"   => { (n1, n2) => n1 * n2 }
    case "/"   => { (n1, n2) => n1 / n2 }
    case "%"   => { (n1, n2) => n1 % n2 }
    case "^>>" => { (n1, n2) => n1 >>> n2 }
    case "^>"  => { (n1, n2) => n1 >> n2 }
    case "^<"  => { (n1, n2) => n1 << n2 }
  }

  def binopRSemLong : String --> ((Long, Long) => Boolean) = {
    case "==" => { (n1, n2) => n1 == n2 }
    case "!=" => { (n1, n2) => n1 != n2 }
    case ">"  => { (n1, n2) => n1 > n2 }
    case ">=" => { (n1, n2) => n1 >= n2 }
    case "<"  => { (n1, n2) => n1 < n2 }
    case "<=" => { (n1, n2) => n1 <= n2 }
  }

  def unopASemLong : String --> (Long => Long) = {
    case "-" => { n => -n }
    case "+" => { n => n }
  }

  def binopBoolSem : String --> ((Boolean, Boolean) => Boolean) = {
    case "&&&" | "&&"   => { (b1, b2) => b1 && b2 }
    case "|||" | "||"   => { (b1, b2) => b1 || b2 }
    case "===>" | "==>" => { (b1, b2) => !b1 || b2 }
    case "<===" | "<==" => { (b1, b2) => b1 || !b2 }
  }

  def unopBoolSem : String --> (Boolean => Boolean) = {
    case "!" => !_
  }

  case class IsMatcher(o : Any) {
    import org.sireum.konkrit.extension.KonkritBooleanExtension._
    import org.sireum.konkrit.extension.KonkritIntegerExtension._
    import org.sireum.konkrit.extension.KonkritIntExtension._
    import org.sireum.konkrit.extension.KonkritLongExtension._
    def is(other : Any) {
      (o, other) match {
        case (kiv : KonkritIntegerValue, bi : BigInt) => kiv.value.toBigInt should equal(bi)
        case (kiv : KonkritIntValue, i : Int)         => kiv.value should equal(i)
        case (klv : KonkritLongValue, l : Long)       => klv.value should equal(l)
        case (kiv : KonkritIntegerValue, b : Boolean) => (kiv.value.toBigInt != 0) should equal(b)
        case (kiv : KonkritIntValue, b : Boolean)     => (kiv.value != 0) should equal(b)
        case (klv : KonkritLongValue, b : Boolean)    => (klv.value != 0) should equal(b)
        case (kbv : KonkritBooleanValue, b : Boolean) => kbv.value should equal(b)
      }
    }
  }
}
