/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
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
object UriValueExtension extends ExtensionCompanion {
  def apply(ec : ExtensionConfig) = new UriValueExtension(ec)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class UriValueExtension[S](ec : ExtensionConfig) extends Extension {

  type V = Value
  type R = ISeq[(S, Value)]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]

  def uriPath = "org/sireum/extension/UriValue"

  @UriValueProvider
  def valueProviderExtractor : ((S, ResourceUri) --> R, V --> ResourceUri) =
    ({ case (s, r) => ivector((s, UriValueImpl(r))) },
      { case v : UriValue => v.uri })
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class UriValueImpl(uri : ResourceUri) extends UriValue
