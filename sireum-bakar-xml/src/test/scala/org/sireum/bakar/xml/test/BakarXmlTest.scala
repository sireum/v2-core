package org.sireum.bakar.xml.test

import org.scalatest.junit.JUnitRunner
import org.sireum.test.bakar.compiler.BakarCompilerTestv1
import org.junit.runner.RunWith
import org.sireum.pipeline.PipelineConfiguration
import org.sireum.pipeline.PipelineStage
import org.sireum.bakar.xml.module.Gnat2XMLWrapperModule
import org.sireum.bakar.xml.module.ParseGnat2XMLModule
import java.io.Writer
import org.sireum.pipeline.PipelineJob
import org.sireum.util.FileUtil
import java.io.File
import java.net.URI
import org.sireum.util.ISeq
import org.sireum.util.FileResourceUri

@RunWith(classOf[JUnitRunner])
class BakarXmlTest extends BakarCompilerTestv1 {

  override def accept(name : String, files : ISeq[FileResourceUri]) : Boolean = {
    if (!files.exists(f => f.endsWith("ads") || f.endsWith("adb"))) {
      return false;
    }
    return super.accept(name, files)
  }

  override def pre(c : Configuration) : Boolean = {
    Gnat2XMLWrapperModule.setSrcFiles(c.job.properties, c.sources)
    Gnat2XMLWrapperModule.setDestDir(c.job.properties, Some(FileUtil.toUri(c.resultsDir)))
    return true;
  }

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
      )
    )

  override def generateExpected = false
  override def outputSuffix = "g2xml"

  override def writeTestString(job : PipelineJob, w : Writer) = {

    val results = ParseGnat2XMLModule.getParseGnat2XMLresults(job.properties)
    results.toList.sorted foreach {
      case (key, value) =>
        val f = new File(new URI(key))
        w.write(f.getName() + "\n" + value + "\n\n")
    }
  }
}