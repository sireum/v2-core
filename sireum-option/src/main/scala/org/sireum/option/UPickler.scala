/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/
package org.sireum.option

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "upickler", className = "org.sireum.tools.upickler.UPickler", featureName = "Sireum Tools")
case class UPicklerMode(
  @Arg(index = 0, value = "package-name") // 
  var packageName : String = "",

  @Arg(index = 1, value = "root-class") // 
  var rootClass : String = "",

  @Arg(index = 2, value = "import-classes") // 
  var importClasses : ISeq[String] = ivectorEmpty,

  @Args("leaf-classes") //
  var leafClasses : Array[String] = Array())
