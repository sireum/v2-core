package org.sireum.bakar.xml.extractor

import org.sireum.util._
import java.io.File
import org.sireum.bakar.xml._
import org.stringtemplate.v4.ST
import org.stringtemplate.v4.STGroupFile
import java.io.FileWriter

class ExtractorGenerator(outdir : File) {
  val seen = msetEmpty[java.lang.Class[_]]

  val stg : STGroupFile = new STGroupFile(getClass.getResource("extractor.stg"), "UTF-8", '$', '$')
  val stTopLevel = stg.getInstanceOf("topLevel")

  def process(c : java.lang.Class[_]) {
    if (seen.contains(c))
      return
    else
      seen += c

    var oname = c.getSimpleName()
    var fqname = c.getName()
    if(c.getEnclosingClass() != null){
      fqname = c.getEnclosingClass().getName() + "." + c.getSimpleName()
      oname = c.getEnclosingClass().getSimpleName() + "_" + oname
    }
    
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

      if (f.getType().getPackage() == c.getPackage()) {
        process(f.getType)
      }
      else if (f.getType == classOf[java.lang.Object]) {
        // fname is type which is reserved so we can't call it directly.  Instead
        // use reflection
        f.getDeclaredAnnotations().foreach { a =>
          if (a.annotationType() == classOf[javax.xml.bind.annotation.XmlElements]) {
            val xelems = a.asInstanceOf[javax.xml.bind.annotation.XmlElements]
            xelems.value().foreach { elem =>
              elem.getClass().getDeclaredMethods().foreach(f =>
                if (f.getName == "type") {
                  val ftype = f.invoke(elem).asInstanceOf[java.lang.Class[_]]
                  if(ftype.getPackage() == c.getPackage){
                    process(ftype)
                  }
                }
              )
            }
          }
        }
      }
    }

    /*
    if(c.getEnclosingClass() != null){
      stTopLevel.add("entry", stg.getInstanceOf("comment").add("entry", stmain))
    } else
    */ 
    stTopLevel.add("entry", stmain)
  }
}

object x {

  def main(args : Array[String]) {
    val root = classOf[CompilationUnit]
    val outdir = new File(this.getClass().getResource(".").getPath().replace("bin", "src/main/scala"))

    println(root)
    println(outdir)

    val eg = new ExtractorGenerator(outdir)
    eg.process(root)
    
    val fw = new FileWriter(new File(outdir, "e.scala"))
    fw.write(eg.stTopLevel.render);
    fw.close
  }
}