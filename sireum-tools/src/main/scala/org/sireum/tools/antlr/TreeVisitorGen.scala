/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.tools.antlr

import java.io._
import java.util._
import org.stringtemplate.v4._
import org.sireum.option.TreeVisitorGenMode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object TreeVisitorGen {
  def run(option : TreeVisitorGenMode) {
    new TreeVisitorGen().execute(option)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class TreeVisitorGen {
  def execute(option : TreeVisitorGenMode) {
    val defMode = TreeVisitorGenMode()
    val packageName = option.packageName
    val tokenFile = new File(option.tokenFile)
    val dir =
      if (option.dir == defMode.dir) tokenFile.getParentFile
      else new File(option.dir)
    val className = option.className

    println("Generated file: " +
      generate(dir, tokenFile, packageName, className).getAbsolutePath())
  }

  def generate(dir : File, tokenFile : File, packageName : String,
               className : String) = {
    val group = new STGroupFile(TreeVisitorGen.getClass
      .getResource("TreeVisitor.stg").toURI().toURL(), "UTF-8", '$', '$')
    val is = new FileInputStream(tokenFile)
    val p = new Properties()
    p.load(is)
    is.close()
    val st = group.getInstanceOf("classdef")
    st.add("pkg", packageName)
    st.add("class", className)

    import scala.collection.JavaConversions._

    for (k <- p.keySet()) {
      val ks = k.toString()
      if (!ks.startsWith("'")) {
        val t = group.getInstanceOf("casedef")
        t.add("name", ks)
        t.add("value", p.get(ks).toString())
        st.add("case", t)
        val t2 = group.getInstanceOf("visitdef")
        t2.add("name", ks)
        st.add("visit", t2)
      }
    }
    val f = new File(dir, className + ".java")
    val fw = new FileWriter(f)
    fw.write(st.render())
    fw.close()
    f
  }
}