/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.konkrit.extension

import org.sireum.extension._
import org.sireum.extension.BooleanExtension._
import org.sireum.extension.annotation._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.util._
import org.sireum.util.math._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritBooleanExtension extends ExtensionCompanion {
  def create[S <: State[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KonkritBooleanExtension(config)

  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritBooleanValue extends BooleanValue with ConcreteValue with IsBoolean {
  def value : Boolean
  def asBoolean = value
}

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

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritBooleanExtension[S <: State[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  import KonkritBooleanExtension._

  val uriPath = UriUtil.classUri(this)

  type KBV = IsBoolean

  @inline
  private implicit def re2r(p : (S, Value)) = ilist(p)

  @inline
  private implicit def kbv2boolean(c : KBV) = c.asBoolean

  @inline
  private def boolean2kbv(b : Boolean) = if (b) TT else FF

  @Cast
  def castType : (S, Value, ResourceUri) --> ISeq[(S, Value)] = {
    case (s, v : KBV, BooleanExtension.Type)        => (s, v)
    case (s, v : KBV, KonkritBooleanExtension.Type) => (s, v)
  }

  val se = config.semanticsExtension

  @Cond
  def cond : (S, Value) --> ISeq[(S, Boolean)] = {
    case (s, b : KBV) => ilist((s, b.asBoolean))
    case (s, v) if se.canCast(s, v, KonkritBooleanExtension.Type) =>
      val r = se.cast(s, v, KonkritBooleanExtension.Type)
      r.map { p => (p._1, p._2.asInstanceOf[KBV].asBoolean) }
  }

  @Literal(value = classOf[Boolean], isTrue = true)
  def literalT : S --> ISeq[(S, Value)] = { case s => (s, TT) }

  @Literal(value = classOf[Boolean], isTrue = false)
  def literalF : S --> ISeq[(S, Value)] = { case s => (s, FF) }

  @DefaultValue
  def defValue : (S, ResourceUri) --> ISeq[(S, Value)] = {
    case (s, BooleanExtension.Type) => (s, FF)
  }

  @Binaries(Array("&&&", "|||", "===>", "<==="))
  def binopLEval : (S, Value, String, Value) --> ISeq[(S, Value)] = {
    case (s, b1 : KBV, opL : String, b2 : KBV) =>
      (s, boolean2kbv(binopLSem(opL)(b1, b2)))
  }

  @inline
  private def binopLSem(opA : String)(b1 : Boolean, b2 : Boolean) =
    opA match {
      case "&&&"  => b1 && b2
      case "|||"  => b1 || b2
      case "<===" => b1 || !b2
      case "===>" => !b1 || b2
    }

  @RBinaries(Array("&&", "||", "==>", "<=="))
  def binopSCEval : (S, Value, String, S => ISeq[(S, Value)]) --> ISeq[(S, Value)] = {
    case (s, b1 : KBV, opSC : String, f) =>
      binopSCSem(opSC)(b1) match {
        case Left(b) => (s, boolean2kbv(b))
        case Right(fb) =>
          for {
            (s2, v) <- f(s)
            (s3, kbv) <- se.cast(s2, v, KonkritBooleanExtension.Type)
          } yield (s3, boolean2kbv(fb(kbv.asInstanceOf[KBV].asBoolean)))
      }
  }

  @inline
  private def binopSCSem(opA : String)(b1 : Boolean) : Either[Boolean, Boolean => Boolean] =
    opA match {
      case "&&"  => if (b1) Right(identity) else Left(false)
      case "||"  => if (b1) Left(true) else Right(identity)
      case "<==" => if (b1) Left(true) else Right(!_)
      case "==>" => if (!b1) Left(true) else Right(identity)
    }

  @Unary("!")
  def unopEval : (S, Value) --> ISeq[(S, Value)] = {
    case (s, b : KBV) => (s, boolean2kbv(!b.asBoolean))
  }
}