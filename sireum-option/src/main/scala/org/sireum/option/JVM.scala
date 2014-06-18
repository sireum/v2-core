/*
Copyright (c) 2011-2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/
package org.sireum.option

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "jvm", className = "org.sireum.jvm.translator.Translator", featureName = "Sireum Tools")
case class JVMMode(
  @Option(shortKey = "d", longKey = "directory", desc = "Output Directory") // 
  var dir : String = "(current direcory)",
  
  @Args("classes") //
  var classes : Array[String] = Array());