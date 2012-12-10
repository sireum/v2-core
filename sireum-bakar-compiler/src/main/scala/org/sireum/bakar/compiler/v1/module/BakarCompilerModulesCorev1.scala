package org.sireum.bakar.compiler.v1.module

import org.sireum.util._
import org.sireum.pipeline._
import java.lang.String
import org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit
import org.sireum.bakar.compiler.v1.module.util.SPARKParserResult
import org.sireum.bakar.compiler.v1.option.SparkCompilerOptions
import org.sireum.pilar.ast.Model
import scala.collection.immutable.Seq

object BakarParserv1Module extends PipelineModule {
  def title = "Bakar Parser Module v1"
  def origin = classOf[BakarParserv1]

  val sparkParserResultKey = "BakarParserv1.sparkParserResult"
  val globalSparkCompilerOptionsKey = "Global.sparkCompilerOptions"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.compiler.v1.module.BakarParserv1Def")
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
    var _sparkCompilerOptions : scala.Option[AnyRef] = None
    var _sparkCompilerOptionsKey : scala.Option[String] = None

    val keylistsparkCompilerOptions = List(BakarParserv1Module.globalSparkCompilerOptionsKey)
    keylistsparkCompilerOptions.foreach(key => 
      if(job ? key) { 
        if(_sparkCompilerOptions.isEmpty) {
          _sparkCompilerOptions = Some(job(key))
          _sparkCompilerOptionsKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _sparkCompilerOptions.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sparkCompilerOptions' keys '" + _sparkCompilerOptionsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sparkCompilerOptions match{
      case Some(x) =>
        if(!x.isInstanceOf[org.sireum.bakar.compiler.v1.option.SparkCompilerOptions]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sparkCompilerOptions'.  Expecting 'org.sireum.bakar.compiler.v1.option.SparkCompilerOptions' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sparkCompilerOptions'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? BakarParserv1Module.sparkParserResultKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'sparkParserResult'. Expecting (BakarParserv1Module.sparkParserResultKey)") 
    }

    if(job ? BakarParserv1Module.sparkParserResultKey && !job(BakarParserv1Module.sparkParserResultKey).isInstanceOf[org.sireum.bakar.compiler.v1.module.util.SPARKParserResult]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for BakarParserv1Module.sparkParserResultKey.  Expecting 'org.sireum.bakar.compiler.v1.module.util.SPARKParserResult' but found '" + 
        job(BakarParserv1Module.sparkParserResultKey).getClass.toString + "'")
    } 
    return tags
  }

  def modGetSparkCompilerOptions (options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions = {
    if (options.contains(BakarParserv1Module.globalSparkCompilerOptionsKey)) {
       return options(BakarParserv1Module.globalSparkCompilerOptionsKey).asInstanceOf[org.sireum.bakar.compiler.v1.option.SparkCompilerOptions]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSparkCompilerOptions (options : MMap[Property.Key, Any], sparkCompilerOptions : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions) : MMap[Property.Key, Any] = {

    options(BakarParserv1Module.globalSparkCompilerOptionsKey) = sparkCompilerOptions

    return options
  }

  def getSparkParserResult (options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.v1.module.util.SPARKParserResult = {
    if (options.contains(BakarParserv1Module.sparkParserResultKey)) {
       return options(BakarParserv1Module.sparkParserResultKey).asInstanceOf[org.sireum.bakar.compiler.v1.module.util.SPARKParserResult]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSparkParserResult (options : MMap[Property.Key, Any], sparkParserResult : org.sireum.bakar.compiler.v1.module.util.SPARKParserResult) : MMap[Property.Key, Any] = {

    options(BakarParserv1Module.sparkParserResultKey) = sparkParserResult

    return options
  }
}

trait BakarParserv1Module {
  def job : PipelineJob

  def sparkCompilerOptions : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions = BakarParserv1Module.modGetSparkCompilerOptions(job.properties)


  def sparkParserResult_=(sparkParserResult : org.sireum.bakar.compiler.v1.module.util.SPARKParserResult) { BakarParserv1Module.modSetSparkParserResult(job.properties, sparkParserResult) }
}

object BakarPackageDependencyAnalysisv1Module extends PipelineModule {
  def title = "Bakar Package Dependency Analysis Module v1"
  def origin = classOf[BakarPackageDependencyAnalysisv1]

  val transOrderKey = "BakarPackageDependencyAnalysisv1.transOrder"
  val globalSparkCompilerOptionsKey = "Global.sparkCompilerOptions"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.compiler.v1.module.BakarPackageDependencyAnalysisv1Def")
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
    val deps = ilist[PipelineModule](BakarParserv1Module)
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
    var _sparkCompilerOptions : scala.Option[AnyRef] = None
    var _sparkCompilerOptionsKey : scala.Option[String] = None

    val keylistsparkCompilerOptions = List(BakarPackageDependencyAnalysisv1Module.globalSparkCompilerOptionsKey)
    keylistsparkCompilerOptions.foreach(key => 
      if(job ? key) { 
        if(_sparkCompilerOptions.isEmpty) {
          _sparkCompilerOptions = Some(job(key))
          _sparkCompilerOptionsKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _sparkCompilerOptions.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sparkCompilerOptions' keys '" + _sparkCompilerOptionsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sparkCompilerOptions match{
      case Some(x) =>
        if(!x.isInstanceOf[org.sireum.bakar.compiler.v1.option.SparkCompilerOptions]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sparkCompilerOptions'.  Expecting 'org.sireum.bakar.compiler.v1.option.SparkCompilerOptions' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sparkCompilerOptions'")       
    }
    var _sparkParserResult : scala.Option[AnyRef] = None
    var _sparkParserResultKey : scala.Option[String] = None

    val keylistsparkParserResult = List(BakarParserv1Module.sparkParserResultKey)
    keylistsparkParserResult.foreach(key => 
      if(job ? key) { 
        if(_sparkParserResult.isEmpty) {
          _sparkParserResult = Some(job(key))
          _sparkParserResultKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _sparkParserResult.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sparkParserResult' keys '" + _sparkParserResultKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sparkParserResult match{
      case Some(x) =>
        if(!x.isInstanceOf[org.sireum.bakar.compiler.v1.module.util.SPARKParserResult]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sparkParserResult'.  Expecting 'org.sireum.bakar.compiler.v1.module.util.SPARKParserResult' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sparkParserResult'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? BakarPackageDependencyAnalysisv1Module.transOrderKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'transOrder'. Expecting (BakarPackageDependencyAnalysisv1Module.transOrderKey)") 
    }

    if(job ? BakarPackageDependencyAnalysisv1Module.transOrderKey && !job(BakarPackageDependencyAnalysisv1Module.transOrderKey).isInstanceOf[scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for BakarPackageDependencyAnalysisv1Module.transOrderKey.  Expecting 'scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit]' but found '" + 
        job(BakarPackageDependencyAnalysisv1Module.transOrderKey).getClass.toString + "'")
    } 
    return tags
  }

  def modGetSparkCompilerOptions (options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions = {
    if (options.contains(BakarPackageDependencyAnalysisv1Module.globalSparkCompilerOptionsKey)) {
       return options(BakarPackageDependencyAnalysisv1Module.globalSparkCompilerOptionsKey).asInstanceOf[org.sireum.bakar.compiler.v1.option.SparkCompilerOptions]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSparkCompilerOptions (options : MMap[Property.Key, Any], sparkCompilerOptions : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions) : MMap[Property.Key, Any] = {

    options(BakarPackageDependencyAnalysisv1Module.globalSparkCompilerOptionsKey) = sparkCompilerOptions

    return options
  }

  def getTransOrder (options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit] = {
    if (options.contains(BakarPackageDependencyAnalysisv1Module.transOrderKey)) {
       return options(BakarPackageDependencyAnalysisv1Module.transOrderKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetTransOrder (options : MMap[Property.Key, Any], transOrder : scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit]) : MMap[Property.Key, Any] = {

    options(BakarPackageDependencyAnalysisv1Module.transOrderKey) = transOrder

    return options
  }

  def modGetSparkParserResult (options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.v1.module.util.SPARKParserResult = {
    if (options.contains(BakarParserv1Module.sparkParserResultKey)) {
       return options(BakarParserv1Module.sparkParserResultKey).asInstanceOf[org.sireum.bakar.compiler.v1.module.util.SPARKParserResult]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSparkParserResult (options : MMap[Property.Key, Any], sparkParserResult : org.sireum.bakar.compiler.v1.module.util.SPARKParserResult) : MMap[Property.Key, Any] = {

    options(BakarParserv1Module.sparkParserResultKey) = sparkParserResult

    return options
  }
}

trait BakarPackageDependencyAnalysisv1Module {
  def job : PipelineJob

  def sparkCompilerOptions : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions = BakarPackageDependencyAnalysisv1Module.modGetSparkCompilerOptions(job.properties)


  def transOrder_=(transOrder : scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit]) { BakarPackageDependencyAnalysisv1Module.modSetTransOrder(job.properties, transOrder) }

  def sparkParserResult : org.sireum.bakar.compiler.v1.module.util.SPARKParserResult = BakarPackageDependencyAnalysisv1Module.modGetSparkParserResult(job.properties)
}

object BakarTranslatorv1Module extends PipelineModule {
  def title = "Bakar Translator Module v1"
  def origin = classOf[BakarTranslatorv1]

  val globalSparkCompilerOptionsKey = "Global.sparkCompilerOptions"
  val globalSourcesKey = "Global.sources"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.compiler.v1.module.BakarTranslatorv1Def")
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
    val deps = ilist[PipelineModule](BakarPackageDependencyAnalysisv1Module)
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
    var _sparkCompilerOptions : scala.Option[AnyRef] = None
    var _sparkCompilerOptionsKey : scala.Option[String] = None

    val keylistsparkCompilerOptions = List(BakarTranslatorv1Module.globalSparkCompilerOptionsKey)
    keylistsparkCompilerOptions.foreach(key => 
      if(job ? key) { 
        if(_sparkCompilerOptions.isEmpty) {
          _sparkCompilerOptions = Some(job(key))
          _sparkCompilerOptionsKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _sparkCompilerOptions.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sparkCompilerOptions' keys '" + _sparkCompilerOptionsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sparkCompilerOptions match{
      case Some(x) =>
        if(!x.isInstanceOf[org.sireum.bakar.compiler.v1.option.SparkCompilerOptions]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sparkCompilerOptions'.  Expecting 'org.sireum.bakar.compiler.v1.option.SparkCompilerOptions' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sparkCompilerOptions'")       
    }
    var _transOrder : scala.Option[AnyRef] = None
    var _transOrderKey : scala.Option[String] = None

    val keylisttransOrder = List(BakarPackageDependencyAnalysisv1Module.transOrderKey)
    keylisttransOrder.foreach(key => 
      if(job ? key) { 
        if(_transOrder.isEmpty) {
          _transOrder = Some(job(key))
          _transOrderKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _transOrder.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'transOrder' keys '" + _transOrderKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _transOrder match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'transOrder'.  Expecting 'scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'transOrder'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? BakarTranslatorv1Module.globalSourcesKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'sources'. Expecting (BakarTranslatorv1Module.globalSourcesKey)") 
    }

    if(job ? BakarTranslatorv1Module.globalSourcesKey && !job(BakarTranslatorv1Module.globalSourcesKey).isInstanceOf[scala.collection.immutable.Seq[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for BakarTranslatorv1Module.globalSourcesKey.  Expecting 'scala.collection.immutable.Seq[java.lang.String]' but found '" + 
        job(BakarTranslatorv1Module.globalSourcesKey).getClass.toString + "'")
    } 
    return tags
  }

  def modGetSparkCompilerOptions (options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions = {
    if (options.contains(BakarTranslatorv1Module.globalSparkCompilerOptionsKey)) {
       return options(BakarTranslatorv1Module.globalSparkCompilerOptionsKey).asInstanceOf[org.sireum.bakar.compiler.v1.option.SparkCompilerOptions]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSparkCompilerOptions (options : MMap[Property.Key, Any], sparkCompilerOptions : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions) : MMap[Property.Key, Any] = {

    options(BakarTranslatorv1Module.globalSparkCompilerOptionsKey) = sparkCompilerOptions

    return options
  }

  def modGetTransOrder (options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit] = {
    if (options.contains(BakarPackageDependencyAnalysisv1Module.transOrderKey)) {
       return options(BakarPackageDependencyAnalysisv1Module.transOrderKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setTransOrder (options : MMap[Property.Key, Any], transOrder : scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit]) : MMap[Property.Key, Any] = {

    options(BakarPackageDependencyAnalysisv1Module.transOrderKey) = transOrder

    return options
  }

  def getSources (options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[java.lang.String] = {
    if (options.contains(BakarTranslatorv1Module.globalSourcesKey)) {
       return options(BakarTranslatorv1Module.globalSourcesKey).asInstanceOf[scala.collection.immutable.Seq[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSources (options : MMap[Property.Key, Any], sources : scala.collection.immutable.Seq[java.lang.String]) : MMap[Property.Key, Any] = {

    options(BakarTranslatorv1Module.globalSourcesKey) = sources

    return options
  }
}

trait BakarTranslatorv1Module {
  def job : PipelineJob

  def sparkCompilerOptions : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions = BakarTranslatorv1Module.modGetSparkCompilerOptions(job.properties)

  def transOrder : scala.collection.immutable.Seq[org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit] = BakarTranslatorv1Module.modGetTransOrder(job.properties)


  def sources_=(sources : scala.collection.immutable.Seq[java.lang.String]) { BakarTranslatorv1Module.modSetSources(job.properties, sources) }
}

object BakarCompilerv1Module extends PipelineModule {
  def title = "Bakar Compiler Module v1"
  def origin = classOf[BakarCompilerv1]

  val globalModelsKey = "Global.models"
  val globalSparkCompilerOptionsKey = "Global.sparkCompilerOptions"
  val globalSourcesKey = "Global.sources"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.compiler.v1.module.BakarCompilerv1Def")
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
    var _sparkCompilerOptions : scala.Option[AnyRef] = None
    var _sparkCompilerOptionsKey : scala.Option[String] = None

    val keylistsparkCompilerOptions = List(BakarCompilerv1Module.globalSparkCompilerOptionsKey)
    keylistsparkCompilerOptions.foreach(key => 
      if(job ? key) { 
        if(_sparkCompilerOptions.isEmpty) {
          _sparkCompilerOptions = Some(job(key))
          _sparkCompilerOptionsKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _sparkCompilerOptions.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sparkCompilerOptions' keys '" + _sparkCompilerOptionsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sparkCompilerOptions match{
      case Some(x) =>
        if(!x.isInstanceOf[org.sireum.bakar.compiler.v1.option.SparkCompilerOptions]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sparkCompilerOptions'.  Expecting 'org.sireum.bakar.compiler.v1.option.SparkCompilerOptions' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sparkCompilerOptions'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? BakarCompilerv1Module.globalSourcesKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'sources'. Expecting (BakarCompilerv1Module.globalSourcesKey)") 
    }

    if(job ? BakarCompilerv1Module.globalSourcesKey && !job(BakarCompilerv1Module.globalSourcesKey).isInstanceOf[scala.collection.immutable.Seq[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for BakarCompilerv1Module.globalSourcesKey.  Expecting 'scala.collection.immutable.Seq[java.lang.String]' but found '" + 
        job(BakarCompilerv1Module.globalSourcesKey).getClass.toString + "'")
    } 

    if(!(job ? BakarCompilerv1Module.globalModelsKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'models'. Expecting (BakarCompilerv1Module.globalModelsKey)") 
    }

    if(job ? BakarCompilerv1Module.globalModelsKey && !job(BakarCompilerv1Module.globalModelsKey).isInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for BakarCompilerv1Module.globalModelsKey.  Expecting 'scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]' but found '" + 
        job(BakarCompilerv1Module.globalModelsKey).getClass.toString + "'")
    } 
    return tags
  }

  def modGetSparkCompilerOptions (options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions = {
    if (options.contains(BakarCompilerv1Module.globalSparkCompilerOptionsKey)) {
       return options(BakarCompilerv1Module.globalSparkCompilerOptionsKey).asInstanceOf[org.sireum.bakar.compiler.v1.option.SparkCompilerOptions]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSparkCompilerOptions (options : MMap[Property.Key, Any], sparkCompilerOptions : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions) : MMap[Property.Key, Any] = {

    options(BakarCompilerv1Module.globalSparkCompilerOptionsKey) = sparkCompilerOptions

    return options
  }

  def getSources (options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[java.lang.String] = {
    if (options.contains(BakarCompilerv1Module.globalSourcesKey)) {
       return options(BakarCompilerv1Module.globalSourcesKey).asInstanceOf[scala.collection.immutable.Seq[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSources (options : MMap[Property.Key, Any], sources : scala.collection.immutable.Seq[java.lang.String]) : MMap[Property.Key, Any] = {

    options(BakarCompilerv1Module.globalSourcesKey) = sources

    return options
  }

  def getModels (options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model] = {
    if (options.contains(BakarCompilerv1Module.globalModelsKey)) {
       return options(BakarCompilerv1Module.globalModelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetModels (options : MMap[Property.Key, Any], models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]) : MMap[Property.Key, Any] = {

    options(BakarCompilerv1Module.globalModelsKey) = models

    return options
  }
}

trait BakarCompilerv1Module {
  def job : PipelineJob

  def sparkCompilerOptions : org.sireum.bakar.compiler.v1.option.SparkCompilerOptions = BakarCompilerv1Module.modGetSparkCompilerOptions(job.properties)


  def sources_=(sources : scala.collection.immutable.Seq[java.lang.String]) { BakarCompilerv1Module.modSetSources(job.properties, sources) }


  def models_=(models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]) { BakarCompilerv1Module.modSetModels(job.properties, models) }
}