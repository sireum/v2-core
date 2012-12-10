package org.sireum.test.bakar.alir

import java.io.Writer
import org.junit.runner.RunWith
import org.sireum.bakar.alir.module.BakarNodeDefRefModule
import org.sireum.bakar.compiler.v1.module.BakarCompilerv1Module
import org.sireum.bakar.compiler.v1.option.OptionFactory
import org.sireum.core.module.PilarSymbolResolverModule
import org.sireum.example.bakar._
import org.sireum.pipeline._
import org.sireum.test.bakar.framework.BakarTestFileFramework
import org.scalatest.junit.JUnitRunner

object BakarAlirExamples {
  val ALIR = "alir/"
  val ALIR_DIR = BakarExamples.sourceDirUri(BakarExamplesAnchor.getClass, 
      "./"+ALIR)
}

@RunWith(classOf[JUnitRunner])
class BakarNodeDefRefTest extends BakarTestFileFramework {
//includes += "-test.ada"  
//includes += "inter"  
 excludes += "bad".r
// includes += "record" 
//  includes += "array" 
this.register(BakarExamples.exampleMap(BakarAlirExamples.ALIR_DIR,
     "BakarAlir", true))

override def pre(c : Configuration) : Boolean = {
    val sco = OptionFactory.newSparkCompilerOptions(BakarExamples.UriToPath(c.sources))
    sco.setOptOutputPath(c.resultsDir.toURI.getPath + "/")
    sco.setRegressionTesting(true)
    BakarCompilerv1Module.setSparkCompilerOptions(c.job.properties, sco);
	PilarSymbolResolverModule.setHasExistingModels(c.job.properties, None)
    PilarSymbolResolverModule.setHasExistingSymbolTable(c.job.properties, None)
	PilarSymbolResolverModule.setParallel(c.job.properties, false)    
	return super.pre(c)
  }
  
  override def pipeline =
    PipelineConfiguration(
      "Bakar Compiler Test Pipeline",
      false,
      PipelineStage(
        "Bakar Compiler Stage",
        false,
        BakarCompilerv1Module
      ),
	  PipelineStage(
		"Pilar Symbol Resolution",
		false,
		PilarSymbolResolverModule),
	  PipelineStage(
		"Bakar Alir Intraprocedural Module",
		false,
		BakarNodeDefRefModule)
    )

  override def generateExpected = false
  
  override def outputSuffix = "ndf"

  override def writeTestString(job : PipelineJob, w : Writer) = {
    val result = BakarNodeDefRefModule.getResults(job)
    w.write(result)
  }
}
