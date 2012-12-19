/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan

import scala.annotation.target._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
package object extension {
  type FreshKiasanValueProvider = org.sireum.kiasan.extension.annotation.FreshKiasanValueProvider @getter
}