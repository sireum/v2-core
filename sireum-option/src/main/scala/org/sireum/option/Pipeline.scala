/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
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
@Main(value = "pipeline", className = "org.sireum.pipeline.gen.ModuleGenerator", featureName = "Sireum Tools")
case class PipelineMode(
  @Option(shortKey = "d", longKey = "directory", desc = "Directory where generated class should be saved") // 
  var dir : String = "",

  @Option(shortKey = "gcn", longKey = "generated-class-name", desc = "Name for the generated class") //
  var genClassName : String = "",
  
  @Option(shortKey = "ts", longKey = "type-substitutions", desc = "Pairs of fully qualified type names separated by '/' (e.g. java.lang.Boolean/scala.Boolean)")
  var typeSubstitutions : ISeq[String] = ivectorEmpty,
  
  @Args("class-names") //
  var classNames : Array[String] = Array())
  