/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.alir

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ControlDependenceGraph[VirtualLabel]
    extends AlirIntraProceduralGraph[ControlDependenceGraph.Node, VirtualLabel]
    with AlirDependentAccesses[ControlDependenceGraph.Node]

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object ControlDependenceGraph {
  type Node = AlirIntraProceduralNode
  type Edge = AlirEdge[Node]

  def apply[VirtualLabel] = build[VirtualLabel] _

  def build[VirtualLabel](pool : AlirIntraProceduralGraph.NodePool,
                          cfg : ControlFlowGraph[VirtualLabel],
                          tempLabel : VirtualLabel) //
                          : ControlDependenceGraph[VirtualLabel] = {
    val result = new Cdg[VirtualLabel](pool)
    val rcfg = cfg.reverse

    val entryNode = rcfg.entryNode
    val exitNode = rcfg.exitNode

    val aea = rcfg.asInstanceOf[AlirEdgeAccesses[Node]]
    if (cfg.predecessors(cfg.exitNode).size == 0)
      // break non-terminating loops by adding an edge from a loop entry node
      // (whose precedessors are not in the strongly connected set) 
      // to the exit node
      for (s <- rcfg.stronglyConnectedSets)
        if (s.size != 1) {
          var found = false
          for (n <- s if !found)
            if (cfg.predecessors(n).exists { !s.contains(_) }) {
              aea.addEdge(entryNode, n)
              found = true
            }
        }

    val r = rcfg.addVirtualNode(tempLabel)
    aea.addEdge(entryNode, r)
    aea.addEdge(exitNode, r)
    val idg = ImmediateDominatorGraph[VirtualLabel](pool, rcfg)
    val dfg = DominanceFrontierGraph[VirtualLabel](pool, rcfg, idg)

    for (y <- rcfg.nodes) {
      for (x <- dfg.dominanceFrontiers(y)) {
        result.addNode(x)
        result.addNode(y)
        result.addEdge(x, y)
      }
    }
    result.graph.removeVertex(r)

    //print(result)

    result
  }

  private class Cdg[VirtualLabel](val pool : AlirIntraProceduralGraph.NodePool)
      extends ControlDependenceGraph[VirtualLabel]
      with AlirEdgeAccesses[ControlDependenceGraph.Node] {

    override def toString = {
      val sb = new StringBuilder("CDG\n")

      for (n <- nodes)
        for (m <- dependents(n))
          sb.append("%s -> %s\n".format(n, m))

      sb.append("\n")

      sb.toString
    }
  }
}