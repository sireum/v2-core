package org.sireum.test.bakar.alir

import org.junit.runner.RunWith
import org.sireum.bakar.compiler.v1.option.OptionFactory
import org.sireum.bakar.compiler.v1.module.BakarCompilerv1Module
import org.sireum.core.module.PilarParserModule
import org.sireum.pipeline.PipelineConfiguration
import org.sireum.pipeline.PipelineJob
import org.sireum.pipeline.PipelineStage
import org.sireum.test.framework.TestFramework
import org.sireum.util.FileResourceUri
import org.sireum.util.Tag
import org.scalatest.junit.JUnitRunner
import org.sireum.example.bakar.BakarExamplesAnchor
import org.sireum.example.bakar.BakarExamples

/*
object BakarAlirExamples extends BakarExamples {
  val MISC_DIR = sourceDirUri(BakarExamplesAnchor.getClass, "./base/0/misc")
  val ARRAYS_DIR = sourceDirUri(BakarExamplesAnchor.getClass, "./base/0/arrays")

  val EXPECTED_DIR = getSourceDir(this.getClass, "src/test/scala") + "/expected/"
  val RESULTS_DIR = getSourceDir(this.getClass, "src/test/scala") + "/results/"

  def getSourceDir(clazz : Class[_], loc : String) : String = {
    val cname = clazz.getSimpleName + ".class"
    val s = clazz.getResource(cname).getPath.replace(cname, "").replaceFirst("bin", loc)
    return s
  }
}

@RunWith(classOf[JUnitRunner])
class BakarAlirIntraproceduralTest extends BakarAlirTestFramework{
  BakarAlirExamples.exampleFiles(BakarAlirExamples.MISC_DIR).foreach { files =>
    BakarCompiler.run(files)
  }

  BakarAlirExamples.exampleFiles(BakarAlirExamples.ARRAYS_DIR).foreach { files =>
    BakarCompiler.run(files)
  }
}

trait BakarAlirTestFramework extends TestFramework {
  def BakarCompiler : this.type = this

  def run(fileNames : ISeq[FileResourceUri]) {
    BakarConfiguration(fileNames)
  }

  var i = 0

  protected val pipeline =
    PipelineConfiguration(
      "Bakar Compiler Test Pipeline",
      false,
      PipelineStage(
        "Bakar Compiler Stage",
        false,
        BakarCompilerModule
      ),
      PipelineStage(
        "Pilar Parsing Stage",
        false,
        PilarParserModule
      )
    )

  case class BakarConfiguration(sources : ISeq[FileResourceUri]) {

    def testName = {
      val name = sources.head
      val subs = BakarExamplesAnchor.getClass.getResource("").toString
      "test_" + name.replace(subs, "").replaceAll("/", "_")
    }

    test(testName) {
      val job = PipelineJob()
      val sco = OptionFactory.newSparkCompilerOptions(BakarAlirExamples.UriToPath(sources))
      sco.setOptOutputPath(BakarAlirExamples.RESULTS_DIR)
      BakarCompilerModule.setCompilerOptions(job, sco);
      pipeline.compute(job)

      val tags = job.tags
      if (!tags.isEmpty) {
        println(Tag.collateAsString(tags))
      } else {
        //val models = job(PilarParserModule.MODEL_KEY).asInstanceOf[Seq[Model]]
        val models = PilarParserModule.getModels(job.properties)
      }
    }
  }
}
*/
