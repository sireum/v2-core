/*
Copyright (c) 2011-2013 Jason Belt, Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.core.module

import org.sireum.pilar.symbol.ProcedureSymbolTable
import org.sireum.pipeline._
import org.sireum.util._
import org.sireum.alir._
import org.sireum.pilar.ast.CatchClause
import org.sireum.alir.DataDependenceGraph.DdgUtil
import org.sireum.alir.ProgramDependenceGraph.PdgUtil
import org.sireum.alir.ProgramDependenceGraph

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class AlirIntraProceduralModuleDef(val job : PipelineJob, info : PipelineJobModuleInfo)
    extends AlirIntraProceduralModule with ImplicitLogging {
  val par = this.parallel
  val st = this.symbolTable
  val psts = st.procedureSymbolTables.toSeq
  val siff = this.shouldIncludeFlowFunction
  val dr = this.defRef
  val iopp = this.isInputOutputParamPredicate
  val saom = this.switchAsOrderedMatch
  val pipeline = buildPipeline(job)

  def compute(pst : ProcedureSymbolTable) = {
    val j = PipelineJob()
    val options = j.properties
    CfgModule.setProcedureSymbolTable(options, pst)
    CfgModule.setShouldIncludeFlowFunction(options, siff)
    AlirIntraProceduralModule.setDefRef(options, dr)
    AlirIntraProceduralModule.setIsInputOutputParamPredicate(options, iopp)
    AlirIntraProceduralModule.setSwitchAsOrderedMatch(options, saom)
    pipeline.compute(j)
    info.hasError = j.hasError
    if (info.hasError) {
      info.exception = Some(ErrorneousModulesThrowable(
        j.lastStageInfo.info.filter { i => i.hasError }))
    }
    println(Tag.collateAsString(j.lastStageInfo.tags))
    val pool = CfgModule.getPool(options)
    val cfg = CfgModule.getCfg(options)
    val idg = if (this.shouldBuildIdg) Some(IdgModule.getIdg(options)) else None
    val dfg = if (this.shouldBuildDfg) Some(DfgModule.getDfg(options)) else None
    val cdg = if (this.shouldBuildCdg) Some(CdgModule.getCdg(options)) else None
    val rda = if (this.shouldBuildRda) Some(RdaModule.getRda(options)) else None
    val ddg = if (this.shouldBuildDdg) Some(DdgModule.getDdg(options)) else None
    val pdg = if (this.shouldBuildPdg) Some(PdgModule.getPdg(options)) else None
    (pst.procedureUri,
      AlirIntraProcedural.AlirIntraproceduralAnalysisResult(
        pool, cfg, idg, dfg, cdg, rda, ddg, pdg
      ))
  }

  val results : MMap[ResourceUri, AlirIntraProcedural.AlirIntraproceduralAnalysisResult] = mmapEmpty
  val itOpt = this.procedureAbsUriIterator
  if (itOpt.isEmpty) {
    val col : GenSeq[ProcedureSymbolTable] = if (par) psts.par else psts
    (col.map { pst =>
      compute(pst)
    }).foreach { p =>
      results(first2(p)) = second2(p)
    }
  } else {
    val it = itOpt.get
    while (it.hasNext)
      st.procedureSymbolTable(it.next)
  }

  this.result_=(results)

  def buildPipeline(job : PipelineJob) = {
    val stages = marrayEmpty[PipelineStage]

    if (this.shouldBuildCfg || this.shouldBuildIdg || this.shouldBuildDfg || this.shouldBuildCdg)
      stages += PipelineStage("CFG Building", false, CfgModule)

    if (this.shouldBuildIdg || this.shouldBuildDfg)
      stages += PipelineStage("IDG Building", false, IdgModule)

    if (this.shouldBuildDfg)
      stages += PipelineStage("DFG Building", false, DfgModule)

    if (this.shouldBuildCdg || this.shouldBuildPdg)
      stages += PipelineStage("CDG Building", false, CdgModule)

    if (this.shouldBuildRda || shouldBuildDdg)
      stages += PipelineStage("RDA Building", false, RdaModule)

    if (this.shouldBuildDdg || this.shouldBuildPdg)
      stages += PipelineStage("DDG Building", false, DdgModule)

    if (this.shouldBuildPdg)
      stages += PipelineStage("PDG Building", false, PdgModule)

    PipelineConfiguration("Alir Intraprocedurel Analysis Pipeline",
      false, stages : _*)
  }
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class CfgModuleDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends CfgModule {
  val ENTRY_NODE_LABEL = "Entry"
  val EXIT_NODE_LABEL = "Exit"
  val pl : AlirIntraProceduralGraph.NodePool = mmapEmpty
  val pst = this.procedureSymbolTable
  val siff = this.shouldIncludeFlowFunction
  this.pool_=(pl)
  val result = ControlFlowGraph[String](pst, ENTRY_NODE_LABEL, EXIT_NODE_LABEL, pl, siff)
  this.cfg_=(result)
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class IdgModuleDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends IdgModule {
  this.idg_=(ImmediateDominatorGraph[String](this.pool, this.cfg))
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class DfgModuleDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends DfgModule {
  this.dfg_=(DominanceFrontierGraph[String](this.pool, this.cfg, this.idg))
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class CdgModuleDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends CdgModule {
  this.cdg_=(ControlDependenceGraph[String](this.pool, this.cfg, "<r>"))
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class RdaModuleDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends RdaModule {
  val pst = this.procedureSymbolTable
  val iiopp = this.isInputOutputParamPredicate(pst)
  this.rda_=(ReachingDefinitionAnalysis[String](pst,
    this.cfg,
    this.defRef(pst.symbolTable),
    first2(iiopp),
    this.switchAsOrderedMatch))
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class DdgModuleDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends DdgModule {
  val pst = this.procedureSymbolTable
  val iiopp = this.isInputOutputParamPredicate(pst)
  val result = DataDependenceGraph[String](this.pool, this.cfg, this.rda,
    this.defRef(pst.symbolTable), iiopp, 
    new DdgUtil(this.rda,this.cfg,this.defRef(pst.symbolTable),iiopp),None)
  this.ddg_=(result)
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class PdgModuleDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends PdgModule {
  val pst = this.procedureSymbolTable
  val lp = new ProgramDependenceGraph.LabelProvider[String] {
      def inLabel(slot : Slot) : String = slot + ".in"
      def outLabel(slot : Slot) : String = slot + ".out"
      def paramLabel(dd : ParamDefDesc) : String = dd + ".arg" + dd.paramIndex
      def resultLabel(dd : LocDefDesc) : String = dd + ".result"
      def useLabel(slot : Slot, dd : UseDefDesc) : String = slot.toString + "#" + dd
      def effectLabel(slot : Slot, dd : EffectDefDesc) : String = slot.toString + "#" + dd
    }
  this.pdg = ProgramDependenceGraph[String](
    pst, this.pool,
    this.cfg, this.cdg, this.ddg,lp,new PdgUtil(pst,cfg,ddg,lp), None)

}
