package org.sireum.pipeline.examples

import org.sireum.pipeline._
import org.sireum.util.Tag

class C1Def(val job : PipelineJob, info : PipelineJobModuleInfo) extends C1Module {
  println("Hello from C1Def: " + info.startTime)
  println("sources =  " + this.sources)

  this.sources = Left("output from C1Def")
}

class C3Def(val job : PipelineJob, info : PipelineJobModuleInfo) extends C3Module {
  println("Hello from C3Def")

  this.sources = Right("output from C3")
}

class C2Def(val job : PipelineJob, info : PipelineJobModuleInfo) extends C2Module {
  println("Hello from C2Def")
  println("Found " + this.sources)

  // c2 produces models and messages
  this.models_=(Seq[org.sireum.pilar.ast.Model]())
  this.messages_=(Seq[String]())
}

object testery {

  // good since parallel flag is false
  val pipeline1 = PipelineConfiguration(
    "pipeline config 1",
    false,
    PipelineStage(
      "stage 1",
      false,
      C1Module,
      C2Module
    )
  )

  val pipeline1_5 = PipelineConfiguration(
    "pipeline config 1.5",
    false,
    PipelineStage(
      "stage 1",
      false,
      C3Module
    ),
    PipelineStage(
      "stage 2",
      false,
      C1Module,
      C2Module
    )
  )

  // bad since parallel flag is true
  val pipeline2 = PipelineConfiguration(
    "pipeline config 2",
    false,
    PipelineStage(
      "stage 1",
      true,
      C1Module,
      C2Module
    )
  )

  // bad since C2 runs before C1
  val pipeline3 = PipelineConfiguration(
    "pipeline config",
    false,
    PipelineStage(
      "stage 1",
      false,
      C2Module,
      C1Module
    )
  )

  // good since c1 and c2 are in different stages
  val pipeline4 = PipelineConfiguration(
    "pipeline config",
    false,
    PipelineStage(
      "stage 1",
      true,
      C1Module
    ),
    PipelineStage(
      "stage 1",
      true,
      C2Module
    )
  )

  // bad since C2's stage runs before C1's stage 
  val pipeline5 = PipelineConfiguration(
    "pipeline config",
    false,
    PipelineStage(
      "stage 1",
      true,
      C2Module
    ),
    PipelineStage(
      "stage 1",
      true,
      C1Module
    )
  )

  def main(args : Array[String]) {
    val job = PipelineJob()
    C1Module.setSources(job.properties, Left("From external"))
    pipeline1_5.compute(job)
    //if(job.hasError){
      System.err.println(Tag.collateAsString(job.lastStageInfo.tags))
    //} 
  }
}
