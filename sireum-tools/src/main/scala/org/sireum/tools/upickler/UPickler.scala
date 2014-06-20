/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.tools.upickler

import org.sireum.option._
import org.sireum.util._

import org.stringtemplate.v4._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object UPickler {
  def run(option : UPicklerMode) {
    new UPickler().execute(option)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class UPickler {
  def execute(option : UPicklerMode) {
      def p(name : String) =
        if (option.packageName == "") name
        else option.packageName + "." + name
    val r =
      pickler(
        Class.forName(p(option.rootClass)),
        option.leafClasses.map(c => Class.forName(p(c))) : _*)
    System.out.println(r)
    System.out.flush
  }

  def pickler(root : Class[_], leaves : Class[_]*) : String = {
    val stg = new STGroupFile(UPickler.getClass
      .getResource("upickler.stg").toURI().toURL(), "UTF-8", '$', '$')
    val st = stg.getInstanceOf("pickler")
    val stMain = stg.getInstanceOf("main").add("member", st)
    val stRoot = stg.getInstanceOf("root").add("class", root.getName)

    for (c <- leaves) {
      val name = c.getName

      st.add("member", stg.getInstanceOf("name").add("class", name))

      val stLeaf = stg.getInstanceOf("leaf").add("class", name)
      val stPy = stg.getInstanceOf("leafPy").add("class", name)
      st.add("member", stLeaf)
      stMain.add("member", stPy)

      val cc = Reflection.CaseClass.caseClassType(c, false)
      for (p <- cc.params) {
        stLeaf.
          add("wfield", stg.getInstanceOf("leafWriteField").add("name", p.name)).
          add("rfield", stg.getInstanceOf("leafReadField").add("type", p.tipe).add("name", p.name))
        stPy.add("field", p.name).
          add("fieldAssign", stg.getInstanceOf("leafPyAssign").add("name", p.name))
      }

      stRoot.
        add("wfield", stg.getInstanceOf("rootWriteField").add("class", name)).
        add("rfield", stg.getInstanceOf("rootReadField").add("class", name))

    }
    st.add("member", stRoot)
    stMain.render
  }
}