package org.sireum.bakar.compiler.v1.module

import java.io.FileWriter
import scala.collection.JavaConversions._
import org.sireum.bakar.compiler.v1.translator.PilarTranslator
import org.sireum.bakar.util.message.Message
import org.sireum.util._
import org.sireum.pipeline._
import org.sireum.bakar.compiler.v1.module._
import org.sireum.bakar.compiler.v1.module.BakarTranslatorv1Module

class BakarTranslatorv1Def(val job : PipelineJob, info : PipelineJobModuleInfo) extends BakarTranslatorv1Module {
  val sco = this.sparkCompilerOptions
  val to = this.transOrder
  val messages = mlistEmpty[Message]

  val transResults : MMap[String, String] = PilarTranslator.translate(transOrder, sco, messages)
  assert(transResults != null && !transResults.isEmpty)

  import scala.io._

  val results = mlistEmpty[String]

  try {
    for ((pilarName, pilarCode) <- transResults) {
      val fname = sco.getOptOutputPath + pilarName + ".plr"
      val fw = new FileWriter(fname)
      fw.write(pilarCode)
      fw.close
      results += fname
    }
  } catch {
    case e => e.printStackTrace
  }

  this.sources_=(results.toList)
}