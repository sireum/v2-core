package org.sireum.bakar.xml.test

import java.io.File
import java.io.FilenameFilter

object test1 {

  def main(Args : Array[String]) : Unit = {

    var dir = new File(this.getClass().getResource(".").getPath())
    val files = dir.listFiles(new FilenameFilter {
      override def accept(dir : File, name : String) = name.endsWith("ads.xml") || name.endsWith("adb.xml")
    })
    files.foreach(f => {
      var xmly = scala.xml.XML.loadFile(f);
      assert(false)
      var cu = ""
      println(f.getAbsolutePath())
      println(cu)
    }
    )
  }
}