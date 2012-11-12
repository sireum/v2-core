/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.topi.process

import java.io._
import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object TopiProcess {
  type ExpTranslator = Exp --> Unit
  
  trait BackEndPart {
    def expTranslator(sb : StringBuilder) : ExpTranslator
    def stateRewriter(m : IMap[String, Value]) : RewriteFunction
  }
}