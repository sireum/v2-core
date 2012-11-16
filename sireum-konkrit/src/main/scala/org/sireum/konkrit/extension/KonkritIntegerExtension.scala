/*
`Copyright (c) 2011-2012 Robby, Kansas State University.        
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
object KonkritIntegerExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritIntegerBExtension(config)

  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"
}

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
final case class CI(value : Integer) extends KonkritIntegerValue {
  val typeUri = KonkritIntegerExtension.Type
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritIntegerExtension[S <: State[S]]
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  import KonkritIntegerExtension._

  val uriPath = UriUtil.classUri(this)

  type C = IsInteger

  @inline
  private implicit def re2r(p : (S, Value)) = ilist(p)

  @inline
  private implicit def bigint2integer(n : BigInt) = SireumNumber(n)

  @inline
  private implicit def kiv2integer(c : C) = c.asInteger

  @NativeIndex
  def nativeIndexConverter : Value --> Integer = {
    case (v : C) => v.asInteger
  }

  @DefaultValue
  def defValue : (S, ResourceUri) --> ISeq[(S, Value)] = {
    case (s, IntegerExtension.Type) => (s, CI(SireumNumber(0)))
  }

  @Cast
  def castType : (S, Value, ResourceUri) --> ISeq[(S, Value)] = {
    case (s, v : C, IntegerExtension.Type)        => (s, v)
    case (s, v : C, KonkritIntegerExtension.Type) => (s, v)
  }

  @Literal(classOf[BigInt])
  def literal : (S, BigInt) --> ISeq[(S, Value)] = {
    case (s, n) => (s, CI(n))
  }

  @Binaries(Array("+", "-", "*", "/", "%"))
  def binopAEval : (S, Value, String, Value) --> ISeq[(S, Value)] = {
    case (s, c : C, opA : String, d : C) => (s, CI(binopASem(opA)(c, d)))
  }

  @inline
  private def binopASem(opA : String)(c : Integer, d : Integer) =
    opA match {
      case "+" => c + d
      case "-" => c - d
      case "*" => c * d
      case "/" => c / d
      case "%" => c % d
    }

  @Binaries(Array("==", "!=", ">", ">=", "<", "<="))
  def binopREval : (S, Value, String, Value) --> ISeq[(S, Value)] = {
    case (s, c : C, opR : String, d : C) => (s, b2v(opRSem(opR)(c, d)))
  }

  def b2v(b : Boolean) : Value

  @inline
  private def opRSem(opR : String)(c : Integer, d : Integer) =
    opR match {
      case "==" => c == d
      case "!=" => c != d
      case ">"  => c > d
      case ">=" => c >= d
      case "<"  => c < d
      case "<=" => c <= d
    }

  @Unaries(Array("-", "+"))
  def unopAEval : (S, String, Value) --> ISeq[(S, Value)] = {
    case (s, opA, c : C) => (s, CI(unopASem(opA)(c)))
  }

  @inline
  private def unopASem(opA : String)(c : Integer) =
    opA match {
      case "+" => c
      case "-" => -c
    }
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

  def b2v(b : Boolean) : Value = if (b) TT else FF
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

  def b2v(b : Boolean) : Value = if (b) CI(SireumNumber(1)) else CI(SireumNumber(0))

  val se = config.semanticsExtension

  @Cond
  def cond : (S, Value) --> ISeq[(S, Boolean)] = {
    case (s, i : C) => ilist((s, !i.asInteger.isZero))
    case (s, v) if se.canCast(s, v, KonkritIntegerExtension.Type) =>
      val r = se.cast(s, v, KonkritIntegerExtension.Type)
      r.map { p => (p._1, !p._2.asInstanceOf[C].asInteger.isZero) }
  }
}