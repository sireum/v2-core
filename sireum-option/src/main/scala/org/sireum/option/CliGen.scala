/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.option

import java.io._
import org.sireum.util._

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
object CliGenOption{
 val ERROR_CLASS_NAME = "Unable to find class: "
}

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
class CliGenOption {
  def check(cgm : CliGenMode, tags : MBuffer[Tag]) : Boolean = {
   // genNameCheck(cgm, tags)

  //  return dirCheck(cgm, tags) && checkClassName(cgm, tags) && genNameCheck(cgm, tags)
    true
  }
  
  def genClassNameCheck(cgm: CliGenMode, tags : MBuffer[Tag]) : Boolean ={
//    if(cgm.genClassName == null || cgm.genClassName.isEmpty){
//      tags += OptionUtil.genTag(OptionUtil.ErrorMarker, "Generated name cannot be empty")
//      return false
//    }
    return true
  }
  
  def dirCheck(cgm : CliGenMode, tags : MBuffer[Tag]) : Boolean = {
    import java.io._
    var res = cgm.dir != null && !cgm.dir.isEmpty()
    if (res) {
      val f = new File(cgm.dir)
      res = f.exists() && f.isDirectory()
    }
    if (!res) {
      tags += OptionUtil.genTag(OptionUtil.ErrorMarker, "Invalid directory: '" + cgm.dir + "'")
    }
    return res
  }

  def classNameCheck(cgm : CliGenMode, tags : MBuffer[Tag]) : Boolean = {
    var res = true
    try {
      Class.forName(cgm.className)
    } catch {
      case e =>
        tags += OptionUtil.genTag(OptionUtil.ErrorMarker, CliGenOption.ERROR_CLASS_NAME + cgm.className)
        res = false
    }
    return res
  }
}


/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
@Main(value = "cligen", className = "org.sireum.cli.gen.CliBuilder", featureName = "Sireum Tools")
@Check(classOf[CliGenOption]) //  
case class CliGenMode(
  @Check(classOf[CliGenOption]) //
  @Option(shortKey = "d", longKey = "directory", desc = "Directory where generated class should be saved") // 
  var dir : String = ".",
  
  @Check(classOf[CliGenOption])
  @Option(shortKey = "c", longKey = "class-name", desc = "Fully qualified name for the generated class") //
  var genClassName : String = "Cli",

  @Option(shortKey = "p", longKey = "packages", desc = "Package name prefixes used to filter which classes to process", separator = ";") 
  var packages : ISeq[String] = ivectorEmpty,

  @Option(longKey = "min-col", desc = "Column where description should begin")
  var minCol : Integer = 20,
  
  @Option(longKey = "max-col", desc = "Maximum number of characters per line")
  var maxCol : Integer = 80,
  
  @Arg(index = 0, value="class-name") //
  @Check(classOf[CliGenOption])
  var className : String = ""
)
