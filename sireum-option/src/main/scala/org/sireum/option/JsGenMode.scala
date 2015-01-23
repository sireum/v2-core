package org.sireum.option

import java.io._
import org.sireum.util._

/**
 * @author <a href="mailto:jake.h.ehrlich@gmail.com">Jake Ehrlich</a>
 */
class JsGenOption extends CliGenOption {
  
}

/**
 * @author <a href="mailto:jake.h.ehrlich@gmail.com">Jake Ehrlich</a>
 */
@Main(value = "jsgen", className = "org.sireum.js.casegen.JsConvBuilder", featureName = "Sireum Tools")
@Check(classOf[JsGenOption]) //  
case class JsGenMode(
  @Check(classOf[JsGenOption]) //
  @Option(shortKey = "d", longKey = "directory", desc = "Directory where generated class should be saved") // 
  var dir : String = ".",
  
  @Option(shortKey = "c", longKey = "class-name", desc = "Fully qualified name for the generated object") //
  var genObjectName : String = "JsConv",
  
  @Check(classOf[JsGenOption])
  @Option(shortKey = "c", longKey = "class-name", desc = "Fully qualified name for the generated implcit class") //
  var genClassName : String = "JsConv",

  //@Option(shortKey = "p", longKey = "packages", desc = "Package name prefixes used to filter which classes to process", separator = ";") 
  //var packages : ISeq[String] = ivectorEmpty,
  
  @Option(shortKey = "p", longKey = "package", desc = "The (optional) package the generated code should go in")
  var packageName : String = "",

  @Option(longKey = "min-col", desc = "Column where description should begin")
  var minCol : Integer = 20,
  
  @Option(longKey = "max-col", desc = "Maximum number of characters per line")
  var maxCol : Integer = 80,
  
  @Option(shortKey = "cp", longKey = "classpath", desc = "Classpaths containing the className attribute of Main modes")
  var classpath : ISeq[String] = ivectorEmpty,
  
  @Arg(index = 0, value="class-name") //
  @Check(classOf[JsGenOption])
  var className : String = ""
)