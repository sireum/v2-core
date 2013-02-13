package org.sireum.core.module

import org.sireum.util._
import org.sireum.pipeline._
import org.sireum.core.module.BogorFactory
import org.sireum.pilar.ast.Model
import org.sireum.pilar.symbol.SymbolTable
import scala.Option
import scala.collection.immutable.Seq

object PilarSourcesModule extends PipelineModule {
  def title = "Pilar Source"
  def origin = classOf[PilarSources]

  val sourcesKey = "PilarSources.sources"
  val globalSourcesKey = "Global.sources"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.PilarSourcesDef")
      val cons = module.getConstructors()(0)
      val params = Array[AnyRef](job, info)
      val inst = cons.newInstance(params : _*)
    } catch {
      case e : Throwable =>
        e.printStackTrace
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, e.getMessage);
    }
    return tags
  }

  override def initialize(job : PipelineJob) {
  }

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ivector[PipelineModule]()
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
    var _sources : scala.Option[AnyRef] = None
    var _sourcesKey : scala.Option[String] = None

    val keylistsources = List(PilarSourcesModule.globalSourcesKey)
    keylistsources.foreach(key =>
      if (job ? key) {
        if (_sources.isEmpty) {
          _sources = Some(job(key))
          _sourcesKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _sources.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sources' keys '" + _sourcesKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sources match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sources'.  Expecting 'scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sources'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? PilarSourcesModule.sourcesKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'sources'. Expecting (PilarSourcesModule.sourcesKey)")
    }

    if (job ? PilarSourcesModule.sourcesKey && !job(PilarSourcesModule.sourcesKey).isInstanceOf[scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for PilarSourcesModule.sourcesKey.  Expecting 'scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]' but found '" +
          job(PilarSourcesModule.sourcesKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetSources(options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]] = {
    if (options.contains(PilarSourcesModule.globalSourcesKey)) {
      return options(PilarSourcesModule.globalSourcesKey).asInstanceOf[scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSources(options : MMap[Property.Key, Any], sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]) : MMap[Property.Key, Any] = {

    options(PilarSourcesModule.globalSourcesKey) = sources

    return options
  }

  def getSources(options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]] = {
    if (options.contains(PilarSourcesModule.sourcesKey)) {
      return options(PilarSourcesModule.sourcesKey).asInstanceOf[scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSources(options : MMap[Property.Key, Any], sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]) : MMap[Property.Key, Any] = {

    options(PilarSourcesModule.globalSourcesKey) = sources
    options(PilarSourcesModule.sourcesKey) = sources

    return options
  }

  implicit class PilarSourcesPropertyProvider(pp : PropertyProvider) extends PropertyProvider {
    val propertyMap = pp.propertyMap

    def sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]] = PilarSourcesModule.modGetSources(pp.propertyMap)

    def sources_=(sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]) { PilarSourcesModule.modSetSources(pp.propertyMap, sources) }
  }
}

trait PilarSourcesModule {
  def job : PipelineJob

  def sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]] = PilarSourcesModule.modGetSources(job.properties)

  def sources_=(sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]) { PilarSourcesModule.modSetSources(job.properties, sources) }
}

object ChunkingPilarParserModule extends PipelineModule {
  def title = "Pilar Parser"
  def origin = classOf[ChunkingPilarParser]

