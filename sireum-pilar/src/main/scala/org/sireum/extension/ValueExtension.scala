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

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object ValueExtension {

}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object BooleanExtension {
  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class BooleanValue extends NonReferenceValue

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object IntegralExtension {
  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class IntegralValue extends NonReferenceValue

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object IntegerExtension {
  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class IntegerValue extends IntegralValue

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object IntExtension {
  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class IntValue extends IntegralValue

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object LongExtension {
  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class LongValue extends IntegralValue

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object ObjectExtension {
  val Type = "pilar://typeext/org/sireum/pilar/state/extension/Object/Type"

  val RefType = "pilar://typeext/org/sireum/pilar/state/extension/Object/RefType"

  val ValType = "pilar://typeext/org/sireum/pilar/state/extension/Object/ValType"
}
