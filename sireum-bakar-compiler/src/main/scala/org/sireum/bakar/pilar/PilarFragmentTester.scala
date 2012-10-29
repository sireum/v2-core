package org.sireum.bakar.pilar

import org.sireum.pilar.parser.PilarParser
import org.sireum.util.FileUtil

object PilarFragmentTester {
  val claz = org.sireum.pilar.ast.Exp.getClass
  //val claz = org.sireum.pilar.ast.Model.getClass
  
  def main(args : Array[String]) {
    val frag = FileUtil.fileUri(this.getClass, "frag.plr")
    println(PilarParser.parseFileWithErrorAsString(frag, claz))
  }
}