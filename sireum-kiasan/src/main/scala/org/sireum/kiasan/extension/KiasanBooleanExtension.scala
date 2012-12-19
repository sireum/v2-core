/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan.extension

import org.sireum.extension._
import org.sireum.extension.BooleanExtension._
import org.sireum.kiasan.extension._
import org.sireum.kiasan.extension.KiasanExtension._
import org.sireum.kiasan.state._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.topi.annotation._
import org.sireum.topi.process._
import org.sireum.util._
import org.sireum.util.math._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanBooleanValue extends BooleanValue with KiasanValue

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanBooleanExtension extends ExtensionCompanion {
  def create[S <: KiasanStatePart[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KiasanBooleanExtension(config)

  private type Op = String
  private type V = Value
  private type KS[S <: KS[S]] = KiasanStatePart[S]

  import language.implicitConversions

  private type CV = org.sireum.konkrit.extension.KonkritBooleanValue
  private type KV = KiasanBooleanValue

  @inline
  private implicit def re2r[S](p : (S, V)) = ilist(p)

  @inline
  def cast[S] : (S, V, ResourceUri) --> ISeq[(S, V)] = {
    case (s, v : KV, BooleanExtension.Type)       => (s, v)
    case (s, v : KV, KiasanBooleanExtension.Type) => (s, v)
  }

  @inline
  def cond[S <: KS[S]] : (S, V) --> ISeq[(S, Boolean)] = {
    case (s, b : KV) =>
      ilist((s.addPathCondition(ValueExp(b)).requestInconsistencyCheck, true),
        (s.addPathCondition(UnaryExp("!", ValueExp(b))).
          requestInconsistencyCheck, false))
  }

  @inline
  def fresh[S <: KS[S]](s : S) = {
    val (nextS, num) = s.next(KiasanBooleanExtension.Type)
    (nextS, KB(num))
  }

  @inline
  def fresh[S <: KS[S]] : (S, ResourceUri) --> (S, Value) = {
    case (s, KiasanBooleanExtension.Type) => fresh(s)
  }

  @inline
  def binopEqu[S <: KS[S]](cond : (S, V) --> ISeq[(S, Boolean)]) : // 
  (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, b1 : CV, opEqu, b2 : KV) => binopEquHelper(cond, s, b1, opEqu, b2)
    case (s, b1 : KV, opEqu, b2 : CV) => binopEquHelper(cond, s, b1, opEqu, b2)
    case (s, b1 : KV, opEqu, b2 : KV) => binopEquHelper(cond, s, b1, opEqu, b2)
  }

  @inline
  def binopEquHelper[S <: KS[S]](
    cond : (S, V) --> ISeq[(S, Boolean)], s : S, v1 : V, opEqu : Op, v2 : V) = {
    import org.sireum.konkrit.extension._
    for {
      (s2, b1) <- cond(s, v1)
      (s3, b2) <- cond(s2, v2)
    } yield (s3,
      KonkritBooleanExtension.b2v(
        KonkritBooleanExtension.binopEquSem(opEqu)(b1, b2)))
  }

  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class KB(num : Int) extends KiasanBooleanValue {
    val typeUri = KiasanBooleanExtension.Type
  }

  @BackEnd(value = "Z3", mode = "Process")
  def z3BackEndPart = new TopiProcess.BackEndPart {

    class Context(var tc : TopiProcess.TypeCounters,
                  val sb : StringBuilder = new StringBuilder) {
      def result = (tc, sb.toString)
    }

    val lineSep = System.getProperty("line.separator")

    import language.implicitConversions

    @inline
    implicit def i2s(i : Int) = i.toString

    @inline
    def println(ss : String*)(implicit ctx : Context) {
      for (s <- ss)
        ctx.sb.append(s)
      ctx.sb.append(lineSep)
    }

    def declareConst(num : Int)(implicit ctx : Context) {
      val lastNum = ctx.tc.getOrElse(KiasanBooleanExtension.Type, -1)
      if (num > lastNum) {
        for (i <- lastNum + 1 to num)
          println("(declare-const b!", i, " Bool)")
        ctx.tc = ctx.tc + (KiasanBooleanExtension.Type -> num)
      }
    }

    def expTranslator = {
      case (tc, ValueExp(KB(num))) =>
        implicit val ctx = new Context(tc)
        declareConst(num)
        println("(assert b!", num, ")")
        ctx.result
      case (tc, UnaryExp("!", ValueExp(KB(num)))) =>
        implicit val ctx = new Context(tc)
        declareConst(num)
        println("(assert (not b!", num, "))")
        ctx.result
    }

    def stateRewriter(m : IMap[String, Value]) = {
      case v @ KB(num) => m.getOrElse("b!" + num, v)
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KiasanBooleanExtension[S <: KiasanStatePart[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  val uriPath = UriUtil.classUri(this)

  @Cast
  val cast = KiasanBooleanExtension.cast[S]

  @Cond
  val cond = KiasanBooleanExtension.cond[S]

  @FreshKiasanValueProvider
  val fresh = KiasanBooleanExtension.fresh[S]

  val se = config.semanticsExtension

  @Binaries(Array("==", "!="))
  val binopEqu = KiasanBooleanExtension.binopEqu[S](se.cond)
}