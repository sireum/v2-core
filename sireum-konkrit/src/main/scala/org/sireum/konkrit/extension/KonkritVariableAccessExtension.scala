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

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritVariableAccessExtension extends ExtensionCompanion {
  def apply(ec : ExtensionConfig) = new KonkritVariableAccessExtension(ec)

  import language.implicitConversions
  import PilarAstUtil._

  private implicit def t2l[T](t : T) = ivector(t)

  private type V = Value
  private type SS[S <: SS[S]] = State[S]

  @inline
  def variable[S <: SS[S]](s : S, x : NameUser) = s.variable(varUri(x))

  @inline
  def variable[S <: SS[S]](s : S, x : NameUser, v : V) : S =
    s.variable(varUri(x), v)

  @inline
  def variableLookup[S <: SS[S]] : (S, NameUser) --> ISeq[(S, V)] = {
    case (s, x) =>
      import State.NameAccess._
      (s, s(x))
  }

  @inline
  def variableUpdate[S <: SS[S]] : (S, NameUser, V) --> ISeq[S] = {
    case (s, x, v) =>
      import State.NameAccess._
      s(x) = v
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritVariableAccessExtension[S <: State[S]](ec : ExtensionConfig)
    extends Extension {

  val uriPath = UriUtil.classUri(this)

  @VarLookup
  val variableLookup = KonkritVariableAccessExtension.variableLookup

  @VarUpdate
  val variableUpdate = KonkritVariableAccessExtension.variableUpdate
}
