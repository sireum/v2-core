/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.example.pilar.symbol

import org.sireum.example.Examples

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object SymbolExamples extends Examples {
  val GOOD_MODEL_DIR_URI = sourceDirUri(this.getClass, "./good/model/") 
  
  def goodModelFiles = exampleFiles(GOOD_MODEL_DIR_URI)
}