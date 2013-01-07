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
import org.sireum.test.bakar.framework.BakarTestFileFramework
import org.sireum.util._
import org.sireum.example.bakar.Project
import org.sireum.example.bakar.ProjectProvider
import java.io.File
import java.io.FilenameFilter
import org.sireum.example.bakar.BakarExamples
import org.sireum.example.bakar.BakarExamplesAnchor

object BakarProjectProvider extends ProjectProvider {
  override def getProjects(dir : File) : ISeq[Project] = {
    var projs = mlistEmpty[Project]
    dir.listFiles.filter(f => f.getName().endsWith(".smf")).foreach{ smf =>
      val files = io.Source.fromFile(smf).getLines.toList.map{ s =>
        if(new File(s).exists) 
          new File(s).toURI().toASCIIString()
        else if(new File(smf.getParentFile(), s).exists())
          new File(smf.getParentFile(), s).toURI.toASCIIString()
        else
          throw new RuntimeException("Couldn't locate %s from %s".format(s, smf))
      }
      projs += Project(smf.getName(), files)
    }
    projs.toList
  }
}

@RunWith(classOf[JUnitRunner])
class BakarTranslatorTestv2 extends BakarTestFileFramework {

  this.register(BakarExamples.getProjects(BakarProjectProvider, BakarExamplesAnchor.GNAT_2012_DIR, true))

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
      ),
      PipelineStage(
        "translator stage",
        false,
        BakarTranslatorModule)
    )

  override def generateExpected = false
  override def outputSuffix = "gvisitor"

  override def writeTestString(job : PipelineJob, w : Writer) = {
    val results = BakarTranslatorModule.getResults(job.properties)
    results foreach { v =>
      XStreamUtil.toXml(v, w)
      w.write("\n\n")
    }
  }
}