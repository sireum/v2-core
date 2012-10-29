package org.sireum.bakar.xml.module

import org.sireum.util._
import org.sireum.pipeline._
import java.lang.String
import scala.Option
import scala.collection.Seq
import scala.collection.mutable.Map

object Gnat2XMLWrapperModule extends PipelineModule {
  def title = "Gnat2XML Wrapper Module"
  def origin = classOf[Gnat2XMLWrapper]

  val gnat2xmlResultsKey = "Gnat2XMLWrapper.gnat2xmlResults"
  val globalDestDirKey = "Global.destDir"
  val globalSrcFilesKey = "Global.srcFiles"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.xml.module.Gnat2XMLWrapperDef")
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
    if(!(job ? Gnat2XMLWrapperModule.globalDestDirKey)) {
      val destDir = Class.forName("org.sireum.bakar.xml.module.Gnat2XMLWrapper").getDeclaredMethod("init$default$3").invoke(null).asInstanceOf[scala.Option[java.lang.String]]
      setDestDir(job.properties, destDir)
    }
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
    var _srcFiles : scala.Option[AnyRef] = None
    var _srcFilesKey : scala.Option[String] = None

    val keylistsrcFiles = List(Gnat2XMLWrapperModule.globalSrcFilesKey)
    keylistsrcFiles.foreach(key => 
      if(job ? key) { 
        if(_srcFiles.isEmpty) {
          _srcFiles = Some(job(key))
          _srcFilesKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _srcFiles.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'srcFiles' keys '" + _srcFilesKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _srcFiles match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.collection.Seq[java.lang.String]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'srcFiles'.  Expecting 'scala.collection.Seq[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'srcFiles'")       
    }
    var _destDir : scala.Option[AnyRef] = None
    var _destDirKey : scala.Option[String] = None

    val keylistdestDir = List(Gnat2XMLWrapperModule.globalDestDirKey)
    keylistdestDir.foreach(key => 
      if(job ? key) { 
        if(_destDir.isEmpty) {
          _destDir = Some(job(key))
          _destDirKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _destDir.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'destDir' keys '" + _destDirKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _destDir match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.Option[java.lang.String]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'destDir'.  Expecting 'scala.Option[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'destDir'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? Gnat2XMLWrapperModule.gnat2xmlResultsKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'gnat2xmlResults'. Expecting (Gnat2XMLWrapperModule.gnat2xmlResultsKey)") 
    }

    if(job ? Gnat2XMLWrapperModule.gnat2xmlResultsKey && !job(Gnat2XMLWrapperModule.gnat2xmlResultsKey).isInstanceOf[scala.collection.Seq[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for Gnat2XMLWrapperModule.gnat2xmlResultsKey.  Expecting 'scala.collection.Seq[java.lang.String]' but found '" + 
        job(Gnat2XMLWrapperModule.gnat2xmlResultsKey).getClass.toString + "'")
    } 
    return tags
  }

  def getGnat2xmlResults (options : scala.collection.Map[Property.Key, Any]) : scala.collection.Seq[java.lang.String] = {
    if (options.contains(Gnat2XMLWrapperModule.gnat2xmlResultsKey)) {
       return options(Gnat2XMLWrapperModule.gnat2xmlResultsKey).asInstanceOf[scala.collection.Seq[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetGnat2xmlResults (options : MMap[Property.Key, Any], gnat2xmlResults : scala.collection.Seq[java.lang.String]) : MMap[Property.Key, Any] = {

    options(Gnat2XMLWrapperModule.gnat2xmlResultsKey) = gnat2xmlResults

    return options
  }

  def modGetSrcFiles (options : scala.collection.Map[Property.Key, Any]) : scala.collection.Seq[java.lang.String] = {
    if (options.contains(Gnat2XMLWrapperModule.globalSrcFilesKey)) {
       return options(Gnat2XMLWrapperModule.globalSrcFilesKey).asInstanceOf[scala.collection.Seq[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSrcFiles (options : MMap[Property.Key, Any], srcFiles : scala.collection.Seq[java.lang.String]) : MMap[Property.Key, Any] = {

    options(Gnat2XMLWrapperModule.globalSrcFilesKey) = srcFiles

    return options
  }

  def modGetDestDir (options : scala.collection.Map[Property.Key, Any]) : scala.Option[java.lang.String] = {
    if (options.contains(Gnat2XMLWrapperModule.globalDestDirKey)) {
       return options(Gnat2XMLWrapperModule.globalDestDirKey).asInstanceOf[scala.Option[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setDestDir (options : MMap[Property.Key, Any], destDir : scala.Option[java.lang.String]) : MMap[Property.Key, Any] = {

    options(Gnat2XMLWrapperModule.globalDestDirKey) = destDir

    return options
  }
}

trait Gnat2XMLWrapperModule {
  def job : PipelineJob


  def gnat2xmlResults_=(gnat2xmlResults : scala.collection.Seq[java.lang.String]) { Gnat2XMLWrapperModule.modSetGnat2xmlResults(job.properties, gnat2xmlResults) }

  def srcFiles : scala.collection.Seq[java.lang.String] = Gnat2XMLWrapperModule.modGetSrcFiles(job.properties)

  def destDir : scala.Option[java.lang.String] = Gnat2XMLWrapperModule.modGetDestDir(job.properties)
}

object ParseGnat2XMLModule extends PipelineModule {
  def title = "ParseGnat2XML Module"
  def origin = classOf[ParseGnat2XML]

  val parseGnat2XMLresultsKey = "ParseGnat2XML.parseGnat2XMLresults"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.xml.module.ParseGnat2XMLDef")
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
    val deps = ilist[PipelineModule](Gnat2XMLWrapperModule)
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
    var _gnat2xmlResults : scala.Option[AnyRef] = None
    var _gnat2xmlResultsKey : scala.Option[String] = None

    val keylistgnat2xmlResults = List(Gnat2XMLWrapperModule.gnat2xmlResultsKey)
    keylistgnat2xmlResults.foreach(key => 
      if(job ? key) { 
        if(_gnat2xmlResults.isEmpty) {
          _gnat2xmlResults = Some(job(key))
          _gnat2xmlResultsKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _gnat2xmlResults.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'gnat2xmlResults' keys '" + _gnat2xmlResultsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _gnat2xmlResults match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.collection.Seq[java.lang.String]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'gnat2xmlResults'.  Expecting 'scala.collection.Seq[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'gnat2xmlResults'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? ParseGnat2XMLModule.parseGnat2XMLresultsKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'parseGnat2XMLresults'. Expecting (ParseGnat2XMLModule.parseGnat2XMLresultsKey)") 
    }

    if(job ? ParseGnat2XMLModule.parseGnat2XMLresultsKey && !job(ParseGnat2XMLModule.parseGnat2XMLresultsKey).isInstanceOf[scala.collection.mutable.Map[java.lang.String, java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for ParseGnat2XMLModule.parseGnat2XMLresultsKey.  Expecting 'scala.collection.mutable.Map[java.lang.String, java.lang.String]' but found '" + 
        job(ParseGnat2XMLModule.parseGnat2XMLresultsKey).getClass.toString + "'")
    } 
    return tags
  }

  def modGetGnat2xmlResults (options : scala.collection.Map[Property.Key, Any]) : scala.collection.Seq[java.lang.String] = {
    if (options.contains(Gnat2XMLWrapperModule.gnat2xmlResultsKey)) {
       return options(Gnat2XMLWrapperModule.gnat2xmlResultsKey).asInstanceOf[scala.collection.Seq[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setGnat2xmlResults (options : MMap[Property.Key, Any], gnat2xmlResults : scala.collection.Seq[java.lang.String]) : MMap[Property.Key, Any] = {

    options(Gnat2XMLWrapperModule.gnat2xmlResultsKey) = gnat2xmlResults

    return options
  }

  def getParseGnat2XMLresults (options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[java.lang.String, java.lang.String] = {
    if (options.contains(ParseGnat2XMLModule.parseGnat2XMLresultsKey)) {
       return options(ParseGnat2XMLModule.parseGnat2XMLresultsKey).asInstanceOf[scala.collection.mutable.Map[java.lang.String, java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetParseGnat2XMLresults (options : MMap[Property.Key, Any], parseGnat2XMLresults : scala.collection.mutable.Map[java.lang.String, java.lang.String]) : MMap[Property.Key, Any] = {

    options(ParseGnat2XMLModule.parseGnat2XMLresultsKey) = parseGnat2XMLresults

    return options
  }
}

trait ParseGnat2XMLModule {
  def job : PipelineJob

  def gnat2xmlResults : scala.collection.Seq[java.lang.String] = ParseGnat2XMLModule.modGetGnat2xmlResults(job.properties)


  def parseGnat2XMLresults_=(parseGnat2XMLresults : scala.collection.mutable.Map[java.lang.String, java.lang.String]) { ParseGnat2XMLModule.modSetParseGnat2XMLresults(job.properties, parseGnat2XMLresults) }
}