package org.sireum.bakar.compiler.module

import org.sireum.util._
import org.sireum.pipeline._
import java.lang.String
import org.sireum.bakar.compiler.module.util.SPARKCompilationUnit
import org.sireum.bakar.compiler.module.util.SPARKParserResult
import org.sireum.bakar.compiler.option.SparkCompilerOptions
import org.sireum.pilar.ast.Model

object BakarParserModule extends PipelineModule {
  def title = "Bakar Parser Module"
  def origin = classOf[BakarParser]

  val globalSparkCompilerOptionsKey = "Global.sparkCompilerOptions"
  val sparkParserResultKey = "BakarParser.sparkParserResult"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.compiler.module.BakarParserDef")
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
    val deps = ilistEmpty[PipelineModule]
    deps.foreach(d =>
      if (stage.modules.contains(d)) {
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "'" + this.title + "' depends on '" + d.title + "' yet both were found in stage '" + stage.title + "'"
        )
      }
    )
    return tags
  }

  def inputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    var _sparkCompilerOptions : scala.Option[AnyRef] = None
    var _sparkCompilerOptionsKey : scala.Option[String] = None

    val keylistsparkCompilerOptions = List(BakarParserModule.globalSparkCompilerOptionsKey)
    keylistsparkCompilerOptions.foreach(key =>
      if (job ? key) {
        if (_sparkCompilerOptions.isEmpty) {
          _sparkCompilerOptions = Some(job(key))
          _sparkCompilerOptionsKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _sparkCompilerOptions.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sparkCompilerOptions' keys '" + _sparkCompilerOptionsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sparkCompilerOptions match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.bakar.compiler.option.SparkCompilerOptions]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sparkCompilerOptions'.  Expecting 'org.sireum.bakar.compiler.option.SparkCompilerOptions' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sparkCompilerOptions'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? BakarParserModule.sparkParserResultKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'sparkParserResult'. Expecting (BakarParserModule.sparkParserResultKey)")
    }

    if (job ? BakarParserModule.sparkParserResultKey && !job(BakarParserModule.sparkParserResultKey).isInstanceOf[org.sireum.bakar.compiler.module.util.SPARKParserResult]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for BakarParserModule.sparkParserResultKey.  Expecting 'org.sireum.bakar.compiler.module.util.SPARKParserResult' but found '" +
          job(BakarParserModule.sparkParserResultKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetSparkCompilerOptions(options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.option.SparkCompilerOptions = {
    if (options.contains(BakarParserModule.globalSparkCompilerOptionsKey)) {
      return options(BakarParserModule.globalSparkCompilerOptionsKey).asInstanceOf[org.sireum.bakar.compiler.option.SparkCompilerOptions]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSparkCompilerOptions(options : MMap[Property.Key, Any], sparkCompilerOptions : org.sireum.bakar.compiler.option.SparkCompilerOptions) : MMap[Property.Key, Any] = {

    options(BakarParserModule.globalSparkCompilerOptionsKey) = sparkCompilerOptions

    return options
  }

  def getSparkParserResult(options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.module.util.SPARKParserResult = {
    if (options.contains(BakarParserModule.sparkParserResultKey)) {
      return options(BakarParserModule.sparkParserResultKey).asInstanceOf[org.sireum.bakar.compiler.module.util.SPARKParserResult]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSparkParserResult(options : MMap[Property.Key, Any], sparkParserResult : org.sireum.bakar.compiler.module.util.SPARKParserResult) : MMap[Property.Key, Any] = {

    options(BakarParserModule.sparkParserResultKey) = sparkParserResult

    return options
  }
}

trait BakarParserModule {
  def job : PipelineJob

  def sparkCompilerOptions : org.sireum.bakar.compiler.option.SparkCompilerOptions = BakarParserModule.modGetSparkCompilerOptions(job.properties)

  def sparkParserResult_=(sparkParserResult : org.sireum.bakar.compiler.module.util.SPARKParserResult) { BakarParserModule.modSetSparkParserResult(job.properties, sparkParserResult) }
}

object BakarPackageDependencyAnalysisModule extends PipelineModule {
  def title = "Bakar Package Dependency Analysis Module"
  def origin = classOf[BakarPackageDependencyAnalysis]

  val globalSparkCompilerOptionsKey = "Global.sparkCompilerOptions"
  val transOrderKey = "BakarPackageDependencyAnalysis.transOrder"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.compiler.module.BakarPackageDependencyAnalysisDef")
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
    val deps = ilist[PipelineModule](BakarParserModule)
    deps.foreach(d =>
      if (stage.modules.contains(d)) {
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "'" + this.title + "' depends on '" + d.title + "' yet both were found in stage '" + stage.title + "'"
        )
      }
    )
    return tags
  }

  def inputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    var _sparkCompilerOptions : scala.Option[AnyRef] = None
    var _sparkCompilerOptionsKey : scala.Option[String] = None

    val keylistsparkCompilerOptions = List(BakarPackageDependencyAnalysisModule.globalSparkCompilerOptionsKey)
    keylistsparkCompilerOptions.foreach(key =>
      if (job ? key) {
        if (_sparkCompilerOptions.isEmpty) {
          _sparkCompilerOptions = Some(job(key))
          _sparkCompilerOptionsKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _sparkCompilerOptions.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sparkCompilerOptions' keys '" + _sparkCompilerOptionsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sparkCompilerOptions match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.bakar.compiler.option.SparkCompilerOptions]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sparkCompilerOptions'.  Expecting 'org.sireum.bakar.compiler.option.SparkCompilerOptions' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sparkCompilerOptions'")
    }
    var _sparkParserResult : scala.Option[AnyRef] = None
    var _sparkParserResultKey : scala.Option[String] = None

    val keylistsparkParserResult = List(BakarParserModule.sparkParserResultKey)
    keylistsparkParserResult.foreach(key =>
      if (job ? key) {
        if (_sparkParserResult.isEmpty) {
          _sparkParserResult = Some(job(key))
          _sparkParserResultKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _sparkParserResult.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sparkParserResult' keys '" + _sparkParserResultKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sparkParserResult match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.bakar.compiler.module.util.SPARKParserResult]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sparkParserResult'.  Expecting 'org.sireum.bakar.compiler.module.util.SPARKParserResult' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sparkParserResult'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? BakarPackageDependencyAnalysisModule.transOrderKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'transOrder'. Expecting (BakarPackageDependencyAnalysisModule.transOrderKey)")
    }

    if (job ? BakarPackageDependencyAnalysisModule.transOrderKey && !job(BakarPackageDependencyAnalysisModule.transOrderKey).isInstanceOf[ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for BakarPackageDependencyAnalysisModule.transOrderKey.  Expecting 'ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit]' but found '" +
          job(BakarPackageDependencyAnalysisModule.transOrderKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetSparkCompilerOptions(options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.option.SparkCompilerOptions = {
    if (options.contains(BakarPackageDependencyAnalysisModule.globalSparkCompilerOptionsKey)) {
      return options(BakarPackageDependencyAnalysisModule.globalSparkCompilerOptionsKey).asInstanceOf[org.sireum.bakar.compiler.option.SparkCompilerOptions]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSparkCompilerOptions(options : MMap[Property.Key, Any], sparkCompilerOptions : org.sireum.bakar.compiler.option.SparkCompilerOptions) : MMap[Property.Key, Any] = {

    options(BakarPackageDependencyAnalysisModule.globalSparkCompilerOptionsKey) = sparkCompilerOptions

    return options
  }

  def getTransOrder(options : scala.collection.Map[Property.Key, Any]) : ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit] = {
    if (options.contains(BakarPackageDependencyAnalysisModule.transOrderKey)) {
      return options(BakarPackageDependencyAnalysisModule.transOrderKey).asInstanceOf[ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetTransOrder(options : MMap[Property.Key, Any], transOrder : ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit]) : MMap[Property.Key, Any] = {

    options(BakarPackageDependencyAnalysisModule.transOrderKey) = transOrder

    return options
  }

  def modGetSparkParserResult(options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.module.util.SPARKParserResult = {
    if (options.contains(BakarParserModule.sparkParserResultKey)) {
      return options(BakarParserModule.sparkParserResultKey).asInstanceOf[org.sireum.bakar.compiler.module.util.SPARKParserResult]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSparkParserResult(options : MMap[Property.Key, Any], sparkParserResult : org.sireum.bakar.compiler.module.util.SPARKParserResult) : MMap[Property.Key, Any] = {

    options(BakarParserModule.sparkParserResultKey) = sparkParserResult

    return options
  }
}

trait BakarPackageDependencyAnalysisModule {
  def job : PipelineJob

  def sparkCompilerOptions : org.sireum.bakar.compiler.option.SparkCompilerOptions = BakarPackageDependencyAnalysisModule.modGetSparkCompilerOptions(job.properties)

  def transOrder_=(transOrder : ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit]) { BakarPackageDependencyAnalysisModule.modSetTransOrder(job.properties, transOrder) }

  def sparkParserResult : org.sireum.bakar.compiler.module.util.SPARKParserResult = BakarPackageDependencyAnalysisModule.modGetSparkParserResult(job.properties)
}

object BakarTranslatorModule extends PipelineModule {
  def title = "Bakar Translator Module"
  def origin = classOf[BakarTranslator]

  val globalSparkCompilerOptionsKey = "Global.sparkCompilerOptions"
  val globalSourcesKey = "Global.sources"

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
    val deps = ilist[PipelineModule](BakarPackageDependencyAnalysisModule)
    deps.foreach(d =>
      if (stage.modules.contains(d)) {
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "'" + this.title + "' depends on '" + d.title + "' yet both were found in stage '" + stage.title + "'"
        )
      }
    )
    return tags
  }

  def inputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    var _sparkCompilerOptions : scala.Option[AnyRef] = None
    var _sparkCompilerOptionsKey : scala.Option[String] = None

    val keylistsparkCompilerOptions = List(BakarTranslatorModule.globalSparkCompilerOptionsKey)
    keylistsparkCompilerOptions.foreach(key =>
      if (job ? key) {
        if (_sparkCompilerOptions.isEmpty) {
          _sparkCompilerOptions = Some(job(key))
          _sparkCompilerOptionsKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _sparkCompilerOptions.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sparkCompilerOptions' keys '" + _sparkCompilerOptionsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sparkCompilerOptions match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.bakar.compiler.option.SparkCompilerOptions]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sparkCompilerOptions'.  Expecting 'org.sireum.bakar.compiler.option.SparkCompilerOptions' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sparkCompilerOptions'")
    }
    var _transOrder : scala.Option[AnyRef] = None
    var _transOrderKey : scala.Option[String] = None

    val keylisttransOrder = List(BakarPackageDependencyAnalysisModule.transOrderKey)
    keylisttransOrder.foreach(key =>
      if (job ? key) {
        if (_transOrder.isEmpty) {
          _transOrder = Some(job(key))
          _transOrderKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _transOrder.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'transOrder' keys '" + _transOrderKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _transOrder match {
      case Some(x) =>
        if (!x.isInstanceOf[ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'transOrder'.  Expecting 'ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'transOrder'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? BakarTranslatorModule.globalSourcesKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'sources'. Expecting (BakarTranslatorModule.globalSourcesKey)")
    }

    if (job ? BakarTranslatorModule.globalSourcesKey && !job(BakarTranslatorModule.globalSourcesKey).isInstanceOf[ISeq[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for BakarTranslatorModule.globalSourcesKey.  Expecting 'ISeq[java.lang.String]' but found '" +
          job(BakarTranslatorModule.globalSourcesKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetSparkCompilerOptions(options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.option.SparkCompilerOptions = {
    if (options.contains(BakarTranslatorModule.globalSparkCompilerOptionsKey)) {
      return options(BakarTranslatorModule.globalSparkCompilerOptionsKey).asInstanceOf[org.sireum.bakar.compiler.option.SparkCompilerOptions]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSparkCompilerOptions(options : MMap[Property.Key, Any], sparkCompilerOptions : org.sireum.bakar.compiler.option.SparkCompilerOptions) : MMap[Property.Key, Any] = {

    options(BakarTranslatorModule.globalSparkCompilerOptionsKey) = sparkCompilerOptions

    return options
  }

  def modGetTransOrder(options : scala.collection.Map[Property.Key, Any]) : ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit] = {
    if (options.contains(BakarPackageDependencyAnalysisModule.transOrderKey)) {
      return options(BakarPackageDependencyAnalysisModule.transOrderKey).asInstanceOf[ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setTransOrder(options : MMap[Property.Key, Any], transOrder : ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit]) : MMap[Property.Key, Any] = {

    options(BakarPackageDependencyAnalysisModule.transOrderKey) = transOrder

    return options
  }

  def getSources(options : scala.collection.Map[Property.Key, Any]) : ISeq[java.lang.String] = {
    if (options.contains(BakarTranslatorModule.globalSourcesKey)) {
      return options(BakarTranslatorModule.globalSourcesKey).asInstanceOf[ISeq[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSources(options : MMap[Property.Key, Any], sources : ISeq[java.lang.String]) : MMap[Property.Key, Any] = {

    options(BakarTranslatorModule.globalSourcesKey) = sources

    return options
  }
}

trait BakarTranslatorModule {
  def job : PipelineJob

  def sparkCompilerOptions : org.sireum.bakar.compiler.option.SparkCompilerOptions = BakarTranslatorModule.modGetSparkCompilerOptions(job.properties)

  def transOrder : ISeq[org.sireum.bakar.compiler.module.util.SPARKCompilationUnit] = BakarTranslatorModule.modGetTransOrder(job.properties)

  def sources_=(sources : ISeq[java.lang.String]) { BakarTranslatorModule.modSetSources(job.properties, sources) }
}

object BakarCompilerModule extends PipelineModule {
  def title = "Bakar Compiler Module"
  def origin = classOf[BakarCompiler]

  val globalSparkCompilerOptionsKey = "Global.sparkCompilerOptions"
  val globalModelsKey = "Global.models"
  val globalSourcesKey = "Global.sources"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.bakar.compiler.module.BakarCompilerDef")
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
    val deps = ilistEmpty[PipelineModule]
    deps.foreach(d =>
      if (stage.modules.contains(d)) {
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "'" + this.title + "' depends on '" + d.title + "' yet both were found in stage '" + stage.title + "'"
        )
      }
    )
    return tags
  }

  def inputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    var _sparkCompilerOptions : scala.Option[AnyRef] = None
    var _sparkCompilerOptionsKey : scala.Option[String] = None

    val keylistsparkCompilerOptions = List(BakarCompilerModule.globalSparkCompilerOptionsKey)
    keylistsparkCompilerOptions.foreach(key =>
      if (job ? key) {
        if (_sparkCompilerOptions.isEmpty) {
          _sparkCompilerOptions = Some(job(key))
          _sparkCompilerOptionsKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _sparkCompilerOptions.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sparkCompilerOptions' keys '" + _sparkCompilerOptionsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sparkCompilerOptions match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.bakar.compiler.option.SparkCompilerOptions]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sparkCompilerOptions'.  Expecting 'org.sireum.bakar.compiler.option.SparkCompilerOptions' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sparkCompilerOptions'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? BakarCompilerModule.globalSourcesKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'sources'. Expecting (BakarCompilerModule.globalSourcesKey)")
    }

    if (job ? BakarCompilerModule.globalSourcesKey && !job(BakarCompilerModule.globalSourcesKey).isInstanceOf[ISeq[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for BakarCompilerModule.globalSourcesKey.  Expecting 'ISeq[java.lang.String]' but found '" +
          job(BakarCompilerModule.globalSourcesKey).getClass.toString + "'")
    }

    if (!(job ? BakarCompilerModule.globalModelsKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'models'. Expecting (BakarCompilerModule.globalModelsKey)")
    }

    if (job ? BakarCompilerModule.globalModelsKey && !job(BakarCompilerModule.globalModelsKey).isInstanceOf[ISeq[org.sireum.pilar.ast.Model]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for BakarCompilerModule.globalModelsKey.  Expecting 'ISeq[org.sireum.pilar.ast.Model]' but found '" +
          job(BakarCompilerModule.globalModelsKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetSparkCompilerOptions(options : scala.collection.Map[Property.Key, Any]) : org.sireum.bakar.compiler.option.SparkCompilerOptions = {
    if (options.contains(BakarCompilerModule.globalSparkCompilerOptionsKey)) {
      return options(BakarCompilerModule.globalSparkCompilerOptionsKey).asInstanceOf[org.sireum.bakar.compiler.option.SparkCompilerOptions]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSparkCompilerOptions(options : MMap[Property.Key, Any], sparkCompilerOptions : org.sireum.bakar.compiler.option.SparkCompilerOptions) : MMap[Property.Key, Any] = {

    options(BakarCompilerModule.globalSparkCompilerOptionsKey) = sparkCompilerOptions

    return options
  }

  def getSources(options : scala.collection.Map[Property.Key, Any]) : ISeq[java.lang.String] = {
    if (options.contains(BakarCompilerModule.globalSourcesKey)) {
      return options(BakarCompilerModule.globalSourcesKey).asInstanceOf[ISeq[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSources(options : MMap[Property.Key, Any], sources : ISeq[java.lang.String]) : MMap[Property.Key, Any] = {

    options(BakarCompilerModule.globalSourcesKey) = sources

    return options
  }

  def getModels(options : scala.collection.Map[Property.Key, Any]) : ISeq[org.sireum.pilar.ast.Model] = {
    if (options.contains(BakarCompilerModule.globalModelsKey)) {
      return options(BakarCompilerModule.globalModelsKey).asInstanceOf[ISeq[org.sireum.pilar.ast.Model]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetModels(options : MMap[Property.Key, Any], models : ISeq[org.sireum.pilar.ast.Model]) : MMap[Property.Key, Any] = {

    options(BakarCompilerModule.globalModelsKey) = models

    return options
  }
}

trait BakarCompilerModule {
  def job : PipelineJob

  def sparkCompilerOptions : org.sireum.bakar.compiler.option.SparkCompilerOptions = BakarCompilerModule.modGetSparkCompilerOptions(job.properties)

  def sources_=(sources : ISeq[java.lang.String]) { BakarCompilerModule.modSetSources(job.properties, sources) }

  def models_=(models : ISeq[org.sireum.pilar.ast.Model]) { BakarCompilerModule.modSetModels(job.properties, models) }
}