package org.sireum.test.bakar.compiler

import org.junit.runner.RunWith
import java.io.Writer
import org.sireum.pipeline._
import org.sireum.bakar.xml.module.Gnat2XMLWrapperModule
import org.sireum.bakar.xml.module.ParseGnat2XMLModule
import org.sireum.bakar.compiler.module._
import org.sireum.test.bakar.xml.BakarXmlTest
import org.sireum.pipeline._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BakarTranslatorTest extends BakarXmlTest {

  override def pipeline =
    PipelineConfiguration(
      "gnat2xml test pipeline",
      false,
      PipelineStage(
        "gnat2xml stage",
        false,
        Gnat2XMLWrapperModule
      ),
      PipelineStage(
        "scalaxb stage",
        false,
        ParseGnat2XMLModule
      ),
      PipelineStage(
        "translator stage",
        false,
        BakarTranslatorModule)
    )

  override def generateExpected = false
  override def outputSuffix = "gvisitor"

  override def writeTestString(job : PipelineJob, w : Writer) = {

    /*
    val results = ParseGnat2XMLModule.getParseGnat2XMLresults(job.properties)
    results.toList.sorted foreach {
      case (key, value) =>
        val f = new File(new URI(key))
        w.write(f.getName() + "\n" + value + "\n\n")
    }
    */
  }
}