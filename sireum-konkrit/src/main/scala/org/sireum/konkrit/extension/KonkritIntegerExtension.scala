/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.konkrit.extension

import org.sireum.extension._
import org.sireum.extension.IntegerExtension._
import org.sireum.extension.annotation._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.util._
import org.sireum.util.math._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritIntegerValue extends IntegerValue with ConcreteValue with IsInteger {
  def value : Integer
  def asInteger = value
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntegerExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritIntegerBExtension(config)

  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"

  private type Op = String

  @inline
  def binopASem(opA : Op)(c : Integer, d : Integer) =
    opA match {
      case "+" => c + d
      case "-" => c - d
      case "*" => c * d
      case "/" => c / d
      case "%" => c % d
    }

  @inline
  def opRSem(opR : Op)(c : Integer, d : Integer) =
    opR match {
      case "==" => c == d
      case "!=" => c != d
      case ">"  => c > d
      case ">=" => c >= d
      case "<"  => c < d
      case "<=" => c <= d
    }

  @inline
  def unopASem(opA : Op)(c : Integer) =
    opA match {
      case "+" => c
      case "-" => -c
    }

  private type CV = IsInteger
  private type V = Value

  import language.implicitConversions

  @inline
  private implicit def re2r[S](p : (S, V)) = ilist(p)

  @inline
  private implicit def bigint2v(n : BigInt) = CI(SireumNumber(n))

  @inline
  private implicit def integer2v(n : Integer) = CI(n)

  @inline
  private implicit def int2v(n : Int) = CI(SireumNumber(n))

  @inline
  private implicit def kiv2integer(c : CV) = c.asInteger

  @inline
  def nativeIndexConverter : V --> Integer = {
    case (v : CV) => v.asInteger
  }

  @inline
  def defValue[S] : (S, ResourceUri) --> ISeq[(S, V)] = {
    case (s, IntegerExtension.Type) => ilist((s, 0))
  }

  @inline
  def cast[S] : (S, V, ResourceUri) --> ISeq[(S, V)] = {
    case (s, v : CV, IntegerExtension.Type)        => (s, v)
    case (s, v : CV, KonkritIntegerExtension.Type) => (s, v)
  }

  @inline
  def literal[S] : (S, BigInt) --> ISeq[(S, V)] = {
    case (s, n) => ilist((s, n))
  }

  @inline
  def binopAEval[S] : (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, c : CV, opA, d : CV) => (s, CI(binopASem(opA)(c, d)))
  }

  @inline
  def unopAEval[S] : (S, Op, V) --> ISeq[(S, V)] = {
    case (s, opA, c : CV) => ilist((s, unopASem(opA)(c)))
  }

  @inline
  def binopREval[S](b2v : Boolean => V) : (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, c : CV, opR, d : CV) => (s, b2v(opRSem(opR)(c, d)))
  }

  @inline
  def cond[S](canCast : (S, V, ResourceUri) => Boolean,
              cast : (S, V, ResourceUri) --> ISeq[(S, V)]) : //
              (S, V) --> ISeq[(S, Boolean)] = {
    case (s, i : CV) => ilist((s, !i.asInteger.isZero))
    case (s, v) if canCast(s, v, KonkritIntegerExtension.Type) =>
      val r = cast(s, v, KonkritIntegerExtension.Type)
      r.map { p => (p._1, !p._2.asInstanceOf[CV].asInteger.isZero) }
  }

  @inline
  def b2v(b : Boolean) : V = if (b) 1 else 0

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class CI(value : Integer) extends KonkritIntegerValue {
    val typeUri = KonkritIntegerExtension.Type
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritIntegerExtension[S <: State[S]]
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  import KonkritIntegerExtension._

  val uriPath = UriUtil.classUri(this)

  @NativeIndex
  def nativeIndexConverter = KonkritIntegerExtension.nativeIndexConverter

  @DefaultValue
  def defValue = KonkritIntegerExtension.defValue[S]

  @Cast
  def cast = KonkritIntegerExtension.cast[S]

  @Literal(classOf[BigInt])
  def literal = KonkritIntegerExtension.literal[S]

  @Binaries(Array("+", "-", "*", "/", "%"))
  def binopAEval = KonkritIntegerExtension.binopAEval[S]

  @Unaries(Array("-", "+"))
  def unopAEval = KonkritIntegerExtension.unopAEval[S]

  @Binaries(Array("==", "!=", ">", ">=", "<", "<="))
  def binopREval = KonkritIntegerExtension.binopREval(b2v _)

  def b2v(b : Boolean) : V
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntegerBExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritIntegerBExtension(config)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritIntegerBExtension[S <: State[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KonkritIntegerExtension[S] {
  import KonkritBooleanExtension._

  def b2v(b : Boolean) = KonkritBooleanExtension.b2v(b)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntegerIExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritIntegerIExtension(config)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritIntegerIExtension[S <: State[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KonkritIntegerExtension[S] {
  import KonkritIntegerExtension._

  def b2v(b : Boolean) = KonkritIntegerExtension.b2v(b)

  val se = config.semanticsExtension

  @Cond
  def cond = KonkritIntegerExtension.cond(se.canCast, se.cast)
}