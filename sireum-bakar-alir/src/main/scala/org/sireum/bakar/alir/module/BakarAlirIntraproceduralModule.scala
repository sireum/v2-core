package org.sireum.bakar.alir.module

import org.sireum.util._
import org.sireum.pipeline._
import org.sireum.bakar.compiler.v1.module.BakarTranslatorv1Module
import org.sireum.bakar.compiler.v1.option.SparkCompilerOptions

object BakarAlirIntraproceduralModule {
  def title : String = "Bakar Alir Intraprocedural Module"

  val BAKAR_COMPILER_OPTIONS = "BAKAR_Alir_OPTIONS"
  val BAKAR_COMPILER_RESULTS = "BAKAR_Alir_RESULTS"
  val BAKAR_COMPILER_TAGS = "BAKAR_Alir_TAGS"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val sco = getCompilerOptions(job)
    val tags = marrayEmpty[Tag]

    //BakarTranslatorModule.pipeline.compute(job)

    //(BAKAR_COMPILER_TAGS) = tags
    marrayEmpty[Tag]
  }
  
  def inputDefined(job : PipelineJob) : MBuffer[Tag] = { marrayEmpty[Tag]}
  
  def outputDefined(job : PipelineJob) : MBuffer[Tag] = { marrayEmpty[Tag]}
  
  def setCompilerOptions(job : PipelineJob, sco : SparkCompilerOptions) {
    job(BAKAR_COMPILER_OPTIONS) = sco
  }

  def getCompilerOptions(job : PipelineJob) : SparkCompilerOptions = {
    return job(BAKAR_COMPILER_OPTIONS).asInstanceOf[SparkCompilerOptions]
  }
}     