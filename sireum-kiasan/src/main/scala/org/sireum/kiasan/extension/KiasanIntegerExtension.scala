/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan.extension

import org.sireum.extension._
import org.sireum.extension.IntegerExtension._
import org.sireum.kiasan.state._
import org.sireum.kiasan.extension._
import org.sireum.kiasan.extension.KiasanExtension._
import org.sireum.konkrit.extension._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.topi.process._
import org.sireum.topi.annotation._
import org.sireum.util._
import org.sireum.util.math._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanIntegerValue extends IntegerValue with KiasanValue

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanIntegerExtension extends ExtensionCompanion {
  def create[S <: KiasanStatePart[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KiasanIntegerBExtension(config)

  private type Op = String
  private type V = Value
  private type CV = KonkritIntegerValue
  private type KV = KiasanIntegerValue
  private type KS[S <: KS[S]] = KiasanStatePart[S]

  import language.implicitConversions

  @inline
  private implicit def t2s[T](t : T) = ivector(t)

  @inline
  protected final implicit def v2e(v : Value) : Exp = ValueExp(v)

  @inline
  def cast[S] : (S, V, ResourceUri) --> ISeq[(S, V)] = {
    case (s, v : KV, IntegerExtension.Type)       => (s, v)
    case (s, v : KV, KiasanIntegerExtension.Type) => (s, v)
  }

  @inline
  def fresh[S <: KiasanStatePart[S]](s : S) = {
    val (s2, num) = s.fresh(KiasanIntegerExtension.Type)
    (s2, KI(num))
  }

  @inline
  def fresh[S <: KS[S]] : (S, ResourceUri) --> (S, V) = {
    case (s, IntegerExtension.Type)       => fresh(s)
    case (s, KiasanIntegerExtension.Type) => fresh(s)
  }

  @inline
  def binopAEval[S <: KS[S]] : (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, v : CV, opA : String, w : KV) => binopAHelper(s, v, opA, w)
    case (s, v : KV, opA : String, w : CV) => binopAHelper(s, v, opA, w)
    case (s, v : KV, opA : String, w : KV) => binopAHelper(s, v, opA, w)
  }

  @inline
  def binopAHelper[S <: KS[S]](s : S, v : V, opA : Op, w : V) : ISeq[(S, V)] = {
    import KiasanState.PathCondition._
    val (s2, a) = fresh(s, KiasanIntegerExtension.Type)
    (s2 + BinaryExp("==", a, BinaryExp(opA, v, w)), a)
  }

  @inline
  def binopREval[S <: KS[S]](
    implicit b2v : Boolean => V) : (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, v : CV, opR : String, w : KV) => binopRHelper(b2v, s, v, opR, w)
    case (s, v : KV, opR : String, w : CV) => binopRHelper(b2v, s, v, opR, w)
    case (s, v : KV, opR : String, w : KV) => binopRHelper(b2v, s, v, opR, w)
  }

  @inline
  def binopRHelper[S <: KS[S]](
    b2v : Boolean => V, s : S, v : V, opR : Op, w : V) : ISeq[(S, Value)] = {
    import KiasanState.PathCondition._
    ivector(
      (s +? BinaryExp(opR, v, w), b2v(true)),
      (s +? BinaryExp(comp(opR), v, w), b2v(false)))
  }

  @inline
  def unopAEval[S <: KS[S]] : (S, Op, V) --> ISeq[(S, V)] = {
    case (s, opA, v : KV) => unopAHelper(s, opA, v)
  }

  @inline
  def unopAHelper[S <: KS[S]](s : S, opA : Op, v : V) : ISeq[(S, V)] = {
    opA match {
      case "-" =>
        import KiasanState.PathCondition._
        val (s2, a) = fresh(s, KiasanIntegerExtension.Type)
        (s2 + BinaryExp("==", a, UnaryExp(opA, v)), a)
      case "+" => (s, v)
    }
  }

  @inline
  def cond[S <: KS[S]] : (S, V) --> ISeq[(S, Boolean)] = {
    case (s, v : KV) =>
      import KiasanState.PathCondition._
      val w = KonkritIntegerExtension.CI(SireumNumber(0))
      ivector(
        (s +? BinaryExp("==", v, w), true),
        (s +? BinaryExp("!=", v, w), false))
  }

  val comp =
    Map("==" -> "!=",
      "!=" -> "==",
      ">" -> "<=",
      ">=" -> "<",
      "<" -> ">=",
      "<=" -> ">")

  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class KI(num : Int) extends KiasanIntegerValue {
    val typeUri = KiasanIntegerExtension.Type
  }

  @BackEnd(value = "Z3", mode = "Process")
  def z3BackEndPart = new TopiProcess.BackEndPart {

    class Context(val sb : StringBuilder, var tc : TopiProcess.TypeCounters)

    val lineSep = System.getProperty("line.separator")

    @inline
    implicit def i2s(i : Int) = i.toString

    @inline
    def println(ss : String*)(implicit ctx : Context) {
      for (s <- ss)
        ctx.sb.append(s)
      ctx.sb.append(lineSep)
    }

    @inline
    def declareConst(num : Int)(implicit ctx : Context) {
      val lastNum = ctx.tc.getOrElse(KiasanIntegerExtension.Type, -1)
      if (num > lastNum) {
        for (i <- lastNum + 1 to num)
          println("(declare-const ii!", i, " Int)")
        ctx.tc = ctx.tc + (KiasanIntegerExtension.Type -> num)
      }
    }

    @inline
    def declareEConst(e : Exp)(implicit ctx : Context) {
      e match {
        case ValueExp(KI(num)) => declareConst(num)
        case _                 =>
      }
    }

    @inline
    def un(freshNum : Int, n : String)(implicit ctx : Context) {
      declareConst(freshNum)
      println("(assert (= ii!", freshNum, " (- ", n, ")))")
    }

    @inline
    def bin(freshNum : Int, n : String,
            m : String, op : String)(implicit ctx : Context) {
      declareConst(freshNum)
      println("(assert (= ii!", freshNum, " (", op, " ", n, " ", m, ")))")
    }

    @inline
    def sbin(v1 : String, v2 : String, op : String)(implicit ctx : Context) = {
      println("(assert (", op, " ", v1, " ", v2, "))")
    }

    @inline
    def nsbin(v1 : String, v2 : String, op : String)(implicit ctx : Context) = {
      println("(assert (not (", op, " ", v1, " ", v2, ")))")
    }

    @inline
    implicit def v2s : Exp --> String = {
      case ValueExp(c : IsInteger) =>
        val n = c.asInteger
        if (n < 0)
          "(- " + (-n).toBigInt + ")"
        else
          n.toBigInt.toString
      case ValueExp(KI(num)) =>
        "ii!" + num
    }

    def expTranslator = {
      case (tc, BinaryExp("==", ValueExp(KI(n1)), BinaryExp("+", BinaryExp("-", ValueExp(KI(n2)), ValueExp(KI(n3))), n))) =>
        implicit val ctx = new Context(new StringBuilder, tc)
        declareConst(n1)
        declareConst(n2)
        declareConst(n3)
        println(s"(assert (= ii!$n1 (+ (- ii!$n2 ii!$n3) ${v2s(n)})))")
        (ctx.tc, ctx.sb.toString)
      case (tc, BinaryExp("==", ValueExp(KI(freshNum)), BinaryExp(op, n, m))) //
      if (v2s isDefinedAt n) && (v2s isDefinedAt m) && (op == "+" || op == "-" || op == "*" || op == "/" || op == "%") =>
        implicit val ctx = new Context(new StringBuilder, tc)
        declareEConst(n)
        declareEConst(m)
        op match {
          case "+" => bin(freshNum, n, m, "+")
          case "-" => bin(freshNum, n, m, "-")
          case "*" => bin(freshNum, n, m, "*")
          case "/" => bin(freshNum, n, m, "div")
          case "%" => bin(freshNum, n, m, "rem")
        }
        (ctx.tc, ctx.sb.toString)

      case (tc, BinaryExp(op, n, m)) if (v2s isDefinedAt n) && (v2s isDefinedAt m) &&
        (op == "==" || op == "!=" || op == "<" || op == "<=" || op == ">" || op == ">=") =>
        implicit val ctx = new Context(new StringBuilder, tc)
        declareEConst(n)
        declareEConst(m)
        op match {
          case "==" => sbin(n, m, "=")
          case "!=" => nsbin(n, m, "=")
          case ">"  => sbin(n, m, ">")
          case ">=" => sbin(n, m, ">=")
          case "<"  => sbin(n, m, "<")
          case "<=" => sbin(n, m, "<=")
        }
        (ctx.tc, ctx.sb.toString)

      case (tc, BinaryExp("==", ValueExp(KI(freshNum)), UnaryExp("-", n))) =>
        implicit val ctx = new Context(new StringBuilder, tc)
        declareEConst(n)
        un(freshNum, n)
        (ctx.tc, ctx.sb.toString)
    }

    def stateRewriter(m : IMap[String, Value]) = {
      case v @ KI(num) => m.getOrElse("ii!" + num, v)
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanIntegerExtension[S <: KiasanStatePart[S]]
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  val uriPath = UriUtil.classUri(this)

  @Cast
  val cast = KiasanIntegerExtension.cast

  @FreshKiasanValueProvider
  val fresh = KiasanIntegerExtension.fresh

  @Binaries(Array("+", "-", "*", "/", "%"))
  val binopAEval = KiasanIntegerExtension.binopAEval

  @Binaries(Array("==", "!=", ">", ">=", "<", "<="))
  val binopREval = KiasanIntegerExtension.binopREval

  @Unaries(Array("-", "+"))
  def unopAEval = KiasanIntegerExtension.unopAEval

  import language.implicitConversions
  implicit def b2v(b : Boolean) : Value
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanIntegerBExtension extends ExtensionCompanion {
  def create[S <: KiasanStatePart[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KiasanIntegerBExtension(config)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KiasanIntegerBExtension[S <: KiasanStatePart[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KiasanIntegerExtension[S] {
  import KonkritBooleanExtension._

  def b2v(b : Boolean) = KonkritBooleanExtension.b2v(b)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanIntegerIExtension extends ExtensionCompanion {
  def create[S <: KiasanStatePart[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KiasanIntegerIExtension(config)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KiasanIntegerIExtension[S <: KiasanStatePart[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KiasanIntegerExtension[S] {
  import KonkritIntegerExtension._
  import KiasanIntegerExtension._

  def b2v(b : Boolean) = KonkritIntegerExtension.b2v(b)

  @Cond
  val cond = KiasanIntegerExtension.cond
}