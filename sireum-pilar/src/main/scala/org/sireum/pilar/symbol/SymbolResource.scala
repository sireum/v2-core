/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.symbol

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Symbol extends PropertyProvider

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Symbol {
  import language.implicitConversions

  val symbolPropKey = "pilar.symbol"

  implicit def pp2r[T <: PropertyProvider](pp : T) : Resource =
    pp.getPropertyOrElse(symbolPropKey,
      new Resource {
        var _uriScheme : String = null
        var _uriType : String = null
        var _uriPaths : ISeq[String] = null
        var _uri : ResourceUri = null

        def uriScheme = { require(hasResourceInfo); _uriScheme }

        def uriType = { require(hasResourceInfo); _uriType }

        def uriPaths = { require(hasResourceInfo); _uriPaths }

        def uri = { require(hasResourceInfo); _uri }

        def hasResourceInfo : Boolean = pp ? symbolPropKey

        def uri(scheme : String, typ : String,
                paths : ISeq[String], uri : ResourceUri) {
          require(paths != null && uri != null && paths.forall(_ != null))
          pp(symbolPropKey) = this
          this._uriScheme = scheme
          this._uriType = typ
          this._uriPaths = paths
          this._uri = uri
        }
      })

  implicit object ResourceAdapter
      extends Adapter[PropertyProvider, Resource] {
    def adapt(pp : PropertyProvider) : Resource = pp
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolDefinition extends Symbol

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolUser extends Symbol

