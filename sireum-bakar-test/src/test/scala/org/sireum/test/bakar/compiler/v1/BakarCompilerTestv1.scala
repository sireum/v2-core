package org.sireum.test.bakar.compiler.v1

import java.io.Writer
import org.junit.runner.RunWith
import org.sireum.example.bakar.BakarExamples
import org.sireum.example.bakar.BakarExamplesAnchor
import org.sireum.pipeline.PipelineJob
import org.sireum.test.bakar.framework.BakarTestFileFramework
import org.sireum.util.FileUtil
import org.sireum.bakar.compiler.v1.option.OptionFactory
import org.sireum.bakar.compiler.v1.module.BakarCompilerv1Module
import org.sireum.util.ISeq
import org.sireum.util.FileResourceUri
import org.sireum.pipeline.PipelineConfiguration.apply
import org.sireum.pipeline.PipelineStage.apply
import org.scalatest.junit.JUnitRunner
import org.sireum.pipeline.PipelineConfiguration
import org.sireum.pipeline.PipelineStage

@RunWith(classOf[JUnitRunner])
class BakarCompilerTestv1 extends BakarTestFileFramework {
  //includes += "BakarCompiler_base_0_array_set_simple_array_set.cfg".r
  //includes += "ArraySet.adb".r
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
    BakarCompilerv1Module.setSparkCompilerOptions(c.job.properties, sco);
    return super.pre(c)
  }

  override def pipeline =
    PipelineConfiguration(
      "Bakar Compiler v1 Test Pipeline",
      false,
      PipelineStage(
        "Bakar Compiler v1 Stage",
        false,
        BakarCompilerv1Module
      )
    )

  override def generateExpected = false
  override def outputSuffix = "bct"

  override def writeTestString(job : PipelineJob, w : Writer) = {
    val sources = BakarCompilerv1Module.getSources(job.properties).sortWith(_ < _)
    sources.foreach(f => {
      //val (c, _) = FileUtil.readFile(f.right.get)
      val (c, _) = FileUtil.readFile(f)
      w.write(c)
    })
  }
}
