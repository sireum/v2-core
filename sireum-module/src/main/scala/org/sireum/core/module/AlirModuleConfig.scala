/*
Copyright (c) 2011-2013 Jason Belt, Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.core.module

import org.sireum.alir._
import org.sireum.pilar.ast._
import org.sireum.pilar.symbol._
import org.sireum.pipeline.gen.ModuleGenerator
import org.sireum.util._
import org.sireum.pipeline._

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object AlirIntraProcedural {
  type VirtualLabel = String
  type CFG = ControlFlowGraph[VirtualLabel]
  type IDG = ImmediateDominatorGraph[VirtualLabel]
  type DFG = DominanceFrontierGraph[VirtualLabel]
  type CDG = ControlDependenceGraph[VirtualLabel]
  type RDA = ReachingDefinitionAnalysis.Result
  type DDG = DataDependenceGraph[VirtualLabel]
  type PDG = ProgramDependenceGraph[VirtualLabel]

  final case class AlirIntraproceduralAnalysisResult(
    pool : AlirIntraProceduralGraph.NodePool,
    cfg : AlirIntraProcedural.CFG,
    idgOpt : Option[AlirIntraProcedural.IDG],
    dfgOpt : Option[AlirIntraProcedural.DFG],
    cdgOpt : Option[AlirIntraProcedural.CDG],
    rdaOpt : Option[AlirIntraProcedural.RDA],
    ddgOpt : Option[AlirIntraProcedural.DDG],
    pdgOpt : Option[AlirIntraProcedural.PDG])

  // helper type matching ControlFlowGraph.ShouldIncludeFlowFunction except 
  // scala.Boolean is replaced with java.lang.Boolean. Note that the 
  // ModuleGenerator automatically substitutes java.lang.Boolean with 
  // scala.Boolean
  type ShouldIncludeFlowFunction = (LocationDecl, Iterable[CatchClause]) => (Iterable[CatchClause], java.lang.Boolean)
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class AlirIntraProcedural(
  title : String = "Alir Intraprocedural Module",

  @Input parallel : Boolean,

  @Input symbolTable : SymbolTable,

  @Input shouldBuildCfg : Boolean,

  @Input shouldBuildIdg : Boolean,

  @Input shouldBuildDfg : Boolean,

  @Input shouldBuildCdg : Boolean,

  @Input shouldBuildRda : Boolean,

  @Input shouldBuildDdg : Boolean,

  @Input shouldBuildPdg : Boolean,

  @Input shouldIncludeFlowFunction : AlirIntraProcedural.ShouldIncludeFlowFunction = // ControlFlowGraph.defaultSiff,
  { (_, _) => (Array.empty[CatchClause], false) },

  @Input defRef : SymbolTable => DefRef = { st => new org.sireum.alir.BasicVarDefRef(st, new org.sireum.alir.BasicVarAccesses(st)) },

  @Input isInputOutputParamPredicate : ProcedureSymbolTable => (ResourceUri => java.lang.Boolean, ResourceUri => java.lang.Boolean) = { pst =>
    val params = pst.params.toSet[ResourceUri]
    ({ localUri => params.contains(localUri) },
      { s => falsePredicate1[ResourceUri](s) })
  },

  @Input switchAsOrderedMatch : Boolean = true,

  @Input procedureAbsUriIterator : scala.Option[Iterator[ResourceUri]] = None,

  @Output result : MMap[ResourceUri, AlirIntraProcedural.AlirIntraproceduralAnalysisResult])

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Cfg(
  title : String = "Control Flow Graph Builder",

  @Input shouldIncludeFlowFunction : AlirIntraProcedural.ShouldIncludeFlowFunction,

  @Input procedureSymbolTable : ProcedureSymbolTable,

  @Output @Produce pool : AlirIntraProceduralGraph.NodePool,

  @Output @Produce cfg : ControlFlowGraph[String])

case class Idg(
  title : String = "Immediate Dominator Graph Builder",

  @Input @Consume(Array(classOf[Cfg])) pool : AlirIntraProceduralGraph.NodePool,

  @Input @Consume(Array(classOf[Cfg])) cfg : ControlFlowGraph[String],

  @Output @Produce idg : ImmediateDominatorGraph[String])

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Dfg(
  title : String = "Dominance Frontier Graph Builder",

  @Input @Consume(Array(classOf[Cfg])) pool : AlirIntraProceduralGraph.NodePool,

  @Input @Consume(Array(classOf[Cfg])) cfg : ControlFlowGraph[String],

  @Input @Consume(Array(classOf[Idg])) idg : ImmediateDominatorGraph[String],

  @Output @Produce dfg : DominanceFrontierGraph[String])

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Cdg(
  title : String = "Control Dependence Graph Builder",

  @Input @Consume(Array(classOf[Cfg])) pool : AlirIntraProceduralGraph.NodePool,

  @Input @Consume(Array(classOf[Cfg])) cfg : ControlFlowGraph[String],

  @Output @Produce cdg : ControlDependenceGraph[String])

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Rda(
  title : String = "Reaching Definition Analysis Builder",

  @Input procedureSymbolTable : ProcedureSymbolTable,

  @Input @Consume(Array(classOf[Cfg])) cfg : ControlFlowGraph[String],

  @Input defRef : SymbolTable => DefRef = { st => new org.sireum.alir.BasicVarDefRef(st, new org.sireum.alir.BasicVarAccesses(st)) },

  @Input isInputOutputParamPredicate : ProcedureSymbolTable => (ResourceUri => java.lang.Boolean, ResourceUri => java.lang.Boolean),

  @Input switchAsOrderedMatch : Boolean = false,

  @Output @Produce rda : ReachingDefinitionAnalysis.Result)

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Ddg(
  title : String = "Data Dependence Graph Builder",

  @Input procedureSymbolTable : ProcedureSymbolTable,

  @Input @Consume(Array(classOf[Cfg])) pool : AlirIntraProceduralGraph.NodePool,

  @Input @Consume(Array(classOf[Cfg])) cfg : ControlFlowGraph[String],

  @Input @Consume(Array(classOf[Rda])) rda : ReachingDefinitionAnalysis.Result,

  @Input defRef : SymbolTable => DefRef = { st => new org.sireum.alir.BasicVarDefRef(st, new org.sireum.alir.BasicVarAccesses(st)) },

  @Input isInputOutputParamPredicate : ProcedureSymbolTable => (ResourceUri => java.lang.Boolean, ResourceUri => java.lang.Boolean),

  @Output @Produce ddg : DataDependenceGraph[String])

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Pdg(
  title : String = "Program Dependence Graph Builder",

  @Input @Consume(Array(classOf[Cfg])) pool : AlirIntraProceduralGraph.NodePool,

  @Input @Consume(Array(classOf[Cfg])) cfg : ControlFlowGraph[String],

  @Input @Consume(Array(classOf[Cdg])) cdg : ControlDependenceGraph[String],

  @Input @Consume(Array(classOf[Ddg])) ddg : DataDependenceGraph[String],

  @Input procedureSymbolTable : ProcedureSymbolTable,

  @Output pdg : ProgramDependenceGraph[String])

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
object AlirModuleConfig {
  def main(args : Array[String]) {
    import org.sireum.option.PipelineMode
    val opt = PipelineMode()
    opt.classNames = Array(AlirIntraProcedural.getClass.getName.dropRight(1),
      Idg.getClass.getName.dropRight(1),
      Dfg.getClass.getName.dropRight(1),
      Pdg.getClass.getName.dropRight(1))
    opt.dir = "./src/main/scala/org/sireum/core/module"
    opt.genClassName = "AlirModulesCore"

    ModuleGenerator.run(opt)
  }
}