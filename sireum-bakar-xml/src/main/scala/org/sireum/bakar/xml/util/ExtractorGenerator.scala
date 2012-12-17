package org.sireum.bakar.xml.util

import org.sireum.util._
import java.io.File
import org.sireum.bakar.xml._
import org.stringtemplate.v4.STGroupFile
import java.io.FileWriter
import javax.xml.bind.annotation.XmlElements
import org.sireum.bakar.xml.Base
import org.sireum.bakar.xml.CompilationUnit

class ExtractorGenerator(outdir : File,
                         packageName : String,
                         baseNode : java.lang.Class[_]) {
  val seen = msetEmpty[java.lang.Class[_]]

  val stg : STGroupFile = new STGroupFile(getClass.getResource("extractor.stg"), "UTF-8", '$', '$')
  //val stTopLevel = stg.getInstanceOf("topLevel")

  def process(c : java.lang.Class[_]) {
    if (seen.contains(c))
      return
    else
      seen += c

    var oname = c.getSimpleName()
    var fqname = c.getName()
    if (c.getEnclosingClass() != null) {
      fqname = c.getEnclosingClass().getName() + "." + c.getSimpleName()
      oname = c.getEnclosingClass().getSimpleName() + "_" + oname
    }
    oname += "Ex"

    val stmain = stg.getInstanceOf("extractor")

    stmain.add("name", oname)
    stmain.add("fqname", fqname)

    c.getDeclaredFields().foreach { f =>
      val name = f.getName()
      val annotations = f.getAnnotations()

      val fname = f.getName()
      val fmname = "get" + fname.charAt(0).toUpper + fname.drop(1) + "()"

      val stfield = stg.getInstanceOf("field")
      stfield.add("name", fmname)
      stmain.add("field", stfield)

      if (f.getType == baseNode) {
        // <jxb:globalBindings>
        //   <xjc:superClass name="org.sireum.bakar.xml.Base"/>
        // </jxb:globalBindings>

        // iterate through the XmlElement (attached to the XmlElements) and 
        // invoke their 'type' method in order to determine the possible 
        // runtime types of this field
        f.getDeclaredAnnotations().foreach { a =>
          if (a.annotationType() == classOf[javax.xml.bind.annotation.XmlElements]) {
            val xelems = a.asInstanceOf[javax.xml.bind.annotation.XmlElements]
            xelems.value().foreach { elem =>
              elem.getClass().getDeclaredMethods().foreach(f =>
                if (f.getName == "type") {
                  val ftype = f.invoke(elem).asInstanceOf[java.lang.Class[_]]
                  if (ftype.getPackage() == c.getPackage) {
                    process(ftype)
                  }
                }
              )
            }
          }
        }
      } else if (f.getType().getPackage() == c.getPackage()) {
        process(f.getType)
      }
    }

    val stTopLevel = stg.getInstanceOf("topLevel").
      add("entry", stmain).add("packageName", packageName)
    val fw = new FileWriter(new File(outdir, oname + ".scala"))
    fw.write(stTopLevel.render);
    fw.close
  }
}

object x {

  def main(args : Array[String]) {
    val root = classOf[CompilationUnit]
    val outdir = new File(root.getResource(".").getPath().replace("bin", "src/main/scala"))
    val baseNode = classOf[org.sireum.bakar.xml.Base]

    val eg = new ExtractorGenerator(outdir, root.getPackage().getName(), baseNode)
    eg.process(root)
  }
}