package org.sireum.core.module

import org.sireum.util._
import org.sireum.pipeline._
import java.lang.String
import org.sireum.alir.AlirIntraProceduralNode
import org.sireum.alir.ControlDependenceGraph
import org.sireum.alir.ControlFlowGraph
import org.sireum.alir.DataDependenceGraph
import org.sireum.alir.DefRef
import org.sireum.alir.DominanceFrontierGraph
import org.sireum.alir.ImmediateDominatorGraph
import org.sireum.alir.MonotoneDataFlowAnalysisResult
import org.sireum.alir.ProgramDependenceGraph
import org.sireum.core.module.AlirIntraProcedural.AlirIntraproceduralAnalysisResult
import org.sireum.pilar.ast.LocationDecl
import org.sireum.pilar.symbol.ProcedureSymbolTable
import org.sireum.pilar.symbol.SymbolTable
import scala.Function1
import scala.Function2
import scala.Option
import scala.collection.mutable.Map

object AlirIntraProceduralModule extends PipelineModule {
  def title = "Alir Intraprocedural Module"
  def origin = classOf[AlirIntraProcedural]

  val globalIsInputOutputParamPredicateKey = "Global.isInputOutputParamPredicate"
  val globalShouldBuildCfgKey = "Global.shouldBuildCfg"
  val globalShouldIncludeFlowFunctionKey = "Global.shouldIncludeFlowFunction"
  val globalShouldBuildDdgKey = "Global.shouldBuildDdg"
  val globalSymbolTableKey = "Global.symbolTable"
  val globalShouldBuildDfgKey = "Global.shouldBuildDfg"
  val globalProcedureAbsUriIteratorKey = "Global.procedureAbsUriIterator"
  val globalParallelKey = "Global.parallel"
  val globalShouldBuildCdgKey = "Global.shouldBuildCdg"
  val globalShouldBuildPdgKey = "Global.shouldBuildPdg"
  val globalShouldBuildIdgKey = "Global.shouldBuildIdg"
  val globalShouldBuildRdaKey = "Global.shouldBuildRda"
  val globalResultKey = "Global.result"
  val globalSwitchAsOrderedMatchKey = "Global.switchAsOrderedMatch"
  val globalDefRefKey = "Global.defRef"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.AlirIntraProceduralDef")
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
    if (!(job ? AlirIntraProceduralModule.globalShouldIncludeFlowFunctionKey)) {
      val shouldIncludeFlowFunction = Class.forName("org.sireum.core.module.AlirIntraProcedural").getDeclaredMethod("$lessinit$greater$default$11").invoke(null).asInstanceOf[scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]]]
      setShouldIncludeFlowFunction(job.properties, shouldIncludeFlowFunction)
    }

    if (!(job ? AlirIntraProceduralModule.globalDefRefKey)) {
      val defRef = Class.forName("org.sireum.core.module.AlirIntraProcedural").getDeclaredMethod("$lessinit$greater$default$12").invoke(null).asInstanceOf[scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]]
      setDefRef(job.properties, defRef)
    }

    if (!(job ? AlirIntraProceduralModule.globalIsInputOutputParamPredicateKey)) {
      val isInputOutputParamPredicate = Class.forName("org.sireum.core.module.AlirIntraProcedural").getDeclaredMethod("$lessinit$greater$default$13").invoke(null).asInstanceOf[scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]]
      setIsInputOutputParamPredicate(job.properties, isInputOutputParamPredicate)
    }

    if (!(job ? AlirIntraProceduralModule.globalSwitchAsOrderedMatchKey)) {
      val switchAsOrderedMatch = Class.forName("org.sireum.core.module.AlirIntraProcedural").getDeclaredMethod("$lessinit$greater$default$14").invoke(null).asInstanceOf[scala.Boolean]
      setSwitchAsOrderedMatch(job.properties, switchAsOrderedMatch)
    }

    if (!(job ? AlirIntraProceduralModule.globalProcedureAbsUriIteratorKey)) {
      val procedureAbsUriIterator = Class.forName("org.sireum.core.module.AlirIntraProcedural").getDeclaredMethod("$lessinit$greater$default$15").invoke(null).asInstanceOf[scala.Option[scala.collection.Iterator[java.lang.String]]]
      setProcedureAbsUriIterator(job.properties, procedureAbsUriIterator)
    }
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
    var _parallel : scala.Option[AnyRef] = None
    var _parallelKey : scala.Option[String] = None

    val keylistparallel = List(AlirIntraProceduralModule.globalParallelKey)
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
    var _symbolTable : scala.Option[AnyRef] = None
    var _symbolTableKey : scala.Option[String] = None

    val keylistsymbolTable = List(AlirIntraProceduralModule.globalSymbolTableKey)
    keylistsymbolTable.foreach(key =>
      if (job ? key) {
        if (_symbolTable.isEmpty) {
          _symbolTable = Some(job(key))
          _symbolTableKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _symbolTable.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'symbolTable' keys '" + _symbolTableKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _symbolTable match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.pilar.symbol.SymbolTable]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'symbolTable'.  Expecting 'org.sireum.pilar.symbol.SymbolTable' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'symbolTable'")
    }
    var _shouldBuildCfg : scala.Option[AnyRef] = None
    var _shouldBuildCfgKey : scala.Option[String] = None

    val keylistshouldBuildCfg = List(AlirIntraProceduralModule.globalShouldBuildCfgKey)
    keylistshouldBuildCfg.foreach(key =>
      if (job ? key) {
        if (_shouldBuildCfg.isEmpty) {
          _shouldBuildCfg = Some(job(key))
          _shouldBuildCfgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _shouldBuildCfg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'shouldBuildCfg' keys '" + _shouldBuildCfgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _shouldBuildCfg match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Boolean]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'shouldBuildCfg'.  Expecting 'scala.Boolean' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'shouldBuildCfg'")
    }
    var _shouldBuildIdg : scala.Option[AnyRef] = None
    var _shouldBuildIdgKey : scala.Option[String] = None

    val keylistshouldBuildIdg = List(AlirIntraProceduralModule.globalShouldBuildIdgKey)
    keylistshouldBuildIdg.foreach(key =>
      if (job ? key) {
        if (_shouldBuildIdg.isEmpty) {
          _shouldBuildIdg = Some(job(key))
          _shouldBuildIdgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _shouldBuildIdg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'shouldBuildIdg' keys '" + _shouldBuildIdgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _shouldBuildIdg match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Boolean]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'shouldBuildIdg'.  Expecting 'scala.Boolean' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'shouldBuildIdg'")
    }
    var _shouldBuildDfg : scala.Option[AnyRef] = None
    var _shouldBuildDfgKey : scala.Option[String] = None

    val keylistshouldBuildDfg = List(AlirIntraProceduralModule.globalShouldBuildDfgKey)
    keylistshouldBuildDfg.foreach(key =>
      if (job ? key) {
        if (_shouldBuildDfg.isEmpty) {
          _shouldBuildDfg = Some(job(key))
          _shouldBuildDfgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _shouldBuildDfg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'shouldBuildDfg' keys '" + _shouldBuildDfgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _shouldBuildDfg match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Boolean]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'shouldBuildDfg'.  Expecting 'scala.Boolean' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'shouldBuildDfg'")
    }
    var _shouldBuildCdg : scala.Option[AnyRef] = None
    var _shouldBuildCdgKey : scala.Option[String] = None

    val keylistshouldBuildCdg = List(AlirIntraProceduralModule.globalShouldBuildCdgKey)
    keylistshouldBuildCdg.foreach(key =>
      if (job ? key) {
        if (_shouldBuildCdg.isEmpty) {
          _shouldBuildCdg = Some(job(key))
          _shouldBuildCdgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _shouldBuildCdg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'shouldBuildCdg' keys '" + _shouldBuildCdgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _shouldBuildCdg match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Boolean]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'shouldBuildCdg'.  Expecting 'scala.Boolean' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'shouldBuildCdg'")
    }
    var _shouldBuildRda : scala.Option[AnyRef] = None
    var _shouldBuildRdaKey : scala.Option[String] = None

    val keylistshouldBuildRda = List(AlirIntraProceduralModule.globalShouldBuildRdaKey)
    keylistshouldBuildRda.foreach(key =>
      if (job ? key) {
        if (_shouldBuildRda.isEmpty) {
          _shouldBuildRda = Some(job(key))
          _shouldBuildRdaKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _shouldBuildRda.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'shouldBuildRda' keys '" + _shouldBuildRdaKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _shouldBuildRda match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Boolean]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'shouldBuildRda'.  Expecting 'scala.Boolean' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'shouldBuildRda'")
    }
    var _shouldBuildDdg : scala.Option[AnyRef] = None
    var _shouldBuildDdgKey : scala.Option[String] = None

    val keylistshouldBuildDdg = List(AlirIntraProceduralModule.globalShouldBuildDdgKey)
    keylistshouldBuildDdg.foreach(key =>
      if (job ? key) {
        if (_shouldBuildDdg.isEmpty) {
          _shouldBuildDdg = Some(job(key))
          _shouldBuildDdgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _shouldBuildDdg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'shouldBuildDdg' keys '" + _shouldBuildDdgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _shouldBuildDdg match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Boolean]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'shouldBuildDdg'.  Expecting 'scala.Boolean' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'shouldBuildDdg'")
    }
    var _shouldBuildPdg : scala.Option[AnyRef] = None
    var _shouldBuildPdgKey : scala.Option[String] = None

    val keylistshouldBuildPdg = List(AlirIntraProceduralModule.globalShouldBuildPdgKey)
    keylistshouldBuildPdg.foreach(key =>
      if (job ? key) {
        if (_shouldBuildPdg.isEmpty) {
          _shouldBuildPdg = Some(job(key))
          _shouldBuildPdgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _shouldBuildPdg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'shouldBuildPdg' keys '" + _shouldBuildPdgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _shouldBuildPdg match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Boolean]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'shouldBuildPdg'.  Expecting 'scala.Boolean' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'shouldBuildPdg'")
    }
    var _shouldIncludeFlowFunction : scala.Option[AnyRef] = None
    var _shouldIncludeFlowFunctionKey : scala.Option[String] = None

    val keylistshouldIncludeFlowFunction = List(AlirIntraProceduralModule.globalShouldIncludeFlowFunctionKey)
    keylistshouldIncludeFlowFunction.foreach(key =>
      if (job ? key) {
        if (_shouldIncludeFlowFunction.isEmpty) {
          _shouldIncludeFlowFunction = Some(job(key))
          _shouldIncludeFlowFunctionKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _shouldIncludeFlowFunction.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'shouldIncludeFlowFunction' keys '" + _shouldIncludeFlowFunctionKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _shouldIncludeFlowFunction match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'shouldIncludeFlowFunction'.  Expecting 'scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'shouldIncludeFlowFunction'")
    }
    var _defRef : scala.Option[AnyRef] = None
    var _defRefKey : scala.Option[String] = None

    val keylistdefRef = List(AlirIntraProceduralModule.globalDefRefKey)
    keylistdefRef.foreach(key =>
      if (job ? key) {
        if (_defRef.isEmpty) {
          _defRef = Some(job(key))
          _defRefKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _defRef.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'defRef' keys '" + _defRefKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _defRef match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'defRef'.  Expecting 'scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'defRef'")
    }
    var _isInputOutputParamPredicate : scala.Option[AnyRef] = None
    var _isInputOutputParamPredicateKey : scala.Option[String] = None

    val keylistisInputOutputParamPredicate = List(AlirIntraProceduralModule.globalIsInputOutputParamPredicateKey)
    keylistisInputOutputParamPredicate.foreach(key =>
      if (job ? key) {
        if (_isInputOutputParamPredicate.isEmpty) {
          _isInputOutputParamPredicate = Some(job(key))
          _isInputOutputParamPredicateKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _isInputOutputParamPredicate.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'isInputOutputParamPredicate' keys '" + _isInputOutputParamPredicateKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _isInputOutputParamPredicate match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'isInputOutputParamPredicate'.  Expecting 'scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'isInputOutputParamPredicate'")
    }
    var _switchAsOrderedMatch : scala.Option[AnyRef] = None
    var _switchAsOrderedMatchKey : scala.Option[String] = None

    val keylistswitchAsOrderedMatch = List(AlirIntraProceduralModule.globalSwitchAsOrderedMatchKey)
    keylistswitchAsOrderedMatch.foreach(key =>
      if (job ? key) {
        if (_switchAsOrderedMatch.isEmpty) {
          _switchAsOrderedMatch = Some(job(key))
          _switchAsOrderedMatchKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _switchAsOrderedMatch.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'switchAsOrderedMatch' keys '" + _switchAsOrderedMatchKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _switchAsOrderedMatch match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Boolean]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'switchAsOrderedMatch'.  Expecting 'scala.Boolean' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'switchAsOrderedMatch'")
    }
    var _procedureAbsUriIterator : scala.Option[AnyRef] = None
    var _procedureAbsUriIteratorKey : scala.Option[String] = None

    val keylistprocedureAbsUriIterator = List(AlirIntraProceduralModule.globalProcedureAbsUriIteratorKey)
    keylistprocedureAbsUriIterator.foreach(key =>
      if (job ? key) {
        if (_procedureAbsUriIterator.isEmpty) {
          _procedureAbsUriIterator = Some(job(key))
          _procedureAbsUriIteratorKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _procedureAbsUriIterator.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'procedureAbsUriIterator' keys '" + _procedureAbsUriIteratorKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _procedureAbsUriIterator match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Option[scala.collection.Iterator[java.lang.String]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'procedureAbsUriIterator'.  Expecting 'scala.Option[scala.collection.Iterator[java.lang.String]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'procedureAbsUriIterator'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? AlirIntraProceduralModule.globalResultKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'result'. Expecting (AlirIntraProceduralModule.globalResultKey)")
    }

    if (job ? AlirIntraProceduralModule.globalResultKey && !job(AlirIntraProceduralModule.globalResultKey).isInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.core.module.AlirIntraProcedural.AlirIntraproceduralAnalysisResult]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for AlirIntraProceduralModule.globalResultKey.  Expecting 'scala.collection.mutable.Map[java.lang.String, org.sireum.core.module.AlirIntraProcedural.AlirIntraproceduralAnalysisResult]' but found '" +
          job(AlirIntraProceduralModule.globalResultKey).getClass.toString + "'")
    }
    return tags
  }

  def getResult(options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[java.lang.String, org.sireum.core.module.AlirIntraProcedural.AlirIntraproceduralAnalysisResult] = {
    if (options.contains(AlirIntraProceduralModule.globalResultKey)) {
      return options(AlirIntraProceduralModule.globalResultKey).asInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.core.module.AlirIntraProcedural.AlirIntraproceduralAnalysisResult]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetResult(options : MMap[Property.Key, Any], result : scala.collection.mutable.Map[java.lang.String, org.sireum.core.module.AlirIntraProcedural.AlirIntraproceduralAnalysisResult]) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalResultKey) = result

    return options
  }

  def modGetParallel(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(AlirIntraProceduralModule.globalParallelKey)) {
      return options(AlirIntraProceduralModule.globalParallelKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setParallel(options : MMap[Property.Key, Any], parallel : scala.Boolean) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalParallelKey) = parallel

    return options
  }

  def modGetSymbolTable(options : scala.collection.Map[Property.Key, Any]) : org.sireum.pilar.symbol.SymbolTable = {
    if (options.contains(AlirIntraProceduralModule.globalSymbolTableKey)) {
      return options(AlirIntraProceduralModule.globalSymbolTableKey).asInstanceOf[org.sireum.pilar.symbol.SymbolTable]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSymbolTable(options : MMap[Property.Key, Any], symbolTable : org.sireum.pilar.symbol.SymbolTable) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalSymbolTableKey) = symbolTable

    return options
  }

  def modGetShouldBuildCfg(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(AlirIntraProceduralModule.globalShouldBuildCfgKey)) {
      return options(AlirIntraProceduralModule.globalShouldBuildCfgKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setShouldBuildCfg(options : MMap[Property.Key, Any], shouldBuildCfg : scala.Boolean) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalShouldBuildCfgKey) = shouldBuildCfg

    return options
  }

  def modGetShouldBuildIdg(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(AlirIntraProceduralModule.globalShouldBuildIdgKey)) {
      return options(AlirIntraProceduralModule.globalShouldBuildIdgKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setShouldBuildIdg(options : MMap[Property.Key, Any], shouldBuildIdg : scala.Boolean) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalShouldBuildIdgKey) = shouldBuildIdg

    return options
  }

  def modGetShouldBuildDfg(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(AlirIntraProceduralModule.globalShouldBuildDfgKey)) {
      return options(AlirIntraProceduralModule.globalShouldBuildDfgKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setShouldBuildDfg(options : MMap[Property.Key, Any], shouldBuildDfg : scala.Boolean) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalShouldBuildDfgKey) = shouldBuildDfg

    return options
  }

  def modGetShouldBuildCdg(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(AlirIntraProceduralModule.globalShouldBuildCdgKey)) {
      return options(AlirIntraProceduralModule.globalShouldBuildCdgKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setShouldBuildCdg(options : MMap[Property.Key, Any], shouldBuildCdg : scala.Boolean) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalShouldBuildCdgKey) = shouldBuildCdg

    return options
  }

  def modGetShouldBuildRda(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(AlirIntraProceduralModule.globalShouldBuildRdaKey)) {
      return options(AlirIntraProceduralModule.globalShouldBuildRdaKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setShouldBuildRda(options : MMap[Property.Key, Any], shouldBuildRda : scala.Boolean) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalShouldBuildRdaKey) = shouldBuildRda

    return options
  }

  def modGetShouldBuildDdg(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(AlirIntraProceduralModule.globalShouldBuildDdgKey)) {
      return options(AlirIntraProceduralModule.globalShouldBuildDdgKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setShouldBuildDdg(options : MMap[Property.Key, Any], shouldBuildDdg : scala.Boolean) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalShouldBuildDdgKey) = shouldBuildDdg

    return options
  }

  def modGetShouldBuildPdg(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(AlirIntraProceduralModule.globalShouldBuildPdgKey)) {
      return options(AlirIntraProceduralModule.globalShouldBuildPdgKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setShouldBuildPdg(options : MMap[Property.Key, Any], shouldBuildPdg : scala.Boolean) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalShouldBuildPdgKey) = shouldBuildPdg

    return options
  }

  def modGetShouldIncludeFlowFunction(options : scala.collection.Map[Property.Key, Any]) : scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]] = {
    if (options.contains(AlirIntraProceduralModule.globalShouldIncludeFlowFunctionKey)) {
      return options(AlirIntraProceduralModule.globalShouldIncludeFlowFunctionKey).asInstanceOf[scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setShouldIncludeFlowFunction(options : MMap[Property.Key, Any], shouldIncludeFlowFunction : scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]]) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalShouldIncludeFlowFunctionKey) = shouldIncludeFlowFunction

    return options
  }

  def modGetDefRef(options : scala.collection.Map[Property.Key, Any]) : scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef] = {
    if (options.contains(AlirIntraProceduralModule.globalDefRefKey)) {
      return options(AlirIntraProceduralModule.globalDefRefKey).asInstanceOf[scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setDefRef(options : MMap[Property.Key, Any], defRef : scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalDefRefKey) = defRef

    return options
  }

  def modGetIsInputOutputParamPredicate(options : scala.collection.Map[Property.Key, Any]) : scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]] = {
    if (options.contains(AlirIntraProceduralModule.globalIsInputOutputParamPredicateKey)) {
      return options(AlirIntraProceduralModule.globalIsInputOutputParamPredicateKey).asInstanceOf[scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setIsInputOutputParamPredicate(options : MMap[Property.Key, Any], isInputOutputParamPredicate : scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalIsInputOutputParamPredicateKey) = isInputOutputParamPredicate

    return options
  }

  def modGetSwitchAsOrderedMatch(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(AlirIntraProceduralModule.globalSwitchAsOrderedMatchKey)) {
      return options(AlirIntraProceduralModule.globalSwitchAsOrderedMatchKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSwitchAsOrderedMatch(options : MMap[Property.Key, Any], switchAsOrderedMatch : scala.Boolean) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalSwitchAsOrderedMatchKey) = switchAsOrderedMatch

    return options
  }

  def modGetProcedureAbsUriIterator(options : scala.collection.Map[Property.Key, Any]) : scala.Option[scala.collection.Iterator[java.lang.String]] = {
    if (options.contains(AlirIntraProceduralModule.globalProcedureAbsUriIteratorKey)) {
      return options(AlirIntraProceduralModule.globalProcedureAbsUriIteratorKey).asInstanceOf[scala.Option[scala.collection.Iterator[java.lang.String]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setProcedureAbsUriIterator(options : MMap[Property.Key, Any], procedureAbsUriIterator : scala.Option[scala.collection.Iterator[java.lang.String]]) : MMap[Property.Key, Any] = {

    options(AlirIntraProceduralModule.globalProcedureAbsUriIteratorKey) = procedureAbsUriIterator

    return options
  }
}

trait AlirIntraProceduralModule {
  def job : PipelineJob

  def result_=(result : scala.collection.mutable.Map[java.lang.String, org.sireum.core.module.AlirIntraProcedural.AlirIntraproceduralAnalysisResult]) { AlirIntraProceduralModule.modSetResult(job.properties, result) }

  def parallel : scala.Boolean = AlirIntraProceduralModule.modGetParallel(job.properties)

  def symbolTable : org.sireum.pilar.symbol.SymbolTable = AlirIntraProceduralModule.modGetSymbolTable(job.properties)

  def shouldBuildCfg : scala.Boolean = AlirIntraProceduralModule.modGetShouldBuildCfg(job.properties)

  def shouldBuildIdg : scala.Boolean = AlirIntraProceduralModule.modGetShouldBuildIdg(job.properties)

  def shouldBuildDfg : scala.Boolean = AlirIntraProceduralModule.modGetShouldBuildDfg(job.properties)

  def shouldBuildCdg : scala.Boolean = AlirIntraProceduralModule.modGetShouldBuildCdg(job.properties)

  def shouldBuildRda : scala.Boolean = AlirIntraProceduralModule.modGetShouldBuildRda(job.properties)

  def shouldBuildDdg : scala.Boolean = AlirIntraProceduralModule.modGetShouldBuildDdg(job.properties)

  def shouldBuildPdg : scala.Boolean = AlirIntraProceduralModule.modGetShouldBuildPdg(job.properties)

  def shouldIncludeFlowFunction : scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]] = AlirIntraProceduralModule.modGetShouldIncludeFlowFunction(job.properties)

  def defRef : scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef] = AlirIntraProceduralModule.modGetDefRef(job.properties)

  def isInputOutputParamPredicate : scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]] = AlirIntraProceduralModule.modGetIsInputOutputParamPredicate(job.properties)

  def switchAsOrderedMatch : scala.Boolean = AlirIntraProceduralModule.modGetSwitchAsOrderedMatch(job.properties)

  def procedureAbsUriIterator : scala.Option[scala.collection.Iterator[java.lang.String]] = AlirIntraProceduralModule.modGetProcedureAbsUriIterator(job.properties)
}

object CfgModule extends PipelineModule {
  def title = "Control Flow Graph Builder"
  def origin = classOf[Cfg]

  val globalShouldIncludeFlowFunctionKey = "Global.shouldIncludeFlowFunction"
  val cfgKey = "Cfg.cfg"
  val globalPoolKey = "Global.pool"
  val globalCfgKey = "Global.cfg"
  val poolKey = "Cfg.pool"
  val globalProcedureSymbolTableKey = "Global.procedureSymbolTable"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.CfgDef")
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
    var _shouldIncludeFlowFunction : scala.Option[AnyRef] = None
    var _shouldIncludeFlowFunctionKey : scala.Option[String] = None

    val keylistshouldIncludeFlowFunction = List(CfgModule.globalShouldIncludeFlowFunctionKey)
    keylistshouldIncludeFlowFunction.foreach(key =>
      if (job ? key) {
        if (_shouldIncludeFlowFunction.isEmpty) {
          _shouldIncludeFlowFunction = Some(job(key))
          _shouldIncludeFlowFunctionKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _shouldIncludeFlowFunction.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'shouldIncludeFlowFunction' keys '" + _shouldIncludeFlowFunctionKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _shouldIncludeFlowFunction match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'shouldIncludeFlowFunction'.  Expecting 'scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'shouldIncludeFlowFunction'")
    }
    var _procedureSymbolTable : scala.Option[AnyRef] = None
    var _procedureSymbolTableKey : scala.Option[String] = None

    val keylistprocedureSymbolTable = List(CfgModule.globalProcedureSymbolTableKey)
    keylistprocedureSymbolTable.foreach(key =>
      if (job ? key) {
        if (_procedureSymbolTable.isEmpty) {
          _procedureSymbolTable = Some(job(key))
          _procedureSymbolTableKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _procedureSymbolTable.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'procedureSymbolTable' keys '" + _procedureSymbolTableKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _procedureSymbolTable match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.pilar.symbol.ProcedureSymbolTable]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'procedureSymbolTable'.  Expecting 'org.sireum.pilar.symbol.ProcedureSymbolTable' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'procedureSymbolTable'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? CfgModule.poolKey) && !(job ? CfgModule.globalPoolKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'pool'. Expecting (CfgModule.poolKey or CfgModule.globalPoolKey)")
    }

    if (job ? CfgModule.poolKey && !job(CfgModule.poolKey).isInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for CfgModule.poolKey.  Expecting 'scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]' but found '" +
          job(CfgModule.poolKey).getClass.toString + "'")
    }

    if (job ? CfgModule.globalPoolKey && !job(CfgModule.globalPoolKey).isInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for CfgModule.globalPoolKey.  Expecting 'scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]' but found '" +
          job(CfgModule.globalPoolKey).getClass.toString + "'")
    }

    if (!(job ? CfgModule.cfgKey) && !(job ? CfgModule.globalCfgKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'cfg'. Expecting (CfgModule.cfgKey or CfgModule.globalCfgKey)")
    }

    if (job ? CfgModule.cfgKey && !job(CfgModule.cfgKey).isInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for CfgModule.cfgKey.  Expecting 'org.sireum.alir.ControlFlowGraph[java.lang.String]' but found '" +
          job(CfgModule.cfgKey).getClass.toString + "'")
    }

    if (job ? CfgModule.globalCfgKey && !job(CfgModule.globalCfgKey).isInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for CfgModule.globalCfgKey.  Expecting 'org.sireum.alir.ControlFlowGraph[java.lang.String]' but found '" +
          job(CfgModule.globalCfgKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetShouldIncludeFlowFunction(options : scala.collection.Map[Property.Key, Any]) : scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]] = {
    if (options.contains(CfgModule.globalShouldIncludeFlowFunctionKey)) {
      return options(CfgModule.globalShouldIncludeFlowFunctionKey).asInstanceOf[scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setShouldIncludeFlowFunction(options : MMap[Property.Key, Any], shouldIncludeFlowFunction : scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]]) : MMap[Property.Key, Any] = {

    options(CfgModule.globalShouldIncludeFlowFunctionKey) = shouldIncludeFlowFunction

    return options
  }

  def getPool(options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = {
    if (options.contains(CfgModule.globalPoolKey)) {
      return options(CfgModule.globalPoolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }
    if (options.contains(CfgModule.poolKey)) {
      return options(CfgModule.poolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetPool(options : MMap[Property.Key, Any], pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]) : MMap[Property.Key, Any] = {

    options(CfgModule.globalPoolKey) = pool
    options(CfgModule.poolKey) = pool

    return options
  }

  def getCfg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ControlFlowGraph[java.lang.String] = {
    if (options.contains(CfgModule.globalCfgKey)) {
      return options(CfgModule.globalCfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }
    if (options.contains(CfgModule.cfgKey)) {
      return options(CfgModule.cfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetCfg(options : MMap[Property.Key, Any], cfg : org.sireum.alir.ControlFlowGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(CfgModule.globalCfgKey) = cfg
    options(CfgModule.cfgKey) = cfg

    return options
  }

  def modGetProcedureSymbolTable(options : scala.collection.Map[Property.Key, Any]) : org.sireum.pilar.symbol.ProcedureSymbolTable = {
    if (options.contains(CfgModule.globalProcedureSymbolTableKey)) {
      return options(CfgModule.globalProcedureSymbolTableKey).asInstanceOf[org.sireum.pilar.symbol.ProcedureSymbolTable]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setProcedureSymbolTable(options : MMap[Property.Key, Any], procedureSymbolTable : org.sireum.pilar.symbol.ProcedureSymbolTable) : MMap[Property.Key, Any] = {

    options(CfgModule.globalProcedureSymbolTableKey) = procedureSymbolTable

    return options
  }
}

trait CfgModule {
  def job : PipelineJob

  def shouldIncludeFlowFunction : scala.Function2[org.sireum.pilar.ast.LocationDecl, scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Tuple2[scala.collection.Iterable[org.sireum.pilar.ast.CatchClause], scala.Boolean]] = CfgModule.modGetShouldIncludeFlowFunction(job.properties)

  def pool_=(pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]) { CfgModule.modSetPool(job.properties, pool) }

  def cfg_=(cfg : org.sireum.alir.ControlFlowGraph[java.lang.String]) { CfgModule.modSetCfg(job.properties, cfg) }

  def procedureSymbolTable : org.sireum.pilar.symbol.ProcedureSymbolTable = CfgModule.modGetProcedureSymbolTable(job.properties)
}

object IdgModule extends PipelineModule {
  def title = "Immediate Dominator Graph Builder"
  def origin = classOf[Idg]

  val globalCfgKey = "Global.cfg"
  val globalPoolKey = "Global.pool"
  val idgKey = "Idg.idg"
  val globalIdgKey = "Global.idg"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.IdgDef")
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
    val deps = ilist[PipelineModule](CfgModule, CfgModule)
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
    var _pool : scala.Option[AnyRef] = None
    var _poolKey : scala.Option[String] = None

    val keylistpool = List(IdgModule.globalPoolKey, CfgModule.poolKey)
    keylistpool.foreach(key =>
      if (job ? key) {
        if (_pool.isEmpty) {
          _pool = Some(job(key))
          _poolKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _pool.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'pool' keys '" + _poolKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _pool match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'pool'.  Expecting 'scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'pool'")
    }
    var _cfg : scala.Option[AnyRef] = None
    var _cfgKey : scala.Option[String] = None

    val keylistcfg = List(IdgModule.globalCfgKey, CfgModule.cfgKey)
    keylistcfg.foreach(key =>
      if (job ? key) {
        if (_cfg.isEmpty) {
          _cfg = Some(job(key))
          _cfgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _cfg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'cfg' keys '" + _cfgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _cfg match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'cfg'.  Expecting 'org.sireum.alir.ControlFlowGraph[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'cfg'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? IdgModule.idgKey) && !(job ? IdgModule.globalIdgKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'idg'. Expecting (IdgModule.idgKey or IdgModule.globalIdgKey)")
    }

    if (job ? IdgModule.idgKey && !job(IdgModule.idgKey).isInstanceOf[org.sireum.alir.ImmediateDominatorGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for IdgModule.idgKey.  Expecting 'org.sireum.alir.ImmediateDominatorGraph[java.lang.String]' but found '" +
          job(IdgModule.idgKey).getClass.toString + "'")
    }

    if (job ? IdgModule.globalIdgKey && !job(IdgModule.globalIdgKey).isInstanceOf[org.sireum.alir.ImmediateDominatorGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for IdgModule.globalIdgKey.  Expecting 'org.sireum.alir.ImmediateDominatorGraph[java.lang.String]' but found '" +
          job(IdgModule.globalIdgKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetPool(options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = {
    if (options.contains(IdgModule.globalPoolKey)) {
      return options(IdgModule.globalPoolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }
    if (options.contains(CfgModule.poolKey)) {
      return options(CfgModule.poolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setPool(options : MMap[Property.Key, Any], pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]) : MMap[Property.Key, Any] = {

    options(IdgModule.globalPoolKey) = pool
    options(CfgModule.poolKey) = pool

    return options
  }

  def modGetCfg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ControlFlowGraph[java.lang.String] = {
    if (options.contains(IdgModule.globalCfgKey)) {
      return options(IdgModule.globalCfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }
    if (options.contains(CfgModule.cfgKey)) {
      return options(CfgModule.cfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setCfg(options : MMap[Property.Key, Any], cfg : org.sireum.alir.ControlFlowGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(IdgModule.globalCfgKey) = cfg
    options(CfgModule.cfgKey) = cfg

    return options
  }

  def getIdg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ImmediateDominatorGraph[java.lang.String] = {
    if (options.contains(IdgModule.globalIdgKey)) {
      return options(IdgModule.globalIdgKey).asInstanceOf[org.sireum.alir.ImmediateDominatorGraph[java.lang.String]]
    }
    if (options.contains(IdgModule.idgKey)) {
      return options(IdgModule.idgKey).asInstanceOf[org.sireum.alir.ImmediateDominatorGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetIdg(options : MMap[Property.Key, Any], idg : org.sireum.alir.ImmediateDominatorGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(IdgModule.globalIdgKey) = idg
    options(IdgModule.idgKey) = idg

    return options
  }
}

trait IdgModule {
  def job : PipelineJob

  def pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = IdgModule.modGetPool(job.properties)

  def cfg : org.sireum.alir.ControlFlowGraph[java.lang.String] = IdgModule.modGetCfg(job.properties)

  def idg_=(idg : org.sireum.alir.ImmediateDominatorGraph[java.lang.String]) { IdgModule.modSetIdg(job.properties, idg) }
}

object DfgModule extends PipelineModule {
  def title = "Dominance Frontier Graph Builder"
  def origin = classOf[Dfg]

  val globalDfgKey = "Global.dfg"
  val globalCfgKey = "Global.cfg"
  val globalPoolKey = "Global.pool"
  val dfgKey = "Dfg.dfg"
  val globalIdgKey = "Global.idg"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.DfgDef")
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
    val deps = ilist[PipelineModule](CfgModule, CfgModule, IdgModule)
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
    var _pool : scala.Option[AnyRef] = None
    var _poolKey : scala.Option[String] = None

    val keylistpool = List(DfgModule.globalPoolKey, CfgModule.poolKey)
    keylistpool.foreach(key =>
      if (job ? key) {
        if (_pool.isEmpty) {
          _pool = Some(job(key))
          _poolKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _pool.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'pool' keys '" + _poolKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _pool match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'pool'.  Expecting 'scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'pool'")
    }
    var _cfg : scala.Option[AnyRef] = None
    var _cfgKey : scala.Option[String] = None

    val keylistcfg = List(DfgModule.globalCfgKey, CfgModule.cfgKey)
    keylistcfg.foreach(key =>
      if (job ? key) {
        if (_cfg.isEmpty) {
          _cfg = Some(job(key))
          _cfgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _cfg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'cfg' keys '" + _cfgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _cfg match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'cfg'.  Expecting 'org.sireum.alir.ControlFlowGraph[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'cfg'")
    }
    var _idg : scala.Option[AnyRef] = None
    var _idgKey : scala.Option[String] = None

    val keylistidg = List(DfgModule.globalIdgKey, IdgModule.idgKey)
    keylistidg.foreach(key =>
      if (job ? key) {
        if (_idg.isEmpty) {
          _idg = Some(job(key))
          _idgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _idg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'idg' keys '" + _idgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _idg match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.alir.ImmediateDominatorGraph[java.lang.String]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'idg'.  Expecting 'org.sireum.alir.ImmediateDominatorGraph[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'idg'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? DfgModule.dfgKey) && !(job ? DfgModule.globalDfgKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'dfg'. Expecting (DfgModule.dfgKey or DfgModule.globalDfgKey)")
    }

    if (job ? DfgModule.dfgKey && !job(DfgModule.dfgKey).isInstanceOf[org.sireum.alir.DominanceFrontierGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for DfgModule.dfgKey.  Expecting 'org.sireum.alir.DominanceFrontierGraph[java.lang.String]' but found '" +
          job(DfgModule.dfgKey).getClass.toString + "'")
    }

    if (job ? DfgModule.globalDfgKey && !job(DfgModule.globalDfgKey).isInstanceOf[org.sireum.alir.DominanceFrontierGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for DfgModule.globalDfgKey.  Expecting 'org.sireum.alir.DominanceFrontierGraph[java.lang.String]' but found '" +
          job(DfgModule.globalDfgKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetPool(options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = {
    if (options.contains(DfgModule.globalPoolKey)) {
      return options(DfgModule.globalPoolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }
    if (options.contains(CfgModule.poolKey)) {
      return options(CfgModule.poolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setPool(options : MMap[Property.Key, Any], pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]) : MMap[Property.Key, Any] = {

    options(DfgModule.globalPoolKey) = pool
    options(CfgModule.poolKey) = pool

    return options
  }

  def modGetCfg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ControlFlowGraph[java.lang.String] = {
    if (options.contains(DfgModule.globalCfgKey)) {
      return options(DfgModule.globalCfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }
    if (options.contains(CfgModule.cfgKey)) {
      return options(CfgModule.cfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setCfg(options : MMap[Property.Key, Any], cfg : org.sireum.alir.ControlFlowGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(DfgModule.globalCfgKey) = cfg
    options(CfgModule.cfgKey) = cfg

    return options
  }

  def modGetIdg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ImmediateDominatorGraph[java.lang.String] = {
    if (options.contains(DfgModule.globalIdgKey)) {
      return options(DfgModule.globalIdgKey).asInstanceOf[org.sireum.alir.ImmediateDominatorGraph[java.lang.String]]
    }
    if (options.contains(IdgModule.idgKey)) {
      return options(IdgModule.idgKey).asInstanceOf[org.sireum.alir.ImmediateDominatorGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setIdg(options : MMap[Property.Key, Any], idg : org.sireum.alir.ImmediateDominatorGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(DfgModule.globalIdgKey) = idg
    options(IdgModule.idgKey) = idg

    return options
  }

  def getDfg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.DominanceFrontierGraph[java.lang.String] = {
    if (options.contains(DfgModule.globalDfgKey)) {
      return options(DfgModule.globalDfgKey).asInstanceOf[org.sireum.alir.DominanceFrontierGraph[java.lang.String]]
    }
    if (options.contains(DfgModule.dfgKey)) {
      return options(DfgModule.dfgKey).asInstanceOf[org.sireum.alir.DominanceFrontierGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetDfg(options : MMap[Property.Key, Any], dfg : org.sireum.alir.DominanceFrontierGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(DfgModule.globalDfgKey) = dfg
    options(DfgModule.dfgKey) = dfg

    return options
  }
}

trait DfgModule {
  def job : PipelineJob

  def pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = DfgModule.modGetPool(job.properties)

  def cfg : org.sireum.alir.ControlFlowGraph[java.lang.String] = DfgModule.modGetCfg(job.properties)

  def idg : org.sireum.alir.ImmediateDominatorGraph[java.lang.String] = DfgModule.modGetIdg(job.properties)

  def dfg_=(dfg : org.sireum.alir.DominanceFrontierGraph[java.lang.String]) { DfgModule.modSetDfg(job.properties, dfg) }
}

object CdgModule extends PipelineModule {
  def title = "Control Dependence Graph Builder"
  def origin = classOf[Cdg]

  val globalCfgKey = "Global.cfg"
  val globalPoolKey = "Global.pool"
  val cdgKey = "Cdg.cdg"
  val globalCdgKey = "Global.cdg"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.CdgDef")
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
    val deps = ilist[PipelineModule](CfgModule, CfgModule)
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
    var _pool : scala.Option[AnyRef] = None
    var _poolKey : scala.Option[String] = None

    val keylistpool = List(CdgModule.globalPoolKey, CfgModule.poolKey)
    keylistpool.foreach(key =>
      if (job ? key) {
        if (_pool.isEmpty) {
          _pool = Some(job(key))
          _poolKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _pool.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'pool' keys '" + _poolKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _pool match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'pool'.  Expecting 'scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'pool'")
    }
    var _cfg : scala.Option[AnyRef] = None
    var _cfgKey : scala.Option[String] = None

    val keylistcfg = List(CdgModule.globalCfgKey, CfgModule.cfgKey)
    keylistcfg.foreach(key =>
      if (job ? key) {
        if (_cfg.isEmpty) {
          _cfg = Some(job(key))
          _cfgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _cfg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'cfg' keys '" + _cfgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _cfg match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'cfg'.  Expecting 'org.sireum.alir.ControlFlowGraph[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'cfg'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? CdgModule.cdgKey) && !(job ? CdgModule.globalCdgKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'cdg'. Expecting (CdgModule.cdgKey or CdgModule.globalCdgKey)")
    }

    if (job ? CdgModule.cdgKey && !job(CdgModule.cdgKey).isInstanceOf[org.sireum.alir.ControlDependenceGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for CdgModule.cdgKey.  Expecting 'org.sireum.alir.ControlDependenceGraph[java.lang.String]' but found '" +
          job(CdgModule.cdgKey).getClass.toString + "'")
    }

    if (job ? CdgModule.globalCdgKey && !job(CdgModule.globalCdgKey).isInstanceOf[org.sireum.alir.ControlDependenceGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for CdgModule.globalCdgKey.  Expecting 'org.sireum.alir.ControlDependenceGraph[java.lang.String]' but found '" +
          job(CdgModule.globalCdgKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetPool(options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = {
    if (options.contains(CdgModule.globalPoolKey)) {
      return options(CdgModule.globalPoolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }
    if (options.contains(CfgModule.poolKey)) {
      return options(CfgModule.poolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setPool(options : MMap[Property.Key, Any], pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]) : MMap[Property.Key, Any] = {

    options(CdgModule.globalPoolKey) = pool
    options(CfgModule.poolKey) = pool

    return options
  }

  def modGetCfg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ControlFlowGraph[java.lang.String] = {
    if (options.contains(CdgModule.globalCfgKey)) {
      return options(CdgModule.globalCfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }
    if (options.contains(CfgModule.cfgKey)) {
      return options(CfgModule.cfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setCfg(options : MMap[Property.Key, Any], cfg : org.sireum.alir.ControlFlowGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(CdgModule.globalCfgKey) = cfg
    options(CfgModule.cfgKey) = cfg

    return options
  }

  def getCdg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ControlDependenceGraph[java.lang.String] = {
    if (options.contains(CdgModule.globalCdgKey)) {
      return options(CdgModule.globalCdgKey).asInstanceOf[org.sireum.alir.ControlDependenceGraph[java.lang.String]]
    }
    if (options.contains(CdgModule.cdgKey)) {
      return options(CdgModule.cdgKey).asInstanceOf[org.sireum.alir.ControlDependenceGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetCdg(options : MMap[Property.Key, Any], cdg : org.sireum.alir.ControlDependenceGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(CdgModule.globalCdgKey) = cdg
    options(CdgModule.cdgKey) = cdg

    return options
  }
}

trait CdgModule {
  def job : PipelineJob

  def pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = CdgModule.modGetPool(job.properties)

  def cfg : org.sireum.alir.ControlFlowGraph[java.lang.String] = CdgModule.modGetCfg(job.properties)

  def cdg_=(cdg : org.sireum.alir.ControlDependenceGraph[java.lang.String]) { CdgModule.modSetCdg(job.properties, cdg) }
}

object RdaModule extends PipelineModule {
  def title = "Reaching Definition Analysis Builder"
  def origin = classOf[Rda]

  val globalIsInputOutputParamPredicateKey = "Global.isInputOutputParamPredicate"
  val globalCfgKey = "Global.cfg"
  val globalRdaKey = "Global.rda"
  val rdaKey = "Rda.rda"
  val globalSwitchAsOrderedMatchKey = "Global.switchAsOrderedMatch"
  val globalProcedureSymbolTableKey = "Global.procedureSymbolTable"
  val globalDefRefKey = "Global.defRef"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.RdaDef")
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
    if (!(job ? RdaModule.globalDefRefKey)) {
      val defRef = Class.forName("org.sireum.core.module.Rda").getDeclaredMethod("$lessinit$greater$default$4").invoke(null).asInstanceOf[scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]]
      setDefRef(job.properties, defRef)
    }

    if (!(job ? RdaModule.globalSwitchAsOrderedMatchKey)) {
      val switchAsOrderedMatch = Class.forName("org.sireum.core.module.Rda").getDeclaredMethod("$lessinit$greater$default$6").invoke(null).asInstanceOf[scala.Boolean]
      setSwitchAsOrderedMatch(job.properties, switchAsOrderedMatch)
    }
  }

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ilist[PipelineModule](CfgModule)
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
    var _defRef : scala.Option[AnyRef] = None
    var _defRefKey : scala.Option[String] = None

    val keylistdefRef = List(RdaModule.globalDefRefKey)
    keylistdefRef.foreach(key =>
      if (job ? key) {
        if (_defRef.isEmpty) {
          _defRef = Some(job(key))
          _defRefKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _defRef.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'defRef' keys '" + _defRefKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _defRef match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'defRef'.  Expecting 'scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'defRef'")
    }
    var _isInputOutputParamPredicate : scala.Option[AnyRef] = None
    var _isInputOutputParamPredicateKey : scala.Option[String] = None

    val keylistisInputOutputParamPredicate = List(RdaModule.globalIsInputOutputParamPredicateKey)
    keylistisInputOutputParamPredicate.foreach(key =>
      if (job ? key) {
        if (_isInputOutputParamPredicate.isEmpty) {
          _isInputOutputParamPredicate = Some(job(key))
          _isInputOutputParamPredicateKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _isInputOutputParamPredicate.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'isInputOutputParamPredicate' keys '" + _isInputOutputParamPredicateKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _isInputOutputParamPredicate match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'isInputOutputParamPredicate'.  Expecting 'scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'isInputOutputParamPredicate'")
    }
    var _switchAsOrderedMatch : scala.Option[AnyRef] = None
    var _switchAsOrderedMatchKey : scala.Option[String] = None

    val keylistswitchAsOrderedMatch = List(RdaModule.globalSwitchAsOrderedMatchKey)
    keylistswitchAsOrderedMatch.foreach(key =>
      if (job ? key) {
        if (_switchAsOrderedMatch.isEmpty) {
          _switchAsOrderedMatch = Some(job(key))
          _switchAsOrderedMatchKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _switchAsOrderedMatch.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'switchAsOrderedMatch' keys '" + _switchAsOrderedMatchKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _switchAsOrderedMatch match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Boolean]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'switchAsOrderedMatch'.  Expecting 'scala.Boolean' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'switchAsOrderedMatch'")
    }
    var _cfg : scala.Option[AnyRef] = None
    var _cfgKey : scala.Option[String] = None

    val keylistcfg = List(RdaModule.globalCfgKey, CfgModule.cfgKey)
    keylistcfg.foreach(key =>
      if (job ? key) {
        if (_cfg.isEmpty) {
          _cfg = Some(job(key))
          _cfgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _cfg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'cfg' keys '" + _cfgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _cfg match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'cfg'.  Expecting 'org.sireum.alir.ControlFlowGraph[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'cfg'")
    }
    var _procedureSymbolTable : scala.Option[AnyRef] = None
    var _procedureSymbolTableKey : scala.Option[String] = None

    val keylistprocedureSymbolTable = List(RdaModule.globalProcedureSymbolTableKey)
    keylistprocedureSymbolTable.foreach(key =>
      if (job ? key) {
        if (_procedureSymbolTable.isEmpty) {
          _procedureSymbolTable = Some(job(key))
          _procedureSymbolTableKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _procedureSymbolTable.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'procedureSymbolTable' keys '" + _procedureSymbolTableKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _procedureSymbolTable match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.pilar.symbol.ProcedureSymbolTable]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'procedureSymbolTable'.  Expecting 'org.sireum.pilar.symbol.ProcedureSymbolTable' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'procedureSymbolTable'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? RdaModule.rdaKey) && !(job ? RdaModule.globalRdaKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'rda'. Expecting (RdaModule.rdaKey or RdaModule.globalRdaKey)")
    }

    if (job ? RdaModule.rdaKey && !job(RdaModule.rdaKey).isInstanceOf[org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for RdaModule.rdaKey.  Expecting 'org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]' but found '" +
          job(RdaModule.rdaKey).getClass.toString + "'")
    }

    if (job ? RdaModule.globalRdaKey && !job(RdaModule.globalRdaKey).isInstanceOf[org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for RdaModule.globalRdaKey.  Expecting 'org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]' but found '" +
          job(RdaModule.globalRdaKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetDefRef(options : scala.collection.Map[Property.Key, Any]) : scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef] = {
    if (options.contains(RdaModule.globalDefRefKey)) {
      return options(RdaModule.globalDefRefKey).asInstanceOf[scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setDefRef(options : MMap[Property.Key, Any], defRef : scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]) : MMap[Property.Key, Any] = {

    options(RdaModule.globalDefRefKey) = defRef

    return options
  }

  def modGetIsInputOutputParamPredicate(options : scala.collection.Map[Property.Key, Any]) : scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]] = {
    if (options.contains(RdaModule.globalIsInputOutputParamPredicateKey)) {
      return options(RdaModule.globalIsInputOutputParamPredicateKey).asInstanceOf[scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setIsInputOutputParamPredicate(options : MMap[Property.Key, Any], isInputOutputParamPredicate : scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]) : MMap[Property.Key, Any] = {

    options(RdaModule.globalIsInputOutputParamPredicateKey) = isInputOutputParamPredicate

    return options
  }

  def modGetSwitchAsOrderedMatch(options : scala.collection.Map[Property.Key, Any]) : scala.Boolean = {
    if (options.contains(RdaModule.globalSwitchAsOrderedMatchKey)) {
      return options(RdaModule.globalSwitchAsOrderedMatchKey).asInstanceOf[scala.Boolean]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setSwitchAsOrderedMatch(options : MMap[Property.Key, Any], switchAsOrderedMatch : scala.Boolean) : MMap[Property.Key, Any] = {

    options(RdaModule.globalSwitchAsOrderedMatchKey) = switchAsOrderedMatch

    return options
  }

  def modGetCfg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ControlFlowGraph[java.lang.String] = {
    if (options.contains(RdaModule.globalCfgKey)) {
      return options(RdaModule.globalCfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }
    if (options.contains(CfgModule.cfgKey)) {
      return options(CfgModule.cfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setCfg(options : MMap[Property.Key, Any], cfg : org.sireum.alir.ControlFlowGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(RdaModule.globalCfgKey) = cfg
    options(CfgModule.cfgKey) = cfg

    return options
  }

  def modGetProcedureSymbolTable(options : scala.collection.Map[Property.Key, Any]) : org.sireum.pilar.symbol.ProcedureSymbolTable = {
    if (options.contains(RdaModule.globalProcedureSymbolTableKey)) {
      return options(RdaModule.globalProcedureSymbolTableKey).asInstanceOf[org.sireum.pilar.symbol.ProcedureSymbolTable]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setProcedureSymbolTable(options : MMap[Property.Key, Any], procedureSymbolTable : org.sireum.pilar.symbol.ProcedureSymbolTable) : MMap[Property.Key, Any] = {

    options(RdaModule.globalProcedureSymbolTableKey) = procedureSymbolTable

    return options
  }

  def getRda(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]] = {
    if (options.contains(RdaModule.globalRdaKey)) {
      return options(RdaModule.globalRdaKey).asInstanceOf[org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]
    }
    if (options.contains(RdaModule.rdaKey)) {
      return options(RdaModule.rdaKey).asInstanceOf[org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetRda(options : MMap[Property.Key, Any], rda : org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]) : MMap[Property.Key, Any] = {

    options(RdaModule.globalRdaKey) = rda
    options(RdaModule.rdaKey) = rda

    return options
  }
}

trait RdaModule {
  def job : PipelineJob

  def defRef : scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef] = RdaModule.modGetDefRef(job.properties)

  def isInputOutputParamPredicate : scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]] = RdaModule.modGetIsInputOutputParamPredicate(job.properties)

  def switchAsOrderedMatch : scala.Boolean = RdaModule.modGetSwitchAsOrderedMatch(job.properties)

  def cfg : org.sireum.alir.ControlFlowGraph[java.lang.String] = RdaModule.modGetCfg(job.properties)

  def procedureSymbolTable : org.sireum.pilar.symbol.ProcedureSymbolTable = RdaModule.modGetProcedureSymbolTable(job.properties)

  def rda_=(rda : org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]) { RdaModule.modSetRda(job.properties, rda) }
}

object DdgModule extends PipelineModule {
  def title = "Data Dependence Graph Builder"
  def origin = classOf[Ddg]

  val globalIsInputOutputParamPredicateKey = "Global.isInputOutputParamPredicate"
  val globalCfgKey = "Global.cfg"
  val globalPoolKey = "Global.pool"
  val globalRdaKey = "Global.rda"
  val globalDdgKey = "Global.ddg"
  val globalProcedureSymbolTableKey = "Global.procedureSymbolTable"
  val ddgKey = "Ddg.ddg"
  val globalDefRefKey = "Global.defRef"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.DdgDef")
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
    if (!(job ? DdgModule.globalDefRefKey)) {
      val defRef = Class.forName("org.sireum.core.module.Ddg").getDeclaredMethod("$lessinit$greater$default$6").invoke(null).asInstanceOf[scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]]
      setDefRef(job.properties, defRef)
    }
  }

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ilist[PipelineModule](CfgModule, CfgModule, RdaModule)
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
    var _defRef : scala.Option[AnyRef] = None
    var _defRefKey : scala.Option[String] = None

    val keylistdefRef = List(DdgModule.globalDefRefKey)
    keylistdefRef.foreach(key =>
      if (job ? key) {
        if (_defRef.isEmpty) {
          _defRef = Some(job(key))
          _defRefKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _defRef.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'defRef' keys '" + _defRefKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _defRef match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'defRef'.  Expecting 'scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'defRef'")
    }
    var _isInputOutputParamPredicate : scala.Option[AnyRef] = None
    var _isInputOutputParamPredicateKey : scala.Option[String] = None

    val keylistisInputOutputParamPredicate = List(DdgModule.globalIsInputOutputParamPredicateKey)
    keylistisInputOutputParamPredicate.foreach(key =>
      if (job ? key) {
        if (_isInputOutputParamPredicate.isEmpty) {
          _isInputOutputParamPredicate = Some(job(key))
          _isInputOutputParamPredicateKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _isInputOutputParamPredicate.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'isInputOutputParamPredicate' keys '" + _isInputOutputParamPredicateKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _isInputOutputParamPredicate match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'isInputOutputParamPredicate'.  Expecting 'scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'isInputOutputParamPredicate'")
    }
    var _pool : scala.Option[AnyRef] = None
    var _poolKey : scala.Option[String] = None

    val keylistpool = List(DdgModule.globalPoolKey, CfgModule.poolKey)
    keylistpool.foreach(key =>
      if (job ? key) {
        if (_pool.isEmpty) {
          _pool = Some(job(key))
          _poolKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _pool.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'pool' keys '" + _poolKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _pool match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'pool'.  Expecting 'scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'pool'")
    }
    var _cfg : scala.Option[AnyRef] = None
    var _cfgKey : scala.Option[String] = None

    val keylistcfg = List(DdgModule.globalCfgKey, CfgModule.cfgKey)
    keylistcfg.foreach(key =>
      if (job ? key) {
        if (_cfg.isEmpty) {
          _cfg = Some(job(key))
          _cfgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _cfg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'cfg' keys '" + _cfgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _cfg match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'cfg'.  Expecting 'org.sireum.alir.ControlFlowGraph[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'cfg'")
    }
    var _procedureSymbolTable : scala.Option[AnyRef] = None
    var _procedureSymbolTableKey : scala.Option[String] = None

    val keylistprocedureSymbolTable = List(DdgModule.globalProcedureSymbolTableKey)
    keylistprocedureSymbolTable.foreach(key =>
      if (job ? key) {
        if (_procedureSymbolTable.isEmpty) {
          _procedureSymbolTable = Some(job(key))
          _procedureSymbolTableKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _procedureSymbolTable.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'procedureSymbolTable' keys '" + _procedureSymbolTableKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _procedureSymbolTable match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.pilar.symbol.ProcedureSymbolTable]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'procedureSymbolTable'.  Expecting 'org.sireum.pilar.symbol.ProcedureSymbolTable' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'procedureSymbolTable'")
    }
    var _rda : scala.Option[AnyRef] = None
    var _rdaKey : scala.Option[String] = None

    val keylistrda = List(DdgModule.globalRdaKey, RdaModule.rdaKey)
    keylistrda.foreach(key =>
      if (job ? key) {
        if (_rda.isEmpty) {
          _rda = Some(job(key))
          _rdaKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _rda.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'rda' keys '" + _rdaKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _rda match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'rda'.  Expecting 'org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'rda'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? DdgModule.ddgKey) && !(job ? DdgModule.globalDdgKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'ddg'. Expecting (DdgModule.ddgKey or DdgModule.globalDdgKey)")
    }

    if (job ? DdgModule.ddgKey && !job(DdgModule.ddgKey).isInstanceOf[org.sireum.alir.DataDependenceGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for DdgModule.ddgKey.  Expecting 'org.sireum.alir.DataDependenceGraph[java.lang.String]' but found '" +
          job(DdgModule.ddgKey).getClass.toString + "'")
    }

    if (job ? DdgModule.globalDdgKey && !job(DdgModule.globalDdgKey).isInstanceOf[org.sireum.alir.DataDependenceGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for DdgModule.globalDdgKey.  Expecting 'org.sireum.alir.DataDependenceGraph[java.lang.String]' but found '" +
          job(DdgModule.globalDdgKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetDefRef(options : scala.collection.Map[Property.Key, Any]) : scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef] = {
    if (options.contains(DdgModule.globalDefRefKey)) {
      return options(DdgModule.globalDefRefKey).asInstanceOf[scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setDefRef(options : MMap[Property.Key, Any], defRef : scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef]) : MMap[Property.Key, Any] = {

    options(DdgModule.globalDefRefKey) = defRef

    return options
  }

  def modGetIsInputOutputParamPredicate(options : scala.collection.Map[Property.Key, Any]) : scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]] = {
    if (options.contains(DdgModule.globalIsInputOutputParamPredicateKey)) {
      return options(DdgModule.globalIsInputOutputParamPredicateKey).asInstanceOf[scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setIsInputOutputParamPredicate(options : MMap[Property.Key, Any], isInputOutputParamPredicate : scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]]) : MMap[Property.Key, Any] = {

    options(DdgModule.globalIsInputOutputParamPredicateKey) = isInputOutputParamPredicate

    return options
  }

  def modGetPool(options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = {
    if (options.contains(DdgModule.globalPoolKey)) {
      return options(DdgModule.globalPoolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }
    if (options.contains(CfgModule.poolKey)) {
      return options(CfgModule.poolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setPool(options : MMap[Property.Key, Any], pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]) : MMap[Property.Key, Any] = {

    options(DdgModule.globalPoolKey) = pool
    options(CfgModule.poolKey) = pool

    return options
  }

  def modGetCfg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ControlFlowGraph[java.lang.String] = {
    if (options.contains(DdgModule.globalCfgKey)) {
      return options(DdgModule.globalCfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }
    if (options.contains(CfgModule.cfgKey)) {
      return options(CfgModule.cfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setCfg(options : MMap[Property.Key, Any], cfg : org.sireum.alir.ControlFlowGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(DdgModule.globalCfgKey) = cfg
    options(CfgModule.cfgKey) = cfg

    return options
  }

  def getDdg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.DataDependenceGraph[java.lang.String] = {
    if (options.contains(DdgModule.globalDdgKey)) {
      return options(DdgModule.globalDdgKey).asInstanceOf[org.sireum.alir.DataDependenceGraph[java.lang.String]]
    }
    if (options.contains(DdgModule.ddgKey)) {
      return options(DdgModule.ddgKey).asInstanceOf[org.sireum.alir.DataDependenceGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetDdg(options : MMap[Property.Key, Any], ddg : org.sireum.alir.DataDependenceGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(DdgModule.globalDdgKey) = ddg
    options(DdgModule.ddgKey) = ddg

    return options
  }

  def modGetProcedureSymbolTable(options : scala.collection.Map[Property.Key, Any]) : org.sireum.pilar.symbol.ProcedureSymbolTable = {
    if (options.contains(DdgModule.globalProcedureSymbolTableKey)) {
      return options(DdgModule.globalProcedureSymbolTableKey).asInstanceOf[org.sireum.pilar.symbol.ProcedureSymbolTable]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setProcedureSymbolTable(options : MMap[Property.Key, Any], procedureSymbolTable : org.sireum.pilar.symbol.ProcedureSymbolTable) : MMap[Property.Key, Any] = {

    options(DdgModule.globalProcedureSymbolTableKey) = procedureSymbolTable

    return options
  }

  def modGetRda(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]] = {
    if (options.contains(DdgModule.globalRdaKey)) {
      return options(DdgModule.globalRdaKey).asInstanceOf[org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]
    }
    if (options.contains(RdaModule.rdaKey)) {
      return options(RdaModule.rdaKey).asInstanceOf[org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setRda(options : MMap[Property.Key, Any], rda : org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]) : MMap[Property.Key, Any] = {

    options(DdgModule.globalRdaKey) = rda
    options(RdaModule.rdaKey) = rda

    return options
  }
}

trait DdgModule {
  def job : PipelineJob

  def defRef : scala.Function1[org.sireum.pilar.symbol.SymbolTable, org.sireum.alir.DefRef] = DdgModule.modGetDefRef(job.properties)

  def isInputOutputParamPredicate : scala.Function1[org.sireum.pilar.symbol.ProcedureSymbolTable, scala.Tuple2[scala.Function1[java.lang.String, scala.Boolean], scala.Function1[java.lang.String, scala.Boolean]]] = DdgModule.modGetIsInputOutputParamPredicate(job.properties)

  def pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = DdgModule.modGetPool(job.properties)

  def cfg : org.sireum.alir.ControlFlowGraph[java.lang.String] = DdgModule.modGetCfg(job.properties)

  def ddg_=(ddg : org.sireum.alir.DataDependenceGraph[java.lang.String]) { DdgModule.modSetDdg(job.properties, ddg) }

  def procedureSymbolTable : org.sireum.pilar.symbol.ProcedureSymbolTable = DdgModule.modGetProcedureSymbolTable(job.properties)

  def rda : org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]] = DdgModule.modGetRda(job.properties)
}

object PdgModule extends PipelineModule {
  def title = "Program Dependence Graph Builder"
  def origin = classOf[Pdg]

  val globalCfgKey = "Global.cfg"
  val globalPoolKey = "Global.pool"
  val globalCdgKey = "Global.cdg"
  val globalDdgKey = "Global.ddg"
  val globalProcedureSymbolTableKey = "Global.procedureSymbolTable"
  val globalPdgKey = "Global.pdg"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.core.module.PdgDef")
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
    val deps = ilist[PipelineModule](CfgModule, CfgModule, CdgModule, DdgModule)
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
    var _pool : scala.Option[AnyRef] = None
    var _poolKey : scala.Option[String] = None

    val keylistpool = List(PdgModule.globalPoolKey, CfgModule.poolKey)
    keylistpool.foreach(key =>
      if (job ? key) {
        if (_pool.isEmpty) {
          _pool = Some(job(key))
          _poolKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _pool.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'pool' keys '" + _poolKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _pool match {
      case Some(x) =>
        if (!x.isInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'pool'.  Expecting 'scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'pool'")
    }
    var _cfg : scala.Option[AnyRef] = None
    var _cfgKey : scala.Option[String] = None

    val keylistcfg = List(PdgModule.globalCfgKey, CfgModule.cfgKey)
    keylistcfg.foreach(key =>
      if (job ? key) {
        if (_cfg.isEmpty) {
          _cfg = Some(job(key))
          _cfgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _cfg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'cfg' keys '" + _cfgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _cfg match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'cfg'.  Expecting 'org.sireum.alir.ControlFlowGraph[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'cfg'")
    }
    var _cdg : scala.Option[AnyRef] = None
    var _cdgKey : scala.Option[String] = None

    val keylistcdg = List(PdgModule.globalCdgKey, CdgModule.cdgKey)
    keylistcdg.foreach(key =>
      if (job ? key) {
        if (_cdg.isEmpty) {
          _cdg = Some(job(key))
          _cdgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _cdg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'cdg' keys '" + _cdgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _cdg match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.alir.ControlDependenceGraph[java.lang.String]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'cdg'.  Expecting 'org.sireum.alir.ControlDependenceGraph[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'cdg'")
    }
    var _ddg : scala.Option[AnyRef] = None
    var _ddgKey : scala.Option[String] = None

    val keylistddg = List(PdgModule.globalDdgKey, DdgModule.ddgKey)
    keylistddg.foreach(key =>
      if (job ? key) {
        if (_ddg.isEmpty) {
          _ddg = Some(job(key))
          _ddgKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _ddg.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'ddg' keys '" + _ddgKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _ddg match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.alir.DataDependenceGraph[java.lang.String]]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'ddg'.  Expecting 'org.sireum.alir.DataDependenceGraph[java.lang.String]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'ddg'")
    }
    var _procedureSymbolTable : scala.Option[AnyRef] = None
    var _procedureSymbolTableKey : scala.Option[String] = None

    val keylistprocedureSymbolTable = List(PdgModule.globalProcedureSymbolTableKey)
    keylistprocedureSymbolTable.foreach(key =>
      if (job ? key) {
        if (_procedureSymbolTable.isEmpty) {
          _procedureSymbolTable = Some(job(key))
          _procedureSymbolTableKey = Some(key)
        }
        if (!(job(key).asInstanceOf[AnyRef] eq _procedureSymbolTable.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'procedureSymbolTable' keys '" + _procedureSymbolTableKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _procedureSymbolTable match {
      case Some(x) =>
        if (!x.isInstanceOf[org.sireum.pilar.symbol.ProcedureSymbolTable]) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'procedureSymbolTable'.  Expecting 'org.sireum.pilar.symbol.ProcedureSymbolTable' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'procedureSymbolTable'")
    }
    return tags
  }

  def outputDefined(job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if (!(job ? PdgModule.globalPdgKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'pdg'. Expecting (PdgModule.globalPdgKey)")
    }

    if (job ? PdgModule.globalPdgKey && !job(PdgModule.globalPdgKey).isInstanceOf[org.sireum.alir.ProgramDependenceGraph[java.lang.String]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': Wrong type found for PdgModule.globalPdgKey.  Expecting 'org.sireum.alir.ProgramDependenceGraph[java.lang.String]' but found '" +
          job(PdgModule.globalPdgKey).getClass.toString + "'")
    }
    return tags
  }

  def modGetPool(options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = {
    if (options.contains(PdgModule.globalPoolKey)) {
      return options(PdgModule.globalPoolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }
    if (options.contains(CfgModule.poolKey)) {
      return options(CfgModule.poolKey).asInstanceOf[scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setPool(options : MMap[Property.Key, Any], pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode]) : MMap[Property.Key, Any] = {

    options(PdgModule.globalPoolKey) = pool
    options(CfgModule.poolKey) = pool

    return options
  }

  def modGetCfg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ControlFlowGraph[java.lang.String] = {
    if (options.contains(PdgModule.globalCfgKey)) {
      return options(PdgModule.globalCfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }
    if (options.contains(CfgModule.cfgKey)) {
      return options(CfgModule.cfgKey).asInstanceOf[org.sireum.alir.ControlFlowGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setCfg(options : MMap[Property.Key, Any], cfg : org.sireum.alir.ControlFlowGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(PdgModule.globalCfgKey) = cfg
    options(CfgModule.cfgKey) = cfg

    return options
  }

  def modGetCdg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ControlDependenceGraph[java.lang.String] = {
    if (options.contains(PdgModule.globalCdgKey)) {
      return options(PdgModule.globalCdgKey).asInstanceOf[org.sireum.alir.ControlDependenceGraph[java.lang.String]]
    }
    if (options.contains(CdgModule.cdgKey)) {
      return options(CdgModule.cdgKey).asInstanceOf[org.sireum.alir.ControlDependenceGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setCdg(options : MMap[Property.Key, Any], cdg : org.sireum.alir.ControlDependenceGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(PdgModule.globalCdgKey) = cdg
    options(CdgModule.cdgKey) = cdg

    return options
  }

  def modGetDdg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.DataDependenceGraph[java.lang.String] = {
    if (options.contains(PdgModule.globalDdgKey)) {
      return options(PdgModule.globalDdgKey).asInstanceOf[org.sireum.alir.DataDependenceGraph[java.lang.String]]
    }
    if (options.contains(DdgModule.ddgKey)) {
      return options(DdgModule.ddgKey).asInstanceOf[org.sireum.alir.DataDependenceGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setDdg(options : MMap[Property.Key, Any], ddg : org.sireum.alir.DataDependenceGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(PdgModule.globalDdgKey) = ddg
    options(DdgModule.ddgKey) = ddg

    return options
  }

  def modGetProcedureSymbolTable(options : scala.collection.Map[Property.Key, Any]) : org.sireum.pilar.symbol.ProcedureSymbolTable = {
    if (options.contains(PdgModule.globalProcedureSymbolTableKey)) {
      return options(PdgModule.globalProcedureSymbolTableKey).asInstanceOf[org.sireum.pilar.symbol.ProcedureSymbolTable]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setProcedureSymbolTable(options : MMap[Property.Key, Any], procedureSymbolTable : org.sireum.pilar.symbol.ProcedureSymbolTable) : MMap[Property.Key, Any] = {

    options(PdgModule.globalProcedureSymbolTableKey) = procedureSymbolTable

    return options
  }

  def getPdg(options : scala.collection.Map[Property.Key, Any]) : org.sireum.alir.ProgramDependenceGraph[java.lang.String] = {
    if (options.contains(PdgModule.globalPdgKey)) {
      return options(PdgModule.globalPdgKey).asInstanceOf[org.sireum.alir.ProgramDependenceGraph[java.lang.String]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def modSetPdg(options : MMap[Property.Key, Any], pdg : org.sireum.alir.ProgramDependenceGraph[java.lang.String]) : MMap[Property.Key, Any] = {

    options(PdgModule.globalPdgKey) = pdg

    return options
  }
}

trait PdgModule {
  def job : PipelineJob

  def pool : scala.collection.mutable.Map[org.sireum.alir.AlirIntraProceduralNode, org.sireum.alir.AlirIntraProceduralNode] = PdgModule.modGetPool(job.properties)

  def cfg : org.sireum.alir.ControlFlowGraph[java.lang.String] = PdgModule.modGetCfg(job.properties)

  def cdg : org.sireum.alir.ControlDependenceGraph[java.lang.String] = PdgModule.modGetCdg(job.properties)

  def ddg : org.sireum.alir.DataDependenceGraph[java.lang.String] = PdgModule.modGetDdg(job.properties)

  def procedureSymbolTable : org.sireum.pilar.symbol.ProcedureSymbolTable = PdgModule.modGetProcedureSymbolTable(job.properties)

  def pdg_=(pdg : org.sireum.alir.ProgramDependenceGraph[java.lang.String]) { PdgModule.modSetPdg(job.properties, pdg) }
}