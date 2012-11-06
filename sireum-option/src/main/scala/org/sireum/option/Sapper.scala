package org.sireum.option

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "sapper", className = "org.sireum.tools.sapp.Sapper", featureName = "Sireum Tools")
case class SapperMode(
  @Arg(index = 0, value = "file.sapp")
  var sappFile : String = "",
  
  @Args("files") //
  var files : Array[String] = Array())
