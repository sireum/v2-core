/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.konkrit.extension

import org.sireum.extension._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.util._
import org.sireum.util.math._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class KonkritIntValue extends IntValue with ConcreteValue with IsInt {
  def value : Int
  def asInt = value
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntExtension extends ExtensionCompanion {
  def create[S](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritIntBExtension(config)

  private type Op = String

  import PilarAstUtil._

  @inline
  def binopASem(opA : Op)(c : Int, d : Int) =
    opA match {
      case ADD_BINOP  => c + d
      case SUB_BINOP  => c - d
      case MUL_BINOP  => c * d
      case DIV_BINOP  => c / d
      case REM_BINOP  => c % d
      case USHR_BINOP => c >>> d
      case SHR_BINOP  => c >> d
      case SHL_BINOP  => c << d
    }

  @inline
  def opRSem(opR : Op)(c : Int, d : Int) =
    opR match {
      case EQ_BINOP => c == d
      case NE_BINOP => c != d
      case GT_BINOP => c > d
      case GE_BINOP => c >= d
      case LT_BINOP => c < d
      case LE_BINOP => c <= d
    }

  @inline
  def unopASem(opA : Op)(c : Int) =
    opA match {
      case PLUS_UNOP  => c
      case MINUS_UNOP => -c
    }

  private type CV = KonkritIntValue
  private type V = Value

  import language.implicitConversions

  @inline
  private implicit def re2r[S](p : (S, V)) = ilist(p)

  @inline
  private implicit def int2v(n : Int) = CInt(n)

  @inline
  private implicit def cv2int(c : CV) = c.value

  @inline
  def nativeIndexConverter : V --> Integer = {
    case (v : CV) => SireumNumber(v)
  }

  @inline
  def defValue[S] : (S, ResourceUri) --> ISeq[(S, V)] = {
    case (s, IntExtension.Type) => ilist((s, 0))
  }

  @inline
  def cast[S] : (S, V, ResourceUri) --> ISeq[(S, V)] = {
    case (s, v : CV, IntExtension.Type)    => (s, v)
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
  def b2v(b : Boolean) : V = if (b) 1 else 0

  @inline
  def cond[S](canCast : (S, V, ResourceUri) => Boolean,
              cast : (S, V, ResourceUri) --> ISeq[(S, V)]) //
              : (S, V) --> ISeq[(S, Boolean)] = {
    case (s, c : CV) => ilist((s, c.value != 0))
    case (s, v) if canCast(s, v, KonkritIntExtension.Type) =>
      for {
        (s1, v1) <- cast(s, v, KonkritIntExtension.Type)
      } yield (s1, v1.asInstanceOf[CV].value != 0)
  }

  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"

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
trait KonkritIntExtension[S]
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  import KonkritIntExtension._

  val uriPath = UriUtil.classUri(this)

  @NativeIndex
  val nativeIndexConverter = KonkritIntExtension.nativeIndexConverter

  @DefaultValueProvider
  val defValue = KonkritIntExtension.defValue[S]

  @Cast
  val cast = KonkritIntExtension.cast[S]

  @Literal(classOf[Int])
  val literal = KonkritIntExtension.literal[S]

  import PilarAstUtil._

  @Binaries(Array(ADD_BINOP, SUB_BINOP, MUL_BINOP, DIV_BINOP, REM_BINOP,
    USHR_BINOP, SHR_BINOP, SHL_BINOP))
  val binopAEval = KonkritIntExtension.binopAEval[S]

  @Binaries(Array(EQ_BINOP, NE_BINOP, GT_BINOP, GE_BINOP, LT_BINOP, LE_BINOP))
  val binopREval = KonkritIntExtension.binopREval[S](b2v _)

  @Unaries(Array(PLUS_UNOP, MINUS_UNOP))
  val unopAEval = KonkritIntExtension.unopAEval[S]

  def b2v(b : Boolean) : Value
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntBExtension extends ExtensionCompanion {
  def create[S](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritIntBExtension(config)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritIntBExtension[S](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KonkritIntExtension[S] {
  import KonkritBooleanExtension._

  def b2v(b : Boolean) = KonkritBooleanExtension.b2v(b)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntIExtension extends ExtensionCompanion {
  def create[S](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritIntIExtension(config)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritIntIExtension[S](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KonkritIntExtension[S] {
  import KonkritIntExtension._

  def b2v(b : Boolean) = KonkritIntExtension.b2v(b)

  val se = config.semanticsExtension

  @Cond
  val cond = KonkritIntExtension.cond(se.canCast, se.cast)
}