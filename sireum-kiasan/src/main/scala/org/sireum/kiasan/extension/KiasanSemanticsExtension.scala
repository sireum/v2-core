/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan.extension

import org.sireum.extension._
import org.sireum.kiasan.extension._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.util._

import java.lang.reflect.Method
import java.lang.annotation.Annotation

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanSemanticsExtensionConsumer[S, V, R, C, SR]
    extends SemanticsExtensionConsumer[S, V, R, C, SR] {
  import org.sireum.topi._
  import org.sireum.topi.process._

  def kiasanSemanticsExtension = this

  def freshKiasanValue : (S, ResourceUri) --> (S, V)

  def rep : (S, V) --> V
  def rep2 : (S, V, V) --> V
}

object KiasanSemanticsExtensionConsumer {
  import language.implicitConversions
  implicit def sec2ksec[S, V, R, C, SR](sec : SemanticsExtensionConsumer[S, V, R, C, SR]) =
    sec.asInstanceOf[KiasanSemanticsExtensionConsumer[S, V, R, C, SR]]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanSemanticsExtensionInit[S, V, R, C, SR]
    extends KiasanSemanticsExtensionConsumer[S, V, R, C, SR]
    with SemanticsExtensionInit[S, V, R, C, SR] {

  def addFreshKiasanValue(valueF : (S, ResourceUri) --> (S, V))

  def addValueRep(repF : (S, V, V) --> V)
  def addGetRep(repF : (S, V) --> V)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class KiasanSemanticsExtensionInitImpl[S, V, R, C, SR]
    extends KiasanSemanticsExtensionInit[S, V, R, C, SR]
    with SemanticsExtensionInitImpl[S, V, R, C, SR] {

  protected val _freshKiasanPFA : MArray[(S, ResourceUri) --> (S, V)] = marrayEmpty
  val freshKiasanValue : (S, ResourceUri) --> (S, V) = PartialFunctionUtil.orElses(_freshKiasanPFA)

  def addFreshKiasanValue(valueF : (S, ResourceUri) --> (S, V)) {
    assert(!PartialFunctionUtil.empty.equals(valueF))
    _freshKiasanPFA += valueF
  }

  protected val _repPFA : MArray[(S, V) --> V] = {
    val r = marrayEmpty[(S, V) --> V]
    r += {
      case (_, v) => v
    }
    r
  }

  val rep : (S, V) --> V = PartialFunctionUtil.orElses(_repPFA)

  def addGetRep(repF : (S, V) --> V) {
    assert(!PartialFunctionUtil.empty.equals(repF))
    _repPFA += repF
  }

  protected val _rep2PFA : MArray[(S, V, V) --> V] = {
    import org.sireum.kiasan.extension.KiasanExtension._
    val r = marrayEmpty[(S, V, V) --> V]
    r += {
      case (_, v1 : KiasanValue, v2 : KiasanValue) =>
        (if (v1.num <= v2.num) v1 else v2).asInstanceOf[V]
    }
    r
  }

  val rep2 : (S, V, V) --> V = PartialFunctionUtil.orElses(_rep2PFA)

  def addValueRep(rep2F : (S, V, V) --> V) {
    assert(!PartialFunctionUtil.empty.equals(rep2F))
    _rep2PFA += rep2F
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanExtensionMiner {
  def mine[S, V, R, C, SR](m : Method, ann : java.lang.annotation.Annotation,
                           sei : SemanticsExtensionInit[S, V, R, C, SR],
                           ext : Extension) : Boolean = {
    if (!sei.isInstanceOf[KiasanSemanticsExtensionInit[S, V, R, C, SR]])
      return false

    import org.sireum.topi._
    import org.sireum.topi.process._

    val ksei = sei.asInstanceOf[KiasanSemanticsExtensionInit[S, V, R, C, SR]]
    ann match {
      case ann : FreshKiasanValueProvider =>
        ksei.addFreshKiasanValue(m.invoke(ext).asInstanceOf[(S, ResourceUri) --> (S, V)])
      case _ =>
        return false
    }
    return true
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanSemanticsExtensionModule[S, V, R, C, SR]
  extends SemanticsExtensionModule[S, V, R, C, SR]
