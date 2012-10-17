package org.sireum.pipeline.examples

import org.sireum.util._
import org.sireum.pipeline._
import scala.Either
import java.lang.String
import org.sireum.pilar.ast.Model
import scala.collection.Seq

object C1Module extends PipelineModule {
  def title = "C1ModuleModule"
  def origin = classOf[C1]

  val sourcesKey = "C1.sources"
  val globalSourcesKey = "Global.sources"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.pipeline.examples.C1Def")
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

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ISeq[PipelineModule]()
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
    var _sources : scala.Option[AnyRef] = None
    var _sourcesKey : scala.Option[String] = None

    val keylistsources = List(C1Module.globalSourcesKey)
    keylistsources.foreach(key => 
      if(job ? key) { 
        if(_sources.isEmpty) {
          _sources = Some(job(key))
          _sourcesKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _sources.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sources' keys '" + _sourcesKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sources match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.Either[java.lang.String, java.lang.String]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sources'.  Expecting 'scala.Either[java.lang.String, java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sources'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? C1Module.sourcesKey) && !(job ? C1Module.globalSourcesKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'sources'. Expecting (C1Module.sourcesKey or C1Module.globalSourcesKey)") 
    }

    if(job ? C1Module.sourcesKey && !job(C1Module.sourcesKey).isInstanceOf[scala.Either[java.lang.String, java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for C1Module.sourcesKey.  Expecting 'scala.Either[java.lang.String, java.lang.String]' but found '" + 
        job(C1Module.sourcesKey).getClass.toString + "'")
    } 

    if(job ? C1Module.globalSourcesKey && !job(C1Module.globalSourcesKey).isInstanceOf[scala.Either[java.lang.String, java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for C1Module.globalSourcesKey.  Expecting 'scala.Either[java.lang.String, java.lang.String]' but found '" + 
        job(C1Module.globalSourcesKey).getClass.toString + "'")
    } 
    return tags
  }

  def modGetSources (options : scala.collection.Map[Property.Key, Any]) : scala.Either[java.lang.String, java.lang.String] = {
    if (options.contains(C1Module.globalSourcesKey)) {
       return options(C1Module.globalSourcesKey).asInstanceOf[scala.Either[java.lang.String, java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSources (options : MMap[Property.Key, Any], sources : scala.Either[java.lang.String, java.lang.String]) : MMap[Property.Key, Any] = {

    options(C1Module.globalSourcesKey) = sources

    return options
  }

  def getSources (options : scala.collection.Map[Property.Key, Any]) : scala.Either[java.lang.String, java.lang.String] = {
    if (options.contains(C1Module.globalSourcesKey)) {
       return options(C1Module.globalSourcesKey).asInstanceOf[scala.Either[java.lang.String, java.lang.String]]
    }
    if (options.contains(C1Module.sourcesKey)) {
       return options(C1Module.sourcesKey).asInstanceOf[scala.Either[java.lang.String, java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSources (options : MMap[Property.Key, Any], sources : scala.Either[java.lang.String, java.lang.String]) : MMap[Property.Key, Any] = {

    options(C1Module.globalSourcesKey) = sources
    options(C1Module.sourcesKey) = sources

    return options
  }
}

trait C1Module {
  def job : PipelineJob

  def sources : scala.Either[java.lang.String, java.lang.String] = C1Module.modGetSources(job.properties)


  def sources_=(sources : scala.Either[java.lang.String, java.lang.String]) { C1Module.modSetSources(job.properties, sources) }
}

object C3Module extends PipelineModule {
  def title = "C3ModuleModule"
  def origin = classOf[C3]

  val globalSourcesKey = "Global.sources"
  val sourcesKey = "C3.sources"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.pipeline.examples.C3Def")
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

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ISeq[PipelineModule]()
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
    var _sources : scala.Option[AnyRef] = None
    var _sourcesKey : scala.Option[String] = None

    val keylistsources = List(C3Module.globalSourcesKey)
    keylistsources.foreach(key => 
      if(job ? key) { 
        if(_sources.isEmpty) {
          _sources = Some(job(key))
          _sourcesKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _sources.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sources' keys '" + _sourcesKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sources match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.Either[java.lang.String, java.lang.String]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sources'.  Expecting 'scala.Either[java.lang.String, java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sources'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? C3Module.sourcesKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'sources'. Expecting (C3Module.sourcesKey)") 
    }

    if(job ? C3Module.sourcesKey && !job(C3Module.sourcesKey).isInstanceOf[scala.Either[java.lang.String, java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for C3Module.sourcesKey.  Expecting 'scala.Either[java.lang.String, java.lang.String]' but found '" + 
        job(C3Module.sourcesKey).getClass.toString + "'")
    } 
    return tags
  }

  def modGetSources (options : scala.collection.Map[Property.Key, Any]) : scala.Either[java.lang.String, java.lang.String] = {
    if (options.contains(C3Module.globalSourcesKey)) {
       return options(C3Module.globalSourcesKey).asInstanceOf[scala.Either[java.lang.String, java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSources (options : MMap[Property.Key, Any], sources : scala.Either[java.lang.String, java.lang.String]) : MMap[Property.Key, Any] = {

    options(C3Module.globalSourcesKey) = sources

    return options
  }

  def getSources (options : scala.collection.Map[Property.Key, Any]) : scala.Either[java.lang.String, java.lang.String] = {
    if (options.contains(C3Module.sourcesKey)) {
       return options(C3Module.sourcesKey).asInstanceOf[scala.Either[java.lang.String, java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSources (options : MMap[Property.Key, Any], sources : scala.Either[java.lang.String, java.lang.String]) : MMap[Property.Key, Any] = {

    options(C3Module.sourcesKey) = sources

    return options
  }
}

trait C3Module {
  def job : PipelineJob

  def sources : scala.Either[java.lang.String, java.lang.String] = C3Module.modGetSources(job.properties)


  def sources_=(sources : scala.Either[java.lang.String, java.lang.String]) { C3Module.modSetSources(job.properties, sources) }
}

object C2Module extends PipelineModule {
  def title = "C2ModuleModule"
  def origin = classOf[C2]

  val modelsKey = "C2.models"
  val messagesKey = "C2.messages"
  val globalSourcesKey = "Global.sources"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.pipeline.examples.C2Def")
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

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ISeq[PipelineModule](C1Module, C3Module)
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
    var _sources : scala.Option[AnyRef] = None
    var _sourcesKey : scala.Option[String] = None

    val keylistsources = List(C2Module.globalSourcesKey, C1Module.sourcesKey, C3Module.sourcesKey)
    keylistsources.foreach(key => 
      if(job ? key) { 
        if(_sources.isEmpty) {
          _sources = Some(job(key))
          _sourcesKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _sources.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sources' keys '" + _sourcesKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sources match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.Either[java.lang.String, java.lang.String]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sources'.  Expecting 'scala.Either[java.lang.String, java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sources'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? C2Module.modelsKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'models'. Expecting (C2Module.modelsKey)") 
    }

    if(job ? C2Module.modelsKey && !job(C2Module.modelsKey).isInstanceOf[scala.collection.Seq[org.sireum.pilar.ast.Model]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for C2Module.modelsKey.  Expecting 'scala.collection.Seq[org.sireum.pilar.ast.Model]' but found '" + 
        job(C2Module.modelsKey).getClass.toString + "'")
    } 

    if(!(job ? C2Module.messagesKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'messages'. Expecting (C2Module.messagesKey)") 
    }

    if(job ? C2Module.messagesKey && !job(C2Module.messagesKey).isInstanceOf[scala.collection.Seq[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for C2Module.messagesKey.  Expecting 'scala.collection.Seq[java.lang.String]' but found '" + 
        job(C2Module.messagesKey).getClass.toString + "'")
    } 

    if(!(job ? C2Module.globalSourcesKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'sources'. Expecting (C2Module.globalSourcesKey)") 
    }

    if(job ? C2Module.globalSourcesKey && !job(C2Module.globalSourcesKey).isInstanceOf[scala.Either[java.lang.String, java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for C2Module.globalSourcesKey.  Expecting 'scala.Either[java.lang.String, java.lang.String]' but found '" + 
        job(C2Module.globalSourcesKey).getClass.toString + "'")
    } 
    return tags
  }

  def getModels (options : scala.collection.Map[Property.Key, Any]) : scala.collection.Seq[org.sireum.pilar.ast.Model] = {
    if (options.contains(C2Module.modelsKey)) {
       return options(C2Module.modelsKey).asInstanceOf[scala.collection.Seq[org.sireum.pilar.ast.Model]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetModels (options : MMap[Property.Key, Any], models : scala.collection.Seq[org.sireum.pilar.ast.Model]) : MMap[Property.Key, Any] = {

    options(C2Module.modelsKey) = models

    return options
  }

  def getMessages (options : scala.collection.Map[Property.Key, Any]) : scala.collection.Seq[java.lang.String] = {
    if (options.contains(C2Module.messagesKey)) {
       return options(C2Module.messagesKey).asInstanceOf[scala.collection.Seq[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetMessages (options : MMap[Property.Key, Any], messages : scala.collection.Seq[java.lang.String]) : MMap[Property.Key, Any] = {

    options(C2Module.messagesKey) = messages

    return options
  }

  def modGetSources (options : scala.collection.Map[Property.Key, Any]) : scala.Either[java.lang.String, java.lang.String] = {
    if (options.contains(C2Module.globalSourcesKey)) {
       return options(C2Module.globalSourcesKey).asInstanceOf[scala.Either[java.lang.String, java.lang.String]]
    }
    if (options.contains(C1Module.sourcesKey)) {
       return options(C1Module.sourcesKey).asInstanceOf[scala.Either[java.lang.String, java.lang.String]]
    }
    if (options.contains(C3Module.sourcesKey)) {
       return options(C3Module.sourcesKey).asInstanceOf[scala.Either[java.lang.String, java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSources (options : MMap[Property.Key, Any], sources : scala.Either[java.lang.String, java.lang.String]) : MMap[Property.Key, Any] = {

    options(C2Module.globalSourcesKey) = sources
    options(C1Module.sourcesKey) = sources
    options(C3Module.sourcesKey) = sources

    return options
  }

  def getSources (options : scala.collection.Map[Property.Key, Any]) : scala.Either[java.lang.String, java.lang.String] = {
    if (options.contains(C2Module.globalSourcesKey)) {
       return options(C2Module.globalSourcesKey).asInstanceOf[scala.Either[java.lang.String, java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSources (options : MMap[Property.Key, Any], sources : scala.Either[java.lang.String, java.lang.String]) : MMap[Property.Key, Any] = {

    options(C2Module.globalSourcesKey) = sources

    return options
  }
}

trait C2Module {
  def job : PipelineJob


  def models_=(models : scala.collection.Seq[org.sireum.pilar.ast.Model]) { C2Module.modSetModels(job.properties, models) }


  def messages_=(messages : scala.collection.Seq[java.lang.String]) { C2Module.modSetMessages(job.properties, messages) }

  def sources : scala.Either[java.lang.String, java.lang.String] = C2Module.modGetSources(job.properties)


  def sources_=(sources : scala.Either[java.lang.String, java.lang.String]) { C2Module.modSetSources(job.properties, sources) }
}