/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan.extension

import org.sireum.extension._
import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.pilar.state.State._
import org.sireum.util._
import org.sireum.pilar.eval.EvaluatorConfiguration

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanVariableAccessExtension extends ExtensionCompanion {
  def create[S <: State[S]](config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new KiasanVariableAccessExtension(config)

  import language.implicitConversions
  import PilarAstUtil._

  private implicit def t2l[T](t : T) = ivector(t)

  private type V = Value
  private type SS[S <: SS[S]] = State[S]

  @inline
  def variableLookup[S <: SS[S]](
    implicit fresh : (S, ResourceUri) => (S, V),
    rep : (S, V) --> V) : (S, NameUser) --> ISeq[(S, V)] = {
    case (s, x) =>
      s.variableOpt(x) match {
        case Some(v) => (s, rep(s, v))
        case _ =>
          val (s2, v) = fresh(s, varUri(x))
          (s2(x) = v, v)
      }
  }

  @inline
  def variableUpdate[S <: SS[S]](
    implicit rep : (S, V) --> V) : (S, NameUser, V) --> ISeq[S] = {
    case (s, x, v) => s(x) = rep(s, v)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KiasanVariableAccessExtension[S <: State[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  val uriPath = UriUtil.classUri(this)

  val se = {
    import KiasanSemanticsExtensionConsumer._
    config.semanticsExtension.kiasanSemanticsExtension
  }

  @inline
  implicit def fresh(s : S, varUri : ResourceUri) =
    se.freshKiasanValue(s, config.typeProvider.typeUri(varUri))

  implicit val repF = se.rep

  @VarLookup
  val variableLookup = KiasanVariableAccessExtension.variableLookup

  @VarUpdate
  val variableUpdate = KiasanVariableAccessExtension.variableUpdate
}
