package org.sireum.bakar.compiler.module

import java.io.FileWriter
import scala.collection.JavaConversions._
import org.sireum.bakar.compiler.translator.PilarTranslator
import org.sireum.bakar.util.message.Message
import org.sireum.util._
import org.sireum.pipeline._
import org.sireum.bakar.compiler.module._

class BakarTranslatorDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends BakarTranslatorModule {
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