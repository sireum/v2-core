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
object KonkritLongExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritLongBExtension(config)

  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  trait KonkritLongValue extends IntegerValue with ConcreteValue {
    def value : Long
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class CLong(value : Long) extends KonkritLongValue {
    val typeUri = KonkritLongExtension.Type
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritLongExtension[S <: State[S]]
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  import KonkritLongExtension._

  val uriPath = UriUtil.classUri(this)

  type C = KonkritLongValue

  @inline
  private implicit def re2r(p : (S, Value)) = ilist(p)

  @inline
  private implicit def int2integer(n : Int) = SireumNumber(n)

  @inline
  private implicit def kiv2int(c : C) = c.value

  @NativeIndex
  def nativeIndexConverter : Value --> Integer = {
    case (v : C) => SireumNumber(v)
  }

  @DefaultValue
  def defValue : (S, ResourceUri) --> ISeq[(S, Value)] = {
    case (s, IntegerExtension.Type) => (s, CLong(0))
  }

  @Cast
  def castType : (S, Value, ResourceUri) --> ISeq[(S, Value)] = {
    case (s, v : C, IntegerExtension.Type)     => (s, v)
    case (s, v : C, KonkritLongExtension.Type) => (s, v)
  }

  @Literal(classOf[Long])
  def literal : (S, Long) --> ISeq[(S, Value)] = {
    case (s, n) => (s, CLong(n))
  }

  @Binaries(Array("+", "-", "*", "/", "%", "^>>", "^>", "^<"))
  def binopAEval : (S, Value, String, Value) --> ISeq[(S, Value)] = {
    case (s, c : C, opA : String, d : C) => (s, CLong(binopASem(opA)(c, d)))
  }

  @inline
  private def binopASem(opA : String)(c : Long, d : Long) =
    opA match {
      case "+"   => c + d
      case "-"   => c - d
      case "*"   => c * d
      case "/"   => c / d
      case "%"   => c % d
      case "^>>" => c >>> d
      case "^>"  => c >> d
      case "^<"  => c << d
    }

  @Binaries(Array("==", "!=", ">", ">=", "<", "<="))
  def binopREval : (S, Value, String, Value) --> ISeq[(S, Value)] = {
    case (s, c : C, opR : String, d : C) => (s, b2v(opRSem(opR)(c, d)))
  }

  def b2v(b : Boolean) : Value

  @inline
  private def opRSem(opR : String)(c : Long, d : Long) =
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
    case (s, opA, c : C) => (s, CLong(unopASem(opA)(c)))
  }

  @inline
  private def unopASem(opA : String)(c : Long) =
    opA match {
      case "+" => c
      case "-" => -c
    }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritLongBExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritLongBExtension(config)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritLongBExtension[S <: State[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KonkritLongExtension[S] {
  import KonkritBooleanExtension._

  def b2v(b : Boolean) : Value = if (b) TT else FF
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritLongIExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritLongIExtension(config)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritLongIExtension[S <: State[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KonkritLongExtension[S] {
  import KonkritLongExtension._

  def b2v(b : Boolean) : Value = if (b) CLong(1) else CLong(0)

  val se = config.semanticsExtension

  @Cond
  def cond : (S, Value) --> ISeq[(S, Boolean)] = {
    case (s, c : C) => ilist((s, c.value != 0))
    case (s, v) if se.canCast(s, v, KonkritLongExtension.Type) =>
      val r = se.cast(s, v, KonkritLongExtension.Type)
      r.map { p => (p._1, p._2.asInstanceOf[C].value != 0) }
  }
}