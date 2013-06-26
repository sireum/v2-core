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
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.pilar.state.State._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanVariableAccessExtension extends ExtensionCompanion {
  def apply(ec : ExtensionConfig) = new KiasanVariableAccessExtension(ec)

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
      import State.NameAccess._
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
    case (s, x, v) =>
      import State.NameAccess._
      s(x) = rep(s, v)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KiasanVariableAccessExtension[S <: State[S]](
    ec : ExtensionConfig) extends Extension {

  val uriPath = UriUtil.classUri(this)

  val sec = {
    import SemanticsExtensionConfig._
    import KiasanSemanticsExtensionConsumer._
    ec.semanticsExtension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]].kiasanSemanticsExtension
  }

  @inline
  implicit def fresh(s : S, varUri : ResourceUri) = {
    import TypeProviderConfig._
    sec.freshKiasanValue(s, ec.typeProvider.typeUri(varUri))
  }

  implicit val repF = sec.rep

  @VarLookup
  val variableLookup = KiasanVariableAccessExtension.variableLookup

  @VarUpdate
  val variableUpdate = KiasanVariableAccessExtension.variableUpdate
}
