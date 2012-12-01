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

  @inline
  def b2v(b : Boolean) = if (b) TT else FF

  @inline
  def binopEquSem(opEqu : String)(b1 : Boolean, b2 : Boolean) =
    opEqu match {
      case "==" => b1 == b2
      case "!=" => b1 != b2
    }
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

  type C = IsBoolean

  @inline
  private implicit def re2r(p : (S, Value)) = ilist(p)

  @Cast
  def castType : (S, Value, ResourceUri) --> ISeq[(S, Value)] = {
    case (s, v : C, BooleanExtension.Type)        => (s, v)
    case (s, v : C, KonkritBooleanExtension.Type) => (s, v)
  }

  val se = config.semanticsExtension

  @Cond
  def cond : (S, Value) --> ISeq[(S, Boolean)] = {
    case (s, b : C) => ilist((s, b.asBoolean))
  }

  @Literal(value = classOf[Boolean], isTrue = true)
  def literalT : S --> ISeq[(S, Value)] = { case s => (s, TT) }

  @Literal(value = classOf[Boolean], isTrue = false)
  def literalF : S --> ISeq[(S, Value)] = { case s => (s, FF) }

  @DefaultValue
  def defValue : (S, ResourceUri) --> ISeq[(S, Value)] = {
    case (s, BooleanExtension.Type) => (s, FF)
  }

  val sec = config.semanticsExtension

  @Binaries(Array("&&&", "|||", "===>", "<==="))
  def binopLEval : (S, Value, String, Value) --> ISeq[(S, Value)] = {
    case (s, v1 : Value, opL : String, v2 : Value) =>
      for {
        (s2, b1) <- sec.cond(s, v1)
        (s3, b2) <- sec.cond(s2, v2)
      } yield (s3, b2v(binopLSem(opL)(b1, b2)))
  }

  @Binaries(Array("==", "!="))
  def binopEqu : (S, Value, String, Value) --> ISeq[(S, Value)] = {
    case (s, b1 : C, opEqu, b2 : C) =>
      (s, b2v(opEqu match {
        case "==" => b1.asBoolean == b2.asBoolean
        case "!=" => b1.asBoolean != b2.asBoolean
      }))
  }

  @RBinaries(Array("&&", "||", "==>", "<=="))
  def binopSCEval : (S, Value, String, S => ISeq[(S, Value)]) --> ISeq[(S, Value)] = {
    case (s, v1 : Value, opSC : String, f) =>
      for {
        (s2, b1) <- sec.cond(s, v1)
        (s5, v) <- {
          binopSCSem(opSC)(b1) match {
            case Left(b) => ilist((s2, b2v(b)))
            case Right(fb) =>
              for {
                (s3, v2) <- f(s2)
                (s4, b2) <- se.cond(s3, v2)
              } yield (s4, b2v(fb(b2)))
          }
        }
      } yield (s5, v)
  }

  @Unary("!")
  def unopEval : (S, Value) --> ISeq[(S, Value)] = {
    case (s, v : Value) =>
      for {
        (s2, b) <- sec.cond(s, v)
      } yield (s2, b2v(!b))
  }

  @inline
  private def binopLSem(opL : String)(b1 : Boolean, b2 : Boolean) =
    opL match {
      case "=="   => b1 == b2
      case "!="   => b1 != b2
      case "&&&"  => b1 && b2
      case "|||"  => b1 || b2
      case "<===" => b1 || !b2
      case "===>" => !b1 || b2
    }

  @inline
  private def binopSCSem(opA : String)(b1 : Boolean) : Either[Boolean, Boolean => Boolean] =
    opA match {
      case "&&"  => if (b1) Right(identity) else Left(false)
      case "||"  => if (b1) Left(true) else Right(identity)
      case "<==" => if (b1) Left(true) else Right(!_)
      case "==>" => if (!b1) Left(true) else Right(identity)
    }
}