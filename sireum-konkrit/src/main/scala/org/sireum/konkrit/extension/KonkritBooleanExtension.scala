/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
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
abstract class KonkritBooleanValue
    extends BooleanValue with ConcreteValue with IsBoolean {
  def value : Boolean
  def asBoolean = value
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritBooleanExtension extends ExtensionCompanion {
  def create[S](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritBooleanExtension(config)

  @inline
  def b2v(b : Boolean) = if (b) TT else FF

  private type Op = String

  import PilarAstUtil._

  @inline
  def binopEquSem(opEqu : Op)(b1 : Boolean, b2 : Boolean) =
    opEqu match {
      case EQ_BINOP => b1 == b2
      case NE_BINOP => b1 != b2
    }

  @inline
  def binopLSem(opL : Op)(b1 : Boolean, b2 : Boolean) =
    opL match {
      case LOGICAL_AND_BINOP     => b1 && b2
      case LOGICAL_OR_BINOP      => b1 || b2
      case LOGICAL_IMPLIED_BINOP => b1 || !b2
      case LOGICAL_IMPLY_BINOP   => !b1 || b2
    }

  @inline
  def binopSCSem(opA : Op)(b1 : Boolean) : Either[Boolean, Boolean => Boolean] =
    opA match {
      case COND_AND_BINOP     => if (b1) Right(identity) else Left(false)
      case COND_OR_BINOP      => if (b1) Left(true) else Right(identity)
      case COND_IMPLIED_BINOP => if (b1) Left(true) else Right(!_)
      case COND_IMPLY_BINOP   => if (!b1) Left(true) else Right(identity)
    }

  private type CV = IsBoolean
  private type V = Value
  private type Cnd[S] = (S, V) --> ISeq[(S, Boolean)]

  import language.implicitConversions

  @inline
  private implicit def re2r[S, T](p : (S, T)) = ivector(p)

  @inline
  def cast[S] : (S, V, ResourceUri) --> ISeq[(S, V)] = {
    case (s, v : CV, BooleanExtension.Type)        => (s, v)
    case (s, v : CV, KonkritBooleanExtension.Type) => (s, v)
  }

  @inline
  def cond[S] : (S, V) --> ISeq[(S, Boolean)] = {
    case (s, b : CV) => (s, b.asBoolean)
  }

  @inline
  def trueLit[S] : S --> (S, V) = { case s => (s, TT) }

  @inline
  def falseLit[S] : S --> (S, V) = { case s => (s, FF) }

  @inline
  def defValue[S] : (S, ResourceUri) --> ISeq[(S, V)] = {
    case (s, BooleanExtension.Type) => (s, FF)
  }

  @inline
  def binopLEval[S](implicit cond : Cnd[S]) : (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, v1, opL : Op, v2) =>
      for {
        (s2, b1) <- cond(s, v1)
        (s3, b2) <- cond(s2, v2)
      } yield (s3, b2v(binopLSem(opL)(b1, b2)))
  }

  @inline
  def binopEqu[S] : (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, b1 : CV, opEqu, b2 : CV) =>
      (s, b2v(binopEquSem(opEqu)(b1.asBoolean, b2.asBoolean)))
  }

  @inline
  def binopSCEval[S](implicit cond : Cnd[S]) : //
  (S, V, Op, S => ISeq[(S, V)]) --> ISeq[(S, V)] = {
    case (s, v1, opSC, f) =>
      for {
        (s2, b1) <- cond(s, v1)
        (s5, v) <- {
          binopSCSem(opSC)(b1) match {
            case Left(b) => ivector((s2, b2v(b)))
            case Right(fb) =>
              for {
                (s3, v2) <- f(s2)
                (s4, b2) <- cond(s3, v2)
              } yield (s4, b2v(fb(b2)))
          }
        }
      } yield (s5, v)
  }

  @inline
  def notEval[S](implicit cond : Cnd[S]) : (S, V) --> ISeq[(S, V)] = {
    case (s, v) =>
      for {
        (s2, b) <- cond(s, v)
      } yield (s2, b2v(!b))
  }

  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  object TT extends KonkritBooleanValue {
    val typeUri = KonkritBooleanExtension.Type
    val value = true
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  object FF extends KonkritBooleanValue {
    val typeUri = KonkritBooleanExtension.Type
    val value = false
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritBooleanExtension[S](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  import KonkritBooleanExtension._

  val uriPath = UriUtil.classUri(this)

  implicit val cnd = config.semanticsExtension.cond

  @Cast
  val cast = KonkritBooleanExtension.cast

  @Cond
  val cond = KonkritBooleanExtension.cond

  @Literal(value = classOf[Boolean], isTrue = true)
  val trueLit = KonkritBooleanExtension.trueLit

  @Literal(value = classOf[Boolean], isTrue = false)
  val falseLit = KonkritBooleanExtension.falseLit

  @DefaultValueProvider
  val defValue = KonkritBooleanExtension.defValue

  import PilarAstUtil._

  @Binaries(Array(LOGICAL_AND_BINOP, LOGICAL_OR_BINOP, LOGICAL_IMPLIED_BINOP,
    LOGICAL_IMPLY_BINOP))
  val binopLEval = KonkritBooleanExtension.binopLEval

  @Binaries(Array(EQ_BINOP, NE_BINOP))
  val binopEqu = KonkritBooleanExtension.binopEqu

  @RBinaries(Array(COND_AND_BINOP, COND_OR_BINOP, COND_IMPLIED_BINOP,
    COND_IMPLY_BINOP))
  val binopSCEval = KonkritBooleanExtension.binopSCEval

  @Unary(NOT_UNOP)
  val notEval = KonkritBooleanExtension.notEval
}