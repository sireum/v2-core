package org.sireum.bakar.compiler.module

import org.sireum.util._
import org.sireum.pipeline._
import java.lang.String
import org.sireum.bakar.xml.CompilationUnit
import org.sireum.pilar.ast.Model
import scala.collection.Seq
import scala.collection.mutable.Map

object BakarTranslatorModule extends PipelineModule {
  def title = "Bakar Vistor"
  def origin = classOf[BakarTranslator]

  val globalParseGnat2XMLresultsKey = "Global.parseGnat2XMLresults"
  val resultsKey = "BakarTranslator.results"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.compiler.module.BakarTranslatorDef")
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

    val keylistparseGnat2XMLresults = List(BakarTranslatorModule.globalParseGnat2XMLresultsKey)
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
    if(!(job ? BakarTranslatorModule.resultsKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'results'. Expecting (BakarTranslatorModule.resultsKey)") 
    }

    if(job ? BakarTranslatorModule.resultsKey && !job(BakarTranslatorModule.resultsKey).isInstanceOf[scala.collection.Seq[org.sireum.pilar.ast.Model]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for BakarTranslatorModule.resultsKey.  Expecting 'scala.collection.Seq[org.sireum.pilar.ast.Model]' but found '" + 
        job(BakarTranslatorModule.resultsKey).getClass.toString + "'")
    } 
    return tags
  }

  def modGetParseGnat2XMLresults (options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[java.lang.String, org.sireum.bakar.xml.CompilationUnit] = {
    if (options.contains(BakarTranslatorModule.globalParseGnat2XMLresultsKey)) {
       return options(BakarTranslatorModule.globalParseGnat2XMLresultsKey).asInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.bakar.xml.CompilationUnit]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setParseGnat2XMLresults (options : MMap[Property.Key, Any], parseGnat2XMLresults : scala.collection.mutable.Map[java.lang.String, org.sireum.bakar.xml.CompilationUnit]) : MMap[Property.Key, Any] = {

    options(BakarTranslatorModule.globalParseGnat2XMLresultsKey) = parseGnat2XMLresults

    return options
  }

  def getResults (options : scala.collection.Map[Property.Key, Any]) : scala.collection.Seq[org.sireum.pilar.ast.Model] = {
    if (options.contains(BakarTranslatorModule.resultsKey)) {
       return options(BakarTranslatorModule.resultsKey).asInstanceOf[scala.collection.Seq[org.sireum.pilar.ast.Model]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetResults (options : MMap[Property.Key, Any], results : scala.collection.Seq[org.sireum.pilar.ast.Model]) : MMap[Property.Key, Any] = {

    options(BakarTranslatorModule.resultsKey) = results

    return options
  }
}

trait BakarTranslatorModule {
  def job : PipelineJob

  def parseGnat2XMLresults : scala.collection.mutable.Map[java.lang.String, org.sireum.bakar.xml.CompilationUnit] = BakarTranslatorModule.modGetParseGnat2XMLresults(job.properties)


  def results_=(results : scala.collection.Seq[org.sireum.pilar.ast.Model]) { BakarTranslatorModule.modSetResults(job.properties, results) }
}