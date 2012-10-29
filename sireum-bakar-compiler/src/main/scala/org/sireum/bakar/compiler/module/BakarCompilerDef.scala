package org.sireum.bakar.compiler.module

import org.sireum.pipeline.PipelineConfiguration
import org.sireum.pipeline.PipelineJob
import org.sireum.pipeline.PipelineJobModuleInfo
import org.sireum.pipeline.PipelineStage
import org.sireum.core.module.PilarParserModule
import org.sireum.core.module.PilarSourcesModule
import org.sireum.bakar.compiler.module._

class BakarCompilerDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends BakarCompilerModule {

  val subpipeline1 = PipelineConfiguration(
    "Bakar Compiler Pipeline",
    false,
    PipelineStage(
      "Bakar Parsing Stage",
      false,
      BakarParserModule),
    PipelineStage(
      "Bakar Dependency Analysis Stage",
      false,
      BakarPackageDependencyAnalysisModule),
    PipelineStage(
      "Bakar to Pilar Translator Stage",
      false,
      BakarTranslatorModule
    ))

  val subpipeline2 = PipelineConfiguration(
    "Pilar Parsing Stage",
    false,
    PipelineStage(
      "Pilar Source Stage",
      false,
      PilarSourcesModule
    ),
    PipelineStage(
      "Pilar Parsing Stage",
      false,
      PilarParserModule
    ))

  val sco = this.sparkCompilerOptions

  subpipeline1.compute(job)
  val results = BakarTranslatorModule.getSources(job.properties)

  PilarSourcesModule.setSources(job.properties, results.map(f => Right("file://" + f)))
  subpipeline2.compute(job)

  this.models_=(PilarParserModule.getModels(job.properties))
  this.sources_=(results.map(f => "file://" + f))
}