/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.extension.composite

import org.sireum.pilar.state._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object CompositeValue {
  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait CompositeValue extends ReferenceValue

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ValCompositeValue extends CompositeValue

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait RefCompositeValue extends CompositeValue

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object NullValue extends RefCompositeValue with ConcreteValue