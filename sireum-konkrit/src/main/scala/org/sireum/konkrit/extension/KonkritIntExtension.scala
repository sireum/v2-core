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
trait KonkritIntValue extends IntegerValue with ConcreteValue {
  def value : Int
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritIntBExtension(config)

  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"

  private type Op = String

  @inline
  def binopASem(opA : Op)(c : Int, d : Int) =
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

  @inline
  def opRSem(opR : Op)(c : Int, d : Int) =
    opR match {
      case "==" => c == d
      case "!=" => c != d
      case ">"  => c > d
      case ">=" => c >= d
      case "<"  => c < d
      case "<=" => c <= d
    }

  @inline
  def unopASem(opA : Op)(c : Int) =
    opA match {
      case "+" => c
      case "-" => -c
    }

  private type CV = KonkritIntValue
  private type V = Value

  import language.implicitConversions

  @inline
  private implicit def re2r[S](p : (S, V)) = ilist(p)

  @inline
  private implicit def int2v(n : Int) = CInt(n)

  @inline
  private implicit def kiv2int(c : CV) = c.value

  @inline
  def nativeIndexConverter : V --> Integer = {
    case (v : CV) => SireumNumber(v)
  }

  @inline
  def defValue[S] : (S, ResourceUri) --> ISeq[(S, V)] = {
    case (s, IntegerExtension.Type) => ilist((s, 0))
  }

  @inline
  def cast[S] : (S, V, ResourceUri) --> ISeq[(S, V)] = {
    case (s, v : CV, IntegerExtension.Type)    => (s, v)
    case (s, v : CV, KonkritIntExtension.Type) => (s, v)
  }

  @inline
  def literal[S] : (S, Int) --> ISeq[(S, V)] = {
    case (s, n) => ilist((s, n))
  }

  @inline
  def binopAEval[S] : (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, c : CV, opA, d : CV) => ilist((s, binopASem(opA)(c, d)))
  }

  @inline
  def binopREval[S](b2v : Boolean => V) : (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, c : CV, opR, d : CV) => ilist((s, b2v(opRSem(opR)(c, d))))
  }

  @inline
  def unopAEval[S] : (S, Op, V) --> ISeq[(S, V)] = {
    case (s, opA, c : CV) => ilist((s, unopASem(opA)(c)))
  }

  @inline
  def b2vAsBoolean(b : Boolean) = {
    import KonkritBooleanExtension._
    if (b) TT else FF
  }

  @inline
  def b2vAsInt(b : Boolean) : V = if (b) 1 else 0

  @inline
  def cond[S](canCast : (S, V, ResourceUri) => Boolean,
              cast : (S, V, ResourceUri) --> ISeq[(S, V)]) //
              : (S, V) --> ISeq[(S, Boolean)] = {
    case (s, c : CV) => ilist((s, c.value != 0))
    case (s, v) if canCast(s, v, KonkritIntExtension.Type) =>
      val r = cast(s, v, KonkritIntExtension.Type)
      r.map { p => (p._1, p._2.asInstanceOf[CV].value != 0) }
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class CInt(value : Int) extends KonkritIntValue {
    val typeUri = KonkritIntExtension.Type
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritIntExtension[S <: State[S]]
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  import KonkritIntExtension._

  val uriPath = UriUtil.classUri(this)

  @NativeIndex
  def nativeIndexConverter = KonkritIntExtension.nativeIndexConverter

  @DefaultValue
  def defValue = KonkritIntExtension.defValue[S]

  @Cast
  def cast = KonkritIntExtension.cast[S]

  @Literal(classOf[Int])
  def literal = KonkritIntExtension.literal[S]

  @Binaries(Array("+", "-", "*", "/", "%", "^>>", "^>", "^<"))
  def binopAEval = KonkritIntExtension.binopAEval[S]

  @Binaries(Array("==", "!=", ">", ">=", "<", "<="))
  def binopREval = KonkritIntExtension.binopREval[S](b2v _)

  @Unaries(Array("-", "+"))
  def unopAEval = KonkritIntExtension.unopAEval[S]

  def b2v(b : Boolean) : Value
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntBExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritIntBExtension(config)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritIntBExtension[S <: State[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KonkritIntExtension[S] {
  import KonkritBooleanExtension._

  def b2v(b : Boolean) = KonkritIntExtension.b2vAsBoolean(b)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntIExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritIntIExtension(config)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritIntIExtension[S <: State[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KonkritIntExtension[S] {
  import KonkritIntExtension._

  def b2v(b : Boolean) = KonkritIntExtension.b2vAsInt(b)

  val se = config.semanticsExtension

  @Cond
  def cond = KonkritIntExtension.cond(se.canCast, se.cast)
}