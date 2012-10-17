/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.option

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "antlr", className = "org.sireum.tools.antlr.TreeVisitorGen", featureName = "Sireum Tools")
case class TreeVisitorGenMode( //
  @Option(shortKey = "d", longKey = "directory", desc = "Directory for the generated class") // 
  var dir : String = "(parent directory of token file)",

  @Option(shortKey = "c", longKey = "class-name", desc = "Name for the generated class") //
  var className : String = "TreeVisitor",

  @Option(shortKey = "p", longKey = "package", desc = "Package name for the generated class") //
  var packageName : String = "parser",

  @Arg(index = 0, value = "token-file") //
  var tokenFile : String = "")