/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pipeline

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class PipelineConfiguration(
    title : String, 
    exclusive : Boolean, 
    stages : PipelineStage*) {

  def compute(job : PipelineJob) : PipelineJob = {

    def initialize(stage : PipelineStage) {
      stage.modules.foreach(m => m.initialize(job))
    }
    
    def preStage(stageInfo : PipelineJobStageInfo, stage : PipelineStage) : Boolean = {
      stage.modules.foreach(m => {
        stageInfo.tags ++= m.inputDefined(job)
        stageInfo.tags ++= m.validPipeline(stage, job)
      })
      return Tag.exists(PipelineUtil.ErrorMarker, {_ => true}, stageInfo.tags)
    }
    
    def postStage(stageInfo : PipelineJobStageInfo, stage : PipelineStage) : Boolean = {
      stage.modules.foreach(m => {
        stageInfo.tags ++= m.outputDefined(job)
      })
      return Tag.exists(PipelineUtil.ErrorMarker, {_ => true}, stageInfo.tags)
    }
    
      def run = {
        val j = if (stages.exists { _.parallel }) job.par else job

        var hasError = false
        for (stage <- stages if !j.hasInternalError && !j.hasError && !j.isCancelled && !hasError) {
          val stageInfo = PipelineJobStageInfo(stage.title, System.currentTimeMillis)
          initialize(stage)
          hasError = preStage(stageInfo, stage) 
          if(!hasError){
            stage.compute(j, stageInfo)
            hasError = postStage(stageInfo, stage)
          }
          stageInfo.endTime = System.currentTimeMillis
          j.info += stageInfo
        }
      }

    if (exclusive) PipelineConfiguration.this.synchronized { run }
    else run

    assert(!job.hasInternalError, job.exceptions.foreach { t => t.printStackTrace })

    job
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class PipelineStage(
    title : String,
    parallel : Boolean,
    modules : PipelineModule*) {
  def compute(job : PipelineJob, stageInfo : PipelineJobStageInfo) {
    val ms : GenSeq[PipelineModule] = if (parallel) modules.par else modules.toSeq

    ms.foreach { m =>
      val moduleInfo = PipelineJobModuleInfo(m.title, System.currentTimeMillis)

      try {
        m.compute(job, moduleInfo)
      } catch {
        case t : Throwable =>
          moduleInfo.hasInternalError = true
          moduleInfo.exception = Some(t)
      } finally {
        moduleInfo.endTime = System.currentTimeMillis
        stageInfo.info += moduleInfo
      }
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait PipelineModule {
  def title : String
  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag]

  def initialize(job : PipelineJob) {}
  def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = marrayEmpty[Tag]
  def inputDefined (job : PipelineJob) : MBuffer[Tag]
  def outputDefined (job : PipelineJob) : MBuffer[Tag]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object PipelineJob {
  def create = PipelineJob()
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class PipelineJob(
  timestamp : Long = System.currentTimeMillis, 
  var isCancelled : Boolean = false, 
  info : MArray[PipelineJobStageInfo] = marrayEmpty[PipelineJobStageInfo], 
  properties : MMap[Property.Key, Any] = mmapEmpty[Property.Key, Any])
    extends PropertyProvider {
  protected val propertyMap = properties

  def hasError =
    if (info.size == 0) false
    else lastStageInfo.hasError

  def hasInternalError =
    if (info.size == 0) false
    else lastStageInfo.hasInternalError

  def exceptions = {
    var result = marrayEmpty[Throwable]
    lastStageInfo.info.foreach { mi =>
      if (mi.exception.isDefined)
        result += mi.exception.get
    }
    result
  }

  def lastStageInfo : PipelineJobStageInfo = info(info.size - 1)

  def par = {
    val newProperties = cmapEmpty[Property.Key, Any]
    newProperties ++= properties
    PipelineJob(timestamp, isCancelled, info, newProperties)
  }

  def tags : ISeq[Tag] = {
    val result = marrayEmpty[Tag]
    for (si <- info)
      for (mi <- si.info)
        result ++= mi.tags
    result.toList
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class PipelineJobStageInfo(
    title : String,
    var startTime : Long,
    var endTime : Long = -1,
    val tags : MBuffer[Tag] = marrayEmpty[Tag],
    info : MArray[PipelineJobModuleInfo] = marrayEmpty[PipelineJobModuleInfo]) {

  def hasError = info.exists(_.hasError) || !tags.isEmpty

  def hasInternalError = info.exists(_.hasInternalError)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class PipelineJobModuleInfo(
  var title : String,
  var startTime : Long,
  var endTime : Long = -1,
  var hasError : Boolean = false,
  var hasInternalError : Boolean = false,
  val tags : MBuffer[Tag] = marrayEmpty[Tag],
  var exception : Option[Throwable] = None)

final case class ErrorneousModulesThrowable(
  val info : MArray[PipelineJobModuleInfo]) extends Throwable