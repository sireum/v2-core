/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.extension

import org.sireum.pilar.ast._
import org.sireum.util._
import org.sireum.pilar.eval.EvaluatorConfiguration

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Extension {
  def uriPath : ResourceUri
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ExtensionConfig

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SemanticsExtensionConfig {
  def extensions : ISeq[ExtensionCompanion]
  def semanticsExtension[S, V, R, C, SR] : SemanticsExtensionConsumer[S, V, R, C, SR]
  def semanticsExtension_=[S, V, R, C, SR](sec : SemanticsExtensionConsumer[S, V, R, C, SR])
}

object SemanticsExtensionConfig {
  import language.implicitConversions

  implicit def adapt(ec : ExtensionConfig) : SemanticsExtensionConfig =
    ec.asInstanceOf[SemanticsExtensionConfig]

  implicit object SemanticsExtensionConfigAdapter
      extends Adapter[ExtensionConfig, SemanticsExtensionConfig] {
    def adapt(ec : ExtensionConfig) : SemanticsExtensionConfig = ec
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ExtensionCompanion {
  def apply(ec : ExtensionConfig) : Extension
}
