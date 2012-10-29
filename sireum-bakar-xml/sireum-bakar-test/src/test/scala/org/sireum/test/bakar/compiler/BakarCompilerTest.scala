package org.sireum.test.bakar.compiler

import java.io.Writer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.sireum.example.bakar.BakarExamples
import org.sireum.example.bakar.BakarExamplesAnchor
import org.sireum.pipeline.PipelineConfiguration
import org.sireum.pipeline.PipelineJob
import org.sireum.pipeline.PipelineStage
import org.sireum.test.bakar.framework.BakarTestFileFramework
import org.sireum.util.FileUtil
import org.sireum.bakar.compiler.option.OptionFactory
import org.sireum.core.module.PilarSourcesModule
import org.sireum.core.module.PilarParserModule
import org.sireum.bakar.compiler.module.BakarCompilerModule
import org.sireum.util.ISeq
import org.sireum.util.FileResourceUri

@RunWith(classOf[JUnitRunner])
class BakarCompilerTest extends BakarTestFileFramework {
  //includes += "BakarCompiler_base_0_array_set_simple_array_set.cfg".r
  //includes += "array".r
  //excludes += "record".r 
  //excludes += "BakarCompiler_base_0_array_set_simple_array_set.cfg".r

  this.register(BakarExamples.exampleMap(BakarExamplesAnchor.BASE_DIR, "BakarCompiler", true))

  override def accept(name : String, files : ISeq[FileResourceUri]) : Boolean = {
    if (name.contains("microwave")) {
      val maxHeapSize = Runtime.getRuntime.maxMemory
      if (maxHeapSize < 264241152) {
        Console.err.println("Ignoring: " + name)
        Console.err.println("You'll need to add the vmarg -Xmx256m to run this."
          + " Currently set to " + maxHeapSize)
        Console.err.flush
        return false
      }
    }
    return super.accept(name, files)
  }

  override def pre(c : Configuration) : Boolean = {
    val sco = OptionFactory.newSparkCompilerOptions(BakarExamples.UriToPath(c.sources))
    sco.setOptOutputPath(c.resultsDir.toURI.getPath + "/")
    sco.setRegressionTesting(true)
    BakarCompilerModule.setSparkCompilerOptions(c.job.properties, sco);
    return super.pre(c)
  }

  override def pipeline =
    PipelineConfiguration(
      "Bakar Compiler Test Pipeline",
      false,
      PipelineStage(
        "Bakar Compiler Stage",
        false,
        BakarCompilerModule
      )
    )

  override def generateExpected = false
  override def outputSuffix = "bct"

  override def writeTestString(job : PipelineJob, w : Writer) = {
    val sources = BakarCompilerModule.getSources(job.properties)
    sources.foreach(f => {
      //val (c, _) = FileUtil.readFile(f.right.get)
      val (c, _) = FileUtil.readFile(f)
      w.write(c)
    })
  }
}
