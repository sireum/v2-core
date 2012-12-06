/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.alir

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ImmediateDominatorGraph[VirtualLabel]
    extends AlirIntraProceduralGraph[ImmediateDominatorGraph.Node, VirtualLabel] {

  def hasImmediateDominator(n : ImmediateDominatorGraph.Node) =
    graph.containsVertex(n) && (graph.outDegreeOf(n) > 0)

  def immediateDominator(n : ImmediateDominatorGraph.Node) : Option[ImmediateDominatorGraph.Node] = {
    if (!hasImmediateDominator(n)) return None
    val es = graph.outgoingEdgesOf(n)
    assert(es.size == 1)
    val e = es.iterator.next()
    Some(e.target)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object ImmediateDominatorGraph {
  type Node = AlirIntraProceduralNode
  type Edge = AlirEdge[Node]

  def apply[VirtualLabel] = build[VirtualLabel] _

  def build[VirtualLabel](pool : AlirIntraProceduralGraph.NodePool,
                          cfg : ControlFlowGraph[VirtualLabel]) //
                          : ImmediateDominatorGraph[VirtualLabel] = {
    val result = new Idg[VirtualLabel](pool)

    val prePostNodeOrder = cfg.prePostNodeOrder(cfg.entryNode)
    val postOrderedNodes = DirectedGraphUtil.postOrderedNodes(prePostNodeOrder)
    result.addNode(cfg.entryNode)
    result.addEdge(cfg.entryNode, cfg.entryNode)
    var changed = true
    val count = postOrderedNodes.length
    while (changed) {
      changed = false
      for (i <- (count - 2) to 0 by -1) { // not including first
        val n = postOrderedNodes(i)
        var newIdom : Node = null
        val es = cfg.predecessorEdges(n)
        for (e <- es if newIdom == null) {
          val p = e.source
          if (result.hasImmediateDominator(p)) {
            newIdom = p
          }
        }
        assert(newIdom != null)
        for (e <- es) {
          val p = e.source
          if (result.hasImmediateDominator(p)) {
            newIdom = intersect(result, prePostNodeOrder, p, newIdom)
          }
        }
        val idom = result.immediateDominator(n)
        if (idom.isEmpty || idom.get != newIdom) {
          result.setImmediateDominator(n, newIdom)
          changed = true
        }
      }
    }
    result.graph.removeEdge(cfg.entryNode, cfg.entryNode)

    //print(result)

    result
  }

  private def intersect[VirtualLabel] //
  (idg : Idg[VirtualLabel], prePostNodeOrder : MMap[Node, (Int, Int)],
   n1 : Node, n2 : Node) = {
    var finger1 = n1
    var finger2 = n2
    while (finger1 != finger2) {
      var i1 = second2(prePostNodeOrder(finger1))
      var i2 = second2(prePostNodeOrder(finger2))
      while (i1 < i2) {
        finger1 = idg.immediateDominator(finger1).get
        i1 = second2(prePostNodeOrder(finger1))
      }
      while (i2 < i1) {
        finger2 = idg.immediateDominator(finger2).get
        i2 = second2(prePostNodeOrder(finger2))
      }
    }
    finger1
  }

  private class Idg[VirtualLabel](val pool : AlirIntraProceduralGraph.NodePool)
      extends ImmediateDominatorGraph[VirtualLabel]
      with AlirEdgeAccesses[ImmediateDominatorGraph.Node] {

    def setImmediateDominator(n1 : ImmediateDominatorGraph.Node,
                              n2 : ImmediateDominatorGraph.Node) {
      addNode(n1)
      addNode(n2)
      addEdge(n1, n2)
    }

    override def toString = {
      val sb = new StringBuilder("IDG\n")

      for (n <- nodes)
        if (hasImmediateDominator(n)) {
          val m = immediateDominator(n).get
          sb.append("%s -> %s\n".format(n, m))
        }

      sb.append("\n")

      sb.toString
    }
  }
}