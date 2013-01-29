/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
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
    fresh : (S, ResourceUri) => (S, V),
    rep : (S, V) --> V) : (S, NameUser) --> ISeq[(S, V)] = {
    case (s, x) =>
      val vuri = varUri(x)
      val s2 =
        if (s.hasVariableValue(vuri)) s
        else {
          val (s1, v) = fresh(s, vuri)
          s1.variable(vuri, v)
        }
      (s2, rep(s2, s2.variable(vuri)))
  }

  @inline
  def variableUpdate[S <: SS[S]](
    rep : (S, V) --> V) : (S, NameUser, V) --> ISeq[S] = {
    case (s, x, v) => s.variable(varUri(x), rep(s, v))
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KiasanVariableAccessExtension[S <: State[S]](
  config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  val uriPath = UriUtil.classUri(this)

  val sei = {
    import KiasanSemanticsExtensionConsumer._
    config.semanticsExtension.kiasanSemanticsExtension
  }

  @inline
  def fresh(s : S, varUri : ResourceUri) =
    sei.freshKiasanValue(s, config.typeProvider.typeUri(varUri))

  @VarLookup
  val variableLookup = KiasanVariableAccessExtension.variableLookup(fresh, sei.rep)

  @VarUpdate
  val variableUpdate = KiasanVariableAccessExtension.variableUpdate(sei.rep)
}
