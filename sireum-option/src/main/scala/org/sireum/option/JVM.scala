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