/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.alir

import org.sireum.pilar.ast._
import org.sireum.pilar.symbol._
import org.sireum.util._
import java.io.Writer
import org.jgrapht.ext.DOTExporter
import org.jgrapht.ext.VertexNameProvider

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ProgramDependenceGraph[VirtualLabel]
  extends AlirIntraProceduralGraph[ProgramDependenceGraph.Node, VirtualLabel]
  with AlirDependentAccesses[ProgramDependenceGraph.Node]
  with HasDataDependenceInfo
  with AlirEdgeAccesses[ProgramDependenceGraph.Node]
  with HasDataDependenceInfoProducer

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object ProgramDependenceGraph {
  type Node = AlirIntraProceduralNode
  type Edge = AlirEdge[Node]

  trait LabelProvider[VirtualLabel] {
    def inLabel(slot: Slot): VirtualLabel
    def outLabel(slot: Slot): VirtualLabel
    def paramLabel(dd: ParamDefDesc): VirtualLabel
    def resultLabel(dd: LocDefDesc): VirtualLabel
    def useLabel(slot: Slot, dd: UseDefDesc): VirtualLabel
    def effectLabel(slot: Slot, dd: EffectDefDesc): VirtualLabel
  }

  trait PdgExtension[VirtualLabel] {
    def getCallEntryExitNodes: MIdSet[Node]
    def addInNodes(callOrEntryOrExitNodes: MIdSet[Node],
      result: PdgResult[VirtualLabel])
    def addOutNodes(callOrEntryOrExitNodes: MIdSet[Node],
      result: PdgResult[VirtualLabel])
    def addParamsGlobalsNode( callOrEntryOrExitNodes: MIdSet[Node],
    result : PdgResult[VirtualLabel])


    def getNode(slot: Slot, dd: DefDesc, result: PdgResult[VirtualLabel]): Node

    def addEdgeNT(from: Node, to: Node, t: (Slot, DefDesc, DefDesc), result: PdgResult[VirtualLabel]) {
      val e = result.addEdge(from, to)
      result._getDependenceInfo(e) += t
    }

    def addEdgeT(t: (Slot, DefDesc, DefDesc), result: PdgResult[VirtualLabel]) {
      val (slot, fromDD, toDD) = t
      val from = getNode(slot, fromDD, result)
      val to = getNode(slot, toDD, result)
      addEdgeNT(from, to, t, result)
    }
  }

  trait PdgResult[VirtualLabel] extends ProgramDependenceGraph[VirtualLabel]
    with AlirEdgeAccesses[ProgramDependenceGraph.Node] {
//    override def toDot(w: Writer) = {
//      val de = new DOTExporter[Node, Edge](labelprovider, labelprovider, null)
//      de.export(w, graph)
//    }
//    val labelprovider = new VertexNameProvider[Node]() {
//      def getVertexName(v: Node): String = {
//        v match {
//          case AlirLocationUriNode(locUri, locIndex) => { UriUtil.lastPath(locUri.split("_").head).replaceAll(".", "_").replaceAll("@@", "Global_") }
//          case AlirLocationIndexNode(locIndex) => locIndex.toString
//          case AlirVirtualNode(vlabel) => vlabel.toString.replace('.', '_').replace('@', 'A')
//        }
//      }
//    }

    override def toString = {
      val sb = new StringBuilder("PDG\n")

      for (n <- nodes)
        for (m <- dependents(n))
          for (e <- getEdges(n, m))
            sb.append("%s -> %s <%s>\n".format(n, m,
              toString(e)))
      sb.append("\n")

      sb.toString
    }

    def toString(e: Edge) =
      e.owner match {
        case ddg: DataDependenceGraph[VirtualLabel] =>
          "DDG[%s, %s]".format(getDependenceInfo(e),
            getLocDependenceInfo(e)).replaceAll("ListBuffer", "")
        case cdg: ControlDependenceGraph[VirtualLabel] =>
          "CDG"
        case pdg: ProgramDependenceGraph[VirtualLabel] =>
          "PDG[%s, %s]".format(getDependenceInfo(e),
            getLocDependenceInfo(e)).replaceAll("ListBuffer", "")
        case _ => "?"
      }
  }

  def apply[VirtualLabel] = build[VirtualLabel] _

  def build[VirtualLabel](pst: ProcedureSymbolTable,
    pool: AlirIntraProceduralGraph.NodePool,
    cfg: ControlFlowGraph[VirtualLabel],
    cdg: ControlDependenceGraph[VirtualLabel],
    ddg: DataDependenceGraph[VirtualLabel],
    lp: LabelProvider[VirtualLabel],
    pdgUtil: PdgExtension[VirtualLabel],
    tempRes: Option[PdgResult[VirtualLabel]] = None) //
    : ProgramDependenceGraph[VirtualLabel] = {

    var result: PdgResult[VirtualLabel] = null

    if (tempRes == None)
      result = new Pdg[VirtualLabel](pool)
    else
      result = tempRes.get

    //    val result = new Pdg[VirtualLabel](pool)

    for (n <- cdg.nodes) {
      result.addNode(n)
    }

    for (e <- cdg.edges) {
      result.addEdge(e)
    }

    val callOrEntryOrExitNodes = pdgUtil.getCallEntryExitNodes

    for (n <- ddg.nodes) {
      if (!callOrEntryOrExitNodes.contains(n))
        result.addNode(n)
    }

    for (e <- ddg.edges) {
      if (!callOrEntryOrExitNodes.contains(e.source) &&
        !callOrEntryOrExitNodes.contains(e.target))
        result.addEdge(e)
    }

    pdgUtil.addInNodes(callOrEntryOrExitNodes, result)
    pdgUtil.addOutNodes(callOrEntryOrExitNodes, result)

    callOrEntryOrExitNodes -= cfg.entryNode
    callOrEntryOrExitNodes -= cfg.exitNode
    pdgUtil.addParamsGlobalsNode(callOrEntryOrExitNodes, result)
    result
  }

  

  class PdgUtil[VirtualLabel](pst: ProcedureSymbolTable,
    cfg: ControlFlowGraph[VirtualLabel],
    ddg: DataDependenceGraph[VirtualLabel],
    lp: LabelProvider[VirtualLabel]) extends PdgExtension[VirtualLabel] {

    def getCallEntryExitNodes: MIdSet[Node] = {
      val callOrEntryOrExitNodes = idsetEmpty[Node]
      for (n <- cfg.nodes)
        n match {
          case n: AlirLocationNode =>
            if (PilarAstUtil.getJumps(pst.location(n.locIndex)).exists { j =>
              j.isDefined && j.get.isInstanceOf[CallJump]
            })
              callOrEntryOrExitNodes(n) = n
          case _ =>
        }

      callOrEntryOrExitNodes(cfg.entryNode) = cfg.entryNode
      callOrEntryOrExitNodes(cfg.exitNode) = cfg.exitNode
      callOrEntryOrExitNodes
    }

    def addInNodes(callOrEntryOrExitNodes: MIdSet[Node],
      result: PdgResult[VirtualLabel]) = {
      for (e <- ddg.dependentEdges(cfg.entryNode))
        for (t @ (slot, _, dd) <- ddg.getDependenceInfo(e)) {
          val n = result.addVirtualNode(lp.inLabel(slot))
          var m = getNode(slot, dd, result)
          if (!callOrEntryOrExitNodes.contains(m))
            addEdgeNT(n, m, t, result)
        }
    }

    def addOutNodes(callOrEntryOrExitNodes: MIdSet[Node],
      result: PdgResult[VirtualLabel]) = {
      for (e <- ddg.dependeeEdges(cfg.exitNode))
        for (t @ (slot, dd, _) <- ddg.getDependenceInfo(e)) {
          var n = DefDesc.getNode(cfg, dd)
          if (!callOrEntryOrExitNodes.contains(n)) {
            val m = result.addVirtualNode(lp.outLabel(slot))
            addEdgeNT(n, m, t, result)
          }
        }
    }

    def getNode(slot: Slot, dd: DefDesc, result: PdgResult[VirtualLabel]) = {
      val n = DefDesc.getNode(cfg, dd)
      if (n eq cfg.entryNode) result.addVirtualNode(lp.inLabel(slot))
      else if (n eq cfg.exitNode) result.addVirtualNode(lp.outLabel(slot))
      else n
    }
    
    def addParamsGlobalsNode( callOrEntryOrExitNodes: MIdSet[Node],result : PdgResult[VirtualLabel])= {
    
      for (n <- callOrEntryOrExitNodes.keys) {
      val jumps = PilarAstUtil.getJumps(pst.location(
        n.asInstanceOf[AlirLocationNode].locIndex))
      val jdds = jumps.map { j =>
        if (j.isDefined && j.get.isInstanceOf[CallJump])
          Set(DefDesc.desc(j.get).asInstanceOf[LocDefDesc])
        else Set[LocDefDesc]()
      }.fold(Set())(iunion[LocDefDesc])

      def involvesCall(dd: DefDesc) =
        jdds.exists { jdd => jdd.hasSameDesc(dd) }

      for (e <- ddg.dependeeEdges(n))
        for (t @ (slot, fromDD, toDD) <- ddg.getDependenceInfo(e))
          if (!involvesCall(toDD))
            addEdgeT(t, result)
          else if (toDD.isInstanceOf[ParamDefDesc]) {
            val from = getNode(slot, fromDD, result)
            val cDD = toDD.asInstanceOf[ParamDefDesc]
            val to = result.addVirtualNode(lp.paramLabel(cDD))
            addEdgeNT(from, to, t, result)
          } else {
            val from = getNode(slot, fromDD, result)
            val uDD = toDD.asInstanceOf[UseDefDesc]
            val to = result.addVirtualNode(lp.useLabel(slot, uDD))
            addEdgeNT(from, to, t, result)
          }

      for (e <- ddg.dependentEdges(n))
        for (t @ (slot, fromDD, toDD) <- ddg.getDependenceInfo(e))
          if (involvesCall(fromDD)) {
            val to = getNode(slot, toDD, result)
            if (fromDD.isInstanceOf[EffectDefDesc]) {
              val cDD = fromDD.asInstanceOf[EffectDefDesc]
              val from = result.addVirtualNode(lp.effectLabel(slot, cDD))
              addEdgeNT(from, to, t, result)
            } else {
              val fromLDD = fromDD.asInstanceOf[LocDefDesc]
              val r = result.addVirtualNode(lp.resultLabel(fromLDD))
              addEdgeNT(r, to, t, result)
            }
          } else
            addEdgeT(t, result)
    }

    //print(result)
    //print(result.toDot)
  }
    

  }

  private class Pdg[VirtualLabel](val pool: AlirIntraProceduralGraph.NodePool)
    extends PdgResult[VirtualLabel] {

    def toDot = {
      val sb = new StringBuilder("digraph PDG {\n")

      for (n <- nodes)
        for (m <- dependents(n))
          for (e <- getEdges(n, m))
            sb.append("  \"%s\" -> \"%s\" [label=\"%s\"]\n".format(n, m,
              toString(e)))

      sb.append("}\n\n")
      sb.toString
    }

  }
}