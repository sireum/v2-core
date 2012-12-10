package org.sireum.bakar.compiler.v1.module

import org.sireum.pipeline.PipelineConfiguration
import org.sireum.pipeline.PipelineJob
import org.sireum.pipeline.PipelineJobModuleInfo
import org.sireum.pipeline.PipelineStage
import org.sireum.core.module.PilarParserModule
import org.sireum.core.module.PilarSourcesModule
import org.sireum.bakar.compiler.v1.module._
import org.sireum.bakar.compiler.v1.module.BakarTranslatorv1Module
import org.sireum.bakar.compiler.v1.module.BakarParserv1Module
import org.sireum.bakar.compiler.v1.module.BakarPackageDependencyAnalysisv1Module
import org.sireum.bakar.compiler.v1.module.BakarCompilerv1Module

class BakarCompilerv1Def(val job : PipelineJob, info : PipelineJobModuleInfo) extends BakarCompilerv1Module {

  val subpipeline1 = PipelineConfiguration(
    "Bakar Compiler Pipeline",
    false,
    PipelineStage(
      "Bakar Parsing Stage",
      false,
      BakarParserv1Module),
    PipelineStage(
      "Bakar Dependency Analysis Stage",
      false,
      BakarPackageDependencyAnalysisv1Module),
    PipelineStage(
      "Bakar to Pilar Translator Stage",
      false,
      BakarTranslatorv1Module
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
  val results = BakarTranslatorv1Module.getSources(job.properties)

  PilarSourcesModule.setSources(job.properties, results.map(f => Right("file://" + f)))
  subpipeline2.compute(job)

  this.models_=(PilarParserModule.getModels(job.properties))
  this.sources_=(results.map(f => "file://" + f))
}