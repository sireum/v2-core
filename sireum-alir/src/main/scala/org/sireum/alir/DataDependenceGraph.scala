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

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait HasDataDependenceInfo {
  val DEPENDENCE_INFO_PROPERTY_KEY = "Dependence Info"
  val LOC_DEPENDENCE_INFO_PROPERTY_KEY = "Location Dependence Info"

  def getDependenceInfo : DataDependenceGraph.Edge => Iterable[(Slot, DefDesc, DefDesc)]

  def getLocDependenceInfo : DataDependenceGraph.Edge => Iterable[(Slot, DefDesc, DefDesc)]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait HasDataDependenceInfoProducer extends HasDataDependenceInfo {
  def getDependenceInfo = _getDependenceInfo _

  def getLocDependenceInfo = _getLocDependenceInfo _

  def _getDependenceInfo(e : DataDependenceGraph.Edge) : MBuffer[(Slot, DefDesc, DefDesc)] =
    e.getPropertyOrElseUpdate(DEPENDENCE_INFO_PROPERTY_KEY, mlistEmpty)

  def _getLocDependenceInfo(e : DataDependenceGraph.Edge) : MBuffer[(Slot, DefDesc, DefDesc)] =
    e.getPropertyOrElseUpdate(LOC_DEPENDENCE_INFO_PROPERTY_KEY, mlistEmpty)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait DataDependenceGraph[VirtualLabel]
  extends AlirIntraProceduralGraph[DataDependenceGraph.Node, VirtualLabel]
  with AlirDependentAccesses[DataDependenceGraph.Node]
  with HasDataDependenceInfo

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object DataDependenceGraph {
  type Node = AlirIntraProceduralNode
  type Edge = AlirEdge[Node]
  type DD = DefDesc
  type RDF = ReachingDefinitionAnalysis.RDFact

  def apply[VirtualLabel] = build[VirtualLabel] _

  def build[VirtualLabel](pool : AlirIntraProceduralGraph.NodePool,
                          cfg : ControlFlowGraph[VirtualLabel],
                          rda : ReachingDefinitionAnalysis.Result,
                          defRef : DefRef,
                          isOutputParam : ResourceUri => Boolean) //
                          : DataDependenceGraph[VirtualLabel] = {
    type LDD = LocDefDesc

    val result = new Ddg[VirtualLabel](pool)

      def addEdgeToExit(n : Node, slot : Slot, dd : DD) {
        result.addNode(n)
        result.addNode(cfg.exitNode)
        val e = result.addEdge(n, cfg.exitNode)
        result._getDependenceInfo(e) +=
          ((slot, dd, ExitDefDesc))
      }

    val esl = new EntrySetListener[RDF] {

      override def action(a : Action, s : ISet[RDF]) {
        addEdges(DefDesc.desc(a), defRef.references(a), s)
      }

      def jump(j : Jump, s : ISet[RDF]) {
        addEdges(DefDesc.desc(j), defRef.references(j), s)
      }

      def addEdges(dd : DD, references : Iterable[Slot], s : ISet[RDF]) {
        val nLDD = dd.asInstanceOf[LDD]
        val n = cfg.getNode(nLDD.locUri, nLDD.locIndex)
        for (slot <- references)
          for (rdf <- s.filter { first2(_) == slot }) {
            val mDD = second2(rdf)
            val m = DefDesc.getNode(cfg, mDD)
            result.addNode(m)
            result.addNode(n)
            val e = result.addEdge(m, n)
            if (n == m && nLDD != mDD)
              result._getLocDependenceInfo(e) += ((slot, mDD, nLDD))
            else
              result._getDependenceInfo(e) += ((slot, mDD, nLDD))
          }
      }

      override def returnJump(j : ReturnJump, s : ISet[RDF]) {
        if (j.exp.isDefined) {
          val nLDD = DefDesc.desc(j)
          val rLDD = ReturnDefDesc(nLDD.locUri, nLDD.locIndex,
            nLDD.transIndex, nLDD.commandIndex)
          addEdges(rLDD, defRef.references(j), s)
          val r = cfg.getNode(rLDD.locUri, nLDD.locIndex)
          addEdgeToExit(r, ReturnSlot, rLDD)
        }
      }

      override def callJump(j : CallJump, s : ISet[RDF]) {
        val nLDD = DefDesc.desc(j)
        val callRefs = defRef.callReferences(j)
        val size = callRefs.size
        for (i <- 0 until size) {
          val cLDD = ParamDefDesc(nLDD.locUri, nLDD.locIndex,
            nLDD.transIndex, nLDD.commandIndex, i)
          addEdges(cLDD, callRefs(i), s)
        }
        addEdges(UseDefDesc(nLDD.locUri, nLDD.locIndex,
          nLDD.transIndex, nLDD.commandIndex), defRef.references(j), s)
      }

      override def gotoJump(j : GotoJump, s : ISet[RDF]) { jump(j, s) }
      override def ifJump(j : IfJump, s : ISet[RDF]) { jump(j, s) }
      override def switchJump(j : SwitchJump, s : ISet[RDF]) { jump(j, s) }
    }

    for (n <- cfg.nodes) {
      rda.entries(n, esl)
    }

    for ((slot @ VarSlot(varUri), dd) <- rda.entrySet(cfg.exitNode)) {
      if (isOutputParam(varUri) || H.isGlobalVar(varUri)) {
        val m = DefDesc.getNode(cfg, dd)
        addEdgeToExit(m, slot, dd)
      }
    }

    //print(result)

    result
  }

  private class Ddg[VirtualLabel](val pool : AlirIntraProceduralGraph.NodePool)
      extends DataDependenceGraph[VirtualLabel]
      with AlirEdgeAccesses[DataDependenceGraph.Node]
      with HasDataDependenceInfoProducer {

    override def toString = {
      val sb = new StringBuilder("DDG\n")

      for (n <- nodes)
        for (m <- dependents(n)) {
          for (e <- getEdges(n, m)) {
            sb.append("%s -> %s [%s, %s]\n".format(
              n, m, getDependenceInfo(e), getLocDependenceInfo(e)))
          }
        }

      sb.append("\n")

      sb.toString
    }
  }
}