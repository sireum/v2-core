/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.cli.gen

import org.sireum.option._
import org.sireum.cli._
import org.sireum.util._
import com.thoughtworks.xstream.XStream
import java.io.FileWriter
import java.io.PrintWriter
import org.stringtemplate.v4.ST
import org.stringtemplate.v4.STGroupFile
import org.sireum.cli.PipelineRunner

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
class testing {

}

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
object test1 {
  def main(args : Array[String]) {

    var cgm : CliGenMode = null
    cgm = CliGenMode()
    cgm.dir = "./src/main/scala"
    cgm.genClassName = "SireumCli"
    cgm.packages = List("org.sireum")
    cgm.className = org.sireum.option.SireumMode.getClass.getName.replace("$", "")

    val c = Class.forName("org.sireum.option.CliGenMode")
    for (i <- c.getDeclaredAnnotations) {
      i match {
        case s : Main => {
          println(s.className)
        }
        case _ => {
          
        }
      }

    }
  }
}