  val modelsKey = "ChunkingPilarParser.models"
  val globalModelsKey = "Global.models"
  val globalSourcesKey = "Global.sources"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.ChunkingPilarParserDef")
      val cons = module.getConstructors()(0)
      val params = Array[AnyRef](job, info)
      val inst = cons.newInstance(params : _*)
    } catch {
      case e : Throwable =>
        e.printStackTrace
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, e.getMessage);
    }
    return tags
  }

  override def initialize(job : PipelineJob) {
  }

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ivector[PipelineModule]()
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
    var _sources : scala.Option[AnyRef] = None
    var _sourcesKey : scala.Option[String] = None

    val keylistsources = List(ChunkingPilarParserModule.globalSourcesKey)
    keylistsources.foreach(key =>
      if (job ? key) {
        if (_sources.isEmpty) {
          _sources = Some(job(key))
          _sourcesKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _sources.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sources' keys '" + _sourcesKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sources match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sources'.  Expecting 'scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sources'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? ChunkingPilarParserModule.modelsKey) && !(job ? ChunkingPilarParserModule.globalModelsKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'models'. Expecting (ChunkingPilarParserModule.modelsKey or ChunkingPilarParserModule.globalModelsKey)")
    }

    if (job ? ChunkingPilarParserModule.modelsKey && !job(ChunkingPilarParserModule.modelsKey).isInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for ChunkingPilarParserModule.modelsKey.  Expecting 'scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]' but found '" +
          job(ChunkingPilarParserModule.modelsKey).getClass.toString + "'")
    }

    if (job ? ChunkingPilarParserModule.globalModelsKey && !job(ChunkingPilarParserModule.globalModelsKey).isInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for ChunkingPilarParserModule.globalModelsKey.  Expecting 'scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]' but found '" +
          job(ChunkingPilarParserModule.globalModelsKey).getClass.toString + "'")
    }
    return tags
  }

  def getModels(options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model] = {
    if (options.contains(ChunkingPilarParserModule.globalModelsKey)) {
      return options(ChunkingPilarParserModule.globalModelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }
    if (options.contains(ChunkingPilarParserModule.modelsKey)) {
      return options(ChunkingPilarParserModule.modelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetModels(options : MMap[Property.Key, Any], models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]) : MMap[Property.Key, Any] = {

    options(ChunkingPilarParserModule.globalModelsKey) = models
    options(ChunkingPilarParserModule.modelsKey) = models

    return options
  }

  def modGetSources(options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]] = {
    if (options.contains(ChunkingPilarParserModule.globalSourcesKey)) {
      return options(ChunkingPilarParserModule.globalSourcesKey).asInstanceOf[scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSources(options : MMap[Property.Key, Any], sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]) : MMap[Property.Key, Any] = {

    options(ChunkingPilarParserModule.globalSourcesKey) = sources

    return options
  }
}

trait ChunkingPilarParserModule {
  def job : PipelineJob

  def models_=(models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]) { ChunkingPilarParserModule.modSetModels(job.properties, models) }

  def sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]] = ChunkingPilarParserModule.modGetSources(job.properties)
}

object PilarParserModule extends PipelineModule {
  def title = "Pilar Parser"
  def origin = classOf[PilarParser]

  val modelsKey = "PilarParser.models"
  val globalModelsKey = "Global.models"
  val globalSourcesKey = "Global.sources"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.PilarParserDef")
      val cons = module.getConstructors()(0)
      val params = Array[AnyRef](job, info)
      val inst = cons.newInstance(params : _*)
    } catch {
      case e : Throwable =>
        e.printStackTrace
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, e.getMessage);
    }
    return tags
  }

  override def initialize(job : PipelineJob) {
  }

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ivector[PipelineModule]()
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
    var _sources : scala.Option[AnyRef] = None
    var _sourcesKey : scala.Option[String] = None

    val keylistsources = List(PilarParserModule.globalSourcesKey)
    keylistsources.foreach(key =>
      if (job ? key) {
        if (_sources.isEmpty) {
          _sources = Some(job(key))
          _sourcesKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _sources.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'sources' keys '" + _sourcesKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _sources match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'sources'.  Expecting 'scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'sources'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? PilarParserModule.modelsKey) && !(job ? PilarParserModule.globalModelsKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'models'. Expecting (PilarParserModule.modelsKey or PilarParserModule.globalModelsKey)")
    }

    if (job ? PilarParserModule.modelsKey && !job(PilarParserModule.modelsKey).isInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for PilarParserModule.modelsKey.  Expecting 'scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]' but found '" +
          job(PilarParserModule.modelsKey).getClass.toString + "'")
    }

    if (job ? PilarParserModule.globalModelsKey && !job(PilarParserModule.globalModelsKey).isInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for PilarParserModule.globalModelsKey.  Expecting 'scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]' but found '" +
          job(PilarParserModule.globalModelsKey).getClass.toString + "'")
    }
    return tags
  }

  def getModels(options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model] = {
    if (options.contains(PilarParserModule.globalModelsKey)) {
      return options(PilarParserModule.globalModelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }
    if (options.contains(PilarParserModule.modelsKey)) {
      return options(PilarParserModule.modelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetModels(options : MMap[Property.Key, Any], models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]) : MMap[Property.Key, Any] = {

    options(PilarParserModule.globalModelsKey) = models
    options(PilarParserModule.modelsKey) = models

    return options
  }

  def modGetSources(options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]] = {
    if (options.contains(PilarParserModule.globalSourcesKey)) {
      return options(PilarParserModule.globalSourcesKey).asInstanceOf[scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSources(options : MMap[Property.Key, Any], sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]) : MMap[Property.Key, Any] = {

    options(PilarParserModule.globalSourcesKey) = sources

    return options
  }

  object Consumer {

    implicit class PilarParserConsumerPropertyProvider(pp : PropertyProvider) extends PropertyProvider {
      val propertyMap = pp.propertyMap

      def models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model] = PilarParserModule.getModels(propertyMap)
    }
  }

  object Producer {
    implicit class PilarParserProducerPropertyProvider(pp : PropertyProvider) extends PropertyProvider {
      val propertyMap = pp.propertyMap

      def sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]] = PilarParserModule.modGetSources(propertyMap)
      def sources_=(srcs : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]]) { PilarParserModule.setSources(propertyMap, srcs) }
    }
  }
}

trait PilarParserModule {
  def job : PipelineJob

  def sources : scala.collection.immutable.Seq[scala.util.Either[java.lang.String, java.lang.String]] = PilarParserModule.modGetSources(job.properties)

  def models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model] = PilarParserModule.getModels(job.properties)
  def models_=(models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]) { PilarParserModule.modSetModels(job.properties, models) }
}

object PilarSymbolResolverModule extends PipelineModule {
  def title = "Pilar Symbol Resolver"
  def origin = classOf[PilarSymbolResolver]

  val globalParallelKey = "Global.parallel"
  val globalHasExistingModelsKey = "Global.hasExistingModels"
  val globalModelsKey = "Global.models"
  val modelsKey = "PilarSymbolResolver.models"
  val globalHasExistingSymbolTableKey = "Global.hasExistingSymbolTable"
  val globalSymbolTableKey = "Global.symbolTable"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.PilarSymbolResolverDef")
      val cons = module.getConstructors()(0)
      val params = Array[AnyRef](job, info)
      val inst = cons.newInstance(params : _*)
    } catch {
      case e : Throwable =>
        e.printStackTrace
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, e.getMessage);
    }
    return tags
  }

  override def initialize(job : PipelineJob) {
  }

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ivector[PipelineModule](PilarParserModule)
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
    var _models : scala.Option[AnyRef] = None
    var _modelsKey : scala.Option[String] = None

    val keylistmodels = List(PilarSymbolResolverModule.globalModelsKey, PilarParserModule.modelsKey)
    keylistmodels.foreach(key =>
      if (job ? key) {
        if (_models.isEmpty) {
          _models = Some(job(key))
          _modelsKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _models.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'models' keys '" + _modelsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _models match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'models'.  Expecting 'scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'models'")
    }
    var _parallel : scala.Option[AnyRef] = None
    var _parallelKey : scala.Option[String] = None

    val keylistparallel = List(PilarSymbolResolverModule.globalParallelKey)
    keylistparallel.foreach(key =>
      if (job ? key) {
        if (_parallel.isEmpty) {
          _parallel = Some(job(key))
          _parallelKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _parallel.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'parallel' keys '" + _parallelKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _parallel match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Boolean]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'parallel'.  Expecting 'scala.Boolean' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'parallel'")
    }
    var _hasExistingSymbolTable : scala.Option[AnyRef] = None
    var _hasExistingSymbolTableKey : scala.Option[String] = None

    val keylisthasExistingSymbolTable = List(PilarSymbolResolverModule.globalHasExistingSymbolTableKey)
    keylisthasExistingSymbolTable.foreach(key =>
      if (job ? key) {
        if (_hasExistingSymbolTable.isEmpty) {
          _hasExistingSymbolTable = Some(job(key))
          _hasExistingSymbolTableKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _hasExistingSymbolTable.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'hasExistingSymbolTable' keys '" + _hasExistingSymbolTableKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _hasExistingSymbolTable match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Option[org.sireum.pilar.symbol.SymbolTable]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'hasExistingSymbolTable'.  Expecting 'scala.Option[org.sireum.pilar.symbol.SymbolTable]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'hasExistingSymbolTable'")
    }
    var _hasExistingModels : scala.Option[AnyRef] = None
    var _hasExistingModelsKey : scala.Option[String] = None

    val keylisthasExistingModels = List(PilarSymbolResolverModule.globalHasExistingModelsKey)
    keylisthasExistingModels.foreach(key =>
      if (job ? key) {
        if (_hasExistingModels.isEmpty) {
          _hasExistingModels = Some(job(key))
          _hasExistingModelsKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _hasExistingModels.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'hasExistingModels' keys '" + _hasExistingModelsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _hasExistingModels match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Option[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'hasExistingModels'.  Expecting 'scala.Option[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'hasExistingModels'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? PilarSymbolResolverModule.modelsKey) && !(job ? PilarSymbolResolverModule.globalModelsKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'models'. Expecting (PilarSymbolResolverModule.modelsKey or PilarSymbolResolverModule.globalModelsKey)")
    }

    if (job ? PilarSymbolResolverModule.modelsKey && !job(PilarSymbolResolverModule.modelsKey).isInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for PilarSymbolResolverModule.modelsKey.  Expecting 'scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]' but found '" +
          job(PilarSymbolResolverModule.modelsKey).getClass.toString + "'")
    }

    if (job ? PilarSymbolResolverModule.globalModelsKey && !job(PilarSymbolResolverModule.globalModelsKey).isInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for PilarSymbolResolverModule.globalModelsKey.  Expecting 'scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]' but found '" +
          job(PilarSymbolResolverModule.globalModelsKey).getClass.toString + "'")
    }

    if (!(job ? PilarSymbolResolverModule.globalSymbolTableKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'symbolTable'. Expecting (PilarSymbolResolverModule.globalSymbolTableKey)")
    }

    if (job ? PilarSymbolResolverModule.globalSymbolTableKey && !job(PilarSymbolResolverModule.globalSymbolTableKey).isInstanceOf[org.sireum.pilar.symbol.SymbolTable]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for PilarSymbolResolverModule.globalSymbolTableKey.  Expecting 'org.sireum.pilar.symbol.SymbolTable' but found '" +
          job(PilarSymbolResolverModule.globalSymbolTableKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetModels(options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model] = {
    if (options.contains(PilarSymbolResolverModule.globalModelsKey)) {
      return options(PilarSymbolResolverModule.globalModelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }
    if (options.contains(PilarParserModule.modelsKey)) {
      return options(PilarParserModule.modelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setModels(options : MMap[Property.Key, Any], models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]) : MMap[Property.Key, Any] = {

    options(PilarSymbolResolverModule.globalModelsKey) = models
    options(PilarParserModule.modelsKey) = models

    return options
  }

  def getModels(options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model] = {
    if (options.contains(PilarSymbolResolverModule.globalModelsKey)) {
      return options(PilarSymbolResolverModule.globalModelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }
    if (options.contains(PilarSymbolResolverModule.modelsKey)) {
      return options(PilarSymbolResolverModule.modelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetModels(options : MMap[Property.Key, Any], models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]) : MMap[Property.Key, Any] = {

    options(PilarSymbolResolverModule.globalModelsKey) = models
    options(PilarSymbolResolverModule.modelsKey) = models

    return options
  }

  def modGetParallel(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(PilarSymbolResolverModule.globalParallelKey)) {
      return options(PilarSymbolResolverModule.globalParallelKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setParallel(options : MMap[Property.Key, Any], parallel : scala.Boolean) : MMap[Property.Key, Any] = {

    options(PilarSymbolResolverModule.globalParallelKey) = parallel

    return options
  }

  def modGetHasExistingSymbolTable(options : scala.collection.Map[Property.Key, Any]) : scala.Option[org.sireum.pilar.symbol.SymbolTable] = {
    if (options.contains(PilarSymbolResolverModule.globalHasExistingSymbolTableKey)) {
      return options(PilarSymbolResolverModule.globalHasExistingSymbolTableKey).asInstanceOf[scala.Option[org.sireum.pilar.symbol.SymbolTable]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setHasExistingSymbolTable(options : MMap[Property.Key, Any], hasExistingSymbolTable : scala.Option[org.sireum.pilar.symbol.SymbolTable]) : MMap[Property.Key, Any] = {

    options(PilarSymbolResolverModule.globalHasExistingSymbolTableKey) = hasExistingSymbolTable

    return options
  }

  def modGetHasExistingModels(options : scala.collection.Map[Property.Key, Any]) : scala.Option[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]] = {
    if (options.contains(PilarSymbolResolverModule.globalHasExistingModelsKey)) {
      return options(PilarSymbolResolverModule.globalHasExistingModelsKey).asInstanceOf[scala.Option[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setHasExistingModels(options : MMap[Property.Key, Any], hasExistingModels : scala.Option[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]) : MMap[Property.Key, Any] = {

    options(PilarSymbolResolverModule.globalHasExistingModelsKey) = hasExistingModels

    return options
  }

  def getSymbolTable(options : scala.collection.Map[Property.Key, Any]) : org.sireum.pilar.symbol.SymbolTable = {
    if (options.contains(PilarSymbolResolverModule.globalSymbolTableKey)) {
      return options(PilarSymbolResolverModule.globalSymbolTableKey).asInstanceOf[org.sireum.pilar.symbol.SymbolTable]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetSymbolTable(options : MMap[Property.Key, Any], symbolTable : org.sireum.pilar.symbol.SymbolTable) : MMap[Property.Key, Any] = {

    options(PilarSymbolResolverModule.globalSymbolTableKey) = symbolTable

    return options
  }
}

trait PilarSymbolResolverModule {
  def job : PipelineJob

  def models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model] = PilarSymbolResolverModule.modGetModels(job.properties)

  def models_=(models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]) { PilarSymbolResolverModule.modSetModels(job.properties, models) }

  def parallel : scala.Boolean = PilarSymbolResolverModule.modGetParallel(job.properties)

  def hasExistingSymbolTable : scala.Option[org.sireum.pilar.symbol.SymbolTable] = PilarSymbolResolverModule.modGetHasExistingSymbolTable(job.properties)

  def hasExistingModels : scala.Option[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]] = PilarSymbolResolverModule.modGetHasExistingModels(job.properties)

  def symbolTable_=(symbolTable : org.sireum.pilar.symbol.SymbolTable) { PilarSymbolResolverModule.modSetSymbolTable(job.properties, symbolTable) }
}

object BogorModule extends PipelineModule {
  def title = "Bogor"
  def origin = classOf[Bogor]

  val globalFactoryKey = "Global.factory"
  val globalModelsKey = "Global.models"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.BogorDef")
      val cons = module.getConstructors()(0)
      val params = Array[AnyRef](job, info)
      val inst = cons.newInstance(params : _*)
    } catch {
      case e : Throwable =>
        e.printStackTrace
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, e.getMessage);
    }
    return tags
  }

  override def initialize(job : PipelineJob) {
  }

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ivector[PipelineModule](PilarSymbolResolverModule)
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
    var _factory : scala.Option[AnyRef] = None
    var _factoryKey : scala.Option[String] = None

    val keylistfactory = List(BogorModule.globalFactoryKey)
    keylistfactory.foreach(key =>
      if (job ? key) {
        if (_factory.isEmpty) {
          _factory = Some(job(key))
          _factoryKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _factory.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'factory' keys '" + _factoryKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _factory match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.core.module.BogorFactory]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'factory'.  Expecting 'org.sireum.core.module.BogorFactory' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'factory'")
    }
    var _models : scala.Option[AnyRef] = None
    var _modelsKey : scala.Option[String] = None

    val keylistmodels = List(BogorModule.globalModelsKey, PilarSymbolResolverModule.modelsKey)
    keylistmodels.foreach(key =>
      if (job ? key) {
        if (_models.isEmpty) {
          _models = Some(job(key))
          _modelsKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _models.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'models' keys '" + _modelsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _models match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'models'.  Expecting 'scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'models'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    return tags
  }

  def modGetFactory(options : scala.collection.Map[Property.Key, Any]) : org.sireum.core.module.BogorFactory = {
    if (options.contains(BogorModule.globalFactoryKey)) {
      return options(BogorModule.globalFactoryKey).asInstanceOf[org.sireum.core.module.BogorFactory]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setFactory(options : MMap[Property.Key, Any], factory : org.sireum.core.module.BogorFactory) : MMap[Property.Key, Any] = {

    options(BogorModule.globalFactoryKey) = factory

    return options
  }

  def modGetModels(options : scala.collection.Map[Property.Key, Any]) : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model] = {
    if (options.contains(BogorModule.globalModelsKey)) {
      return options(BogorModule.globalModelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }
    if (options.contains(PilarSymbolResolverModule.modelsKey)) {
      return options(PilarSymbolResolverModule.modelsKey).asInstanceOf[scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setModels(options : MMap[Property.Key, Any], models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model]) : MMap[Property.Key, Any] = {

    options(BogorModule.globalModelsKey) = models
    options(PilarSymbolResolverModule.modelsKey) = models

    return options
  }
}

trait BogorModule {
  def job : PipelineJob

  def factory : org.sireum.core.module.BogorFactory = BogorModule.modGetFactory(job.properties)

  def models : scala.collection.immutable.Seq[org.sireum.pilar.ast.Model] = BogorModule.modGetModels(job.properties)
}