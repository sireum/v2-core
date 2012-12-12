package org.sireum.bakar.xml.module

import org.sireum.util._
import org.sireum.pipeline._
import java.lang.String
import org.sireum.bakar.xml.CompilationUnit
import scala.collection.mutable.Map

object BakarVisitorModule extends PipelineModule {
  def title = "Bakar Vistor"
  def origin = classOf[BakarVisitor]

  val resultsKey = "BakarVisitor.results"
  val globalParseGnat2XMLresultsKey = "Global.parseGnat2XMLresults"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.xml.module.BakarVisitorDef")
      val cons = module.getConstructors()(0)
      val params = Array[AnyRef](job, info)
      val inst = cons.newInstance(params : _*)
    } catch {
      case e =>
        e.printStackTrace
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, e.getMessage);
    }
    return tags
  }

  override def initialize(job : PipelineJob) {
  }

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ilist[PipelineModule]()
    deps.foreach(d =>
      if(stage.modules.contains(d)){
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "'" + this.title + "' depends on '" + d.title + "' yet both were found in stage '" + stage.title + "'"
        )
      }
    )
    return tags
  }

  def inputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    var _parseGnat2XMLresults : scala.Option[AnyRef] = None
    var _parseGnat2XMLresultsKey : scala.Option[String] = None

    val keylistparseGnat2XMLresults = List(BakarVisitorModule.globalParseGnat2XMLresultsKey)
    keylistparseGnat2XMLresults.foreach(key => 
      if(job ? key) { 
        if(_parseGnat2XMLresults.isEmpty) {
          _parseGnat2XMLresults = Some(job(key))
          _parseGnat2XMLresultsKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _parseGnat2XMLresults.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'parseGnat2XMLresults' keys '" + _parseGnat2XMLresultsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _parseGnat2XMLresults match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.bakar.xml.CompilationUnit]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'parseGnat2XMLresults'.  Expecting 'scala.collection.mutable.Map[java.lang.String, org.sireum.bakar.xml.CompilationUnit]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'parseGnat2XMLresults'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? BakarVisitorModule.resultsKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'results'. Expecting (BakarVisitorModule.resultsKey)") 
    }

    if(job ? BakarVisitorModule.resultsKey && !job(BakarVisitorModule.resultsKey).isInstanceOf[scala.Boolean]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for BakarVisitorModule.resultsKey.  Expecting 'scala.Boolean' but found '" + 
        job(BakarVisitorModule.resultsKey).getClass.toString + "'")
    } 
    return tags
  }

  def modGetParseGnat2XMLresults (options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[java.lang.String, org.sireum.bakar.xml.CompilationUnit] = {
    if (options.contains(BakarVisitorModule.globalParseGnat2XMLresultsKey)) {
       return options(BakarVisitorModule.globalParseGnat2XMLresultsKey).asInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.bakar.xml.CompilationUnit]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setParseGnat2XMLresults (options : MMap[Property.Key, Any], parseGnat2XMLresults : scala.collection.mutable.Map[java.lang.String, org.sireum.bakar.xml.CompilationUnit]) : MMap[Property.Key, Any] = {

    options(BakarVisitorModule.globalParseGnat2XMLresultsKey) = parseGnat2XMLresults

    return options
  }

  def getResults (options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(BakarVisitorModule.resultsKey)) {
       return options(BakarVisitorModule.resultsKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetResults (options : MMap[Property.Key, Any], results : scala.Boolean) : MMap[Property.Key, Any] = {

    options(BakarVisitorModule.resultsKey) = results

    return options
  }
}

trait BakarVisitorModule {
  def job : PipelineJob

  def parseGnat2XMLresults : scala.collection.mutable.Map[java.lang.String, org.sireum.bakar.xml.CompilationUnit] = BakarVisitorModule.modGetParseGnat2XMLresults(job.properties)


  def results_=(results : scala.Boolean) { BakarVisitorModule.modSetResults(job.properties, results) }
}