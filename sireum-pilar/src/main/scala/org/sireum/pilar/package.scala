/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
package object pilar {
  val PILAR_PACKAGE_CHAR = ':'
  val PILAR_PACKAGE_SEP = "::"
  val PILAR_EXT_SEP = "."
  val PILAR_VALUE_ANNOTATION_PARAM_ID = "value"
    
  def pilarSimpleName(name : String) : String = {
    val i = name.lastIndexOf(PILAR_PACKAGE_CHAR)
    if (i >= 0) name.substring(i + 1)
    else name
  }
}