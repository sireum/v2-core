/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan.extension

import org.sireum.extension._
import org.sireum.kiasan.extension.annotation._
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

  def freshKiasanValue(s : S, typeUri : ResourceUri) : (S, V)
  def processTranslators : ISeq[TopiSolver.Type --> TopiProcess.BackEndPart]

  def rep(s : S, v : V) : V
  def rep2(s : S, v1 : V, v2 : V) : V
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanSemanticsExtensionInit[S, V, R, C, SR]
    extends SemanticsExtensionInit[S, V, R, C, SR] {
  import org.sireum.topi._
  import org.sireum.topi.process._

  def addFreshKiasanValue(valueF : (S, ResourceUri) --> (S, V))
  def addProcessTranslator(valueF : TopiSolver.Type --> TopiProcess.BackEndPart)

  def addValueRep(repF : (S, V, V) --> V)
  def addGetRep(repF : (S, V) --> V)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanSemanticsExtensionInitConsumer[S, V, R, C, SR]
  extends KiasanSemanticsExtensionInit[S, V, R, C, SR]
  with KiasanSemanticsExtensionConsumer[S, V, R, C, SR]
  with SemanticsExtensionInit[S, V, R, C, SR]

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanSemanticsExtensionInitImpl[S, V, R, C, SR]
    extends KiasanSemanticsExtensionInit[S, V, R, C, SR] {
  import org.sireum.topi._
  import org.sireum.topi.process._

  protected val _freshKiasanPFA : MArray[(S, ResourceUri) --> (S, V)] = marrayEmpty
  protected val _freshKiasanPF : (S, ResourceUri) --> (S, V) = PartialFunctionUtil.orElses(_freshKiasanPFA)

  def addFreshKiasanValue(valueF : (S, ResourceUri) --> (S, V)) {
    assert(!PartialFunctionUtil.empty.equals(valueF))
    _freshKiasanPFA += valueF
  }

  protected val _processTranslatorPFA : MArray[TopiSolver.Type --> TopiProcess.BackEndPart] = marrayEmpty

  def addProcessTranslator(valueF : TopiSolver.Type --> TopiProcess.BackEndPart) {
    assert(!PartialFunctionUtil.empty.equals(valueF))
    _processTranslatorPFA += valueF
  }

  protected val _repPFA : MArray[(S, V) --> V] = {
    val r = marrayEmpty[(S, V) --> V]
    r += {
      case (_, v) => v
    }
    r
  }
  protected val _repPF : (S, V) --> V = PartialFunctionUtil.orElses(_repPFA)
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
  protected val _rep2PF : (S, V, V) --> V = PartialFunctionUtil.orElses(_rep2PFA)
  def addValueRep(rep2F : (S, V, V) --> V) {
    assert(!PartialFunctionUtil.empty.equals(rep2F))
    _rep2PFA += rep2F
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanSemanticsExtensionConsumerImpl[S, V, R, C, SR]
    extends KiasanSemanticsExtensionConsumer[S, V, R, C, SR] {
  ksei : KiasanSemanticsExtensionInitImpl[S, V, R, C, SR] =>

  import org.sireum.topi._
  import org.sireum.topi.process._

  def freshKiasanValue(s : S, typeUri : ResourceUri) : (S, V) = _freshKiasanPF(s, typeUri)
  def processTranslators : ISeq[TopiSolver.Type --> TopiProcess.BackEndPart] = _processTranslatorPFA.toList
  def rep(s : S, v : V) : V = _repPF(s, v)
  def rep2(s : S, v1 : V, v2 : V) : V = _rep2PF(s, v1, v2)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanExtensionMiner {
  def mine[S, V, R, C, SR](m : Method, ann : java.lang.annotation.Annotation,
                           sei : SemanticsExtensionInit[S, V, R, C, SR],
                           ext : Extension[S, V, R, C, SR]) : Boolean = {
    if (!sei.isInstanceOf[KiasanSemanticsExtensionInit[S, V, R, C, SR]])
      return false

    import org.sireum.topi._
    import org.sireum.topi.process._

    val ksei = sei.asInstanceOf[KiasanSemanticsExtensionInit[S, V, R, C, SR]]
    ann match {
      case ann : FreshKiasanValue =>
        ksei.addFreshKiasanValue(m.invoke(ext).asInstanceOf[(S, ResourceUri) --> (S, V)])
      case ann : ProcessTranslator =>
        ksei.addProcessTranslator(m.invoke(ext).asInstanceOf[TopiSolver.Type --> TopiProcess.BackEndPart])
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
    extends SemanticsExtensionModule[S, V, R, C, SR] {
  override val sei =
    new SemanticsExtensionInitImpl[S, V, R, C, SR] // 
    with KiasanSemanticsExtensionInitImpl[S, V, R, C, SR] //
    with KiasanSemanticsExtensionConsumerImpl[S, V, R, C, SR] //
    with KiasanSemanticsExtensionInitConsumer[S, V, R, C, SR]

  override def miners = ilist(KiasanExtensionMiner.mine[S, V, R, C, SR] _,
    ExtensionMiner.mine[S, V, R, C, SR] _)
}
