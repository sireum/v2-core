package org.sireum.test.bakar.kiasan

import java.io.File
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.sireum.bakar.compiler.module.BakarTranslatorModule
import org.sireum.bakar.xml.module.Gnat2XMLWrapperModule
import org.sireum.bakar.xml.module.ParseGnat2XMLModule
import org.sireum.example.bakar.BakarExamples
import org.sireum.example.bakar.BakarExamplesAnchor
import org.sireum.pipeline.PipelineConfiguration
import org.sireum.pipeline.PipelineJob
import org.sireum.pipeline.PipelineStage
import org.sireum.test.bakar.framework.BakarSmfProjectProvider
import org.sireum.test.bakar.framework.BakarTestFramework
import org.sireum.test.framework.TestFramework
import org.sireum.util._
import com.google.common.io.Files
import org.sireum.pilar.ast.BinaryExp

@RunWith(classOf[JUnitRunner])
class BakarKiasanExpTest extends TestFramework {

  val pipeline =
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

  val projs = BakarExamples.getProjects(BakarSmfProjectProvider, BakarExamplesAnchor.GNAT_2012_DIR, true)
  projs.foreach { p =>
    val job = PipelineJob()

    val tempDir = Some(FileUtil.toUri(Files.createTempDir()))
    Gnat2XMLWrapperModule.setSrcFiles(job.properties, p.files)
    Gnat2XMLWrapperModule.setDestDir(job.properties, tempDir)

    pipeline.compute(job)

    val tags = if (!job.tags.isEmpty) job.tags else job.lastStageInfo.tags
    if (!tags.isEmpty) {
      println("Error encountered while processing " + p.projectName)
      println(tags)
    } else {
      BakarTranslatorModule.getResults(job.properties) foreach { v =>
        // now find all the binary exp and build a test
        var i = 0
        Visitor.build({
          case o : BinaryExp =>
            test("test" + i) {
              println(o)
            }
            i += 1
            false
        })(v)
      }
    }
  }
}