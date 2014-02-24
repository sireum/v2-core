/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.extension

import org.sireum.pilar.state._
import org.sireum.util._
import org.sireum.pilar.eval.EvaluatorConfiguration

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object TupleValueExtension extends ExtensionCompanion {
  def apply(ec : ExtensionConfig) = new TupleValueExtension(ec)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class TupleValueExtension[S](ec : ExtensionConfig) extends Extension {

  type V = Value
  type R = ISeq[(S, Value)]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]

  def uriPath = "org/sireum/extension/TupleValue"

  @TupleCon
  val tupleCon : (S, ISeq[V]) --> (S, V) = {
    case (s, vs) => (s, TupleValue(vs))
  }

  @TupleDecon
  val tupleDecon : (S, V) --> ISeq[(S, ISeq[V])] = {
    case (s, TupleValue(vs)) => ivector((s, vs))
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class TupleValue(vs : ISeq[Value]) extends Value
