/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.state

import org.sireum.util._
import org.sireum.util.math._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Value {
  def toInteger : Value --> Integer = {
    case v : IsInteger => v.asInteger
  }

  def toBoolean : Value --> Boolean = {
    case v : IsBoolean => v.asBoolean
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Value

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ConcreteValue extends Value

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait AbstractValue extends Value

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ScalarValue extends Value {
  def typeUri : ResourceUri
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ReferenceValue extends Value

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait UriValue extends Value {
  def uri : ResourceUri
}