package org.sireum.bakar.xml.test

import java.io.File
import org.sireum.bakar.xml.scalaxb.Compilation_Unit
import java.io.FilenameFilter

object test1 {

  def main(Args : Array[String]) : Unit = {

    var dir = new File(this.getClass().getResource(".").getPath())
    val files = dir.listFiles(new FilenameFilter {
      override def accept(dir : File, name : String) = name.endsWith("ads.xml") || name.endsWith("adb.xml")
    })
    files.foreach(f => {
      var xmly = scala.xml.XML.loadFile(f);
      var cu = scalaxb.fromXML[Compilation_Unit](xmly)
      println(f.getAbsolutePath())
      println(cu)
    }
    )
  }
}