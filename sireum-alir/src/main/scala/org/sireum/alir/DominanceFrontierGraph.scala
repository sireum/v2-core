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
trait DominanceFrontierGraph[VirtualLabel]
    extends AlirIntraProceduralGraph[DominanceFrontierGraph.Node, VirtualLabel] {

  def dominanceFrontierEdges(n : DominanceFrontierGraph.Node) : Iterable[DominanceFrontierGraph.Edge] =
    if (!graph.containsVertex(n))
      Set()
    else {
      import scala.collection.JavaConversions._

      graph.outgoingEdgesOf(n)
    }

  def dominanceFrontiers(n : DominanceFrontierGraph.Node) : Iterable[DominanceFrontierGraph.Node] =
    dominanceFrontierEdges(n).map(edgeTarget)

  protected def edgeTarget(edge : AlirEdge[DominanceFrontierGraph.Node]) = edge.target
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object DominanceFrontierGraph {
  type Node = AlirIntraProceduralNode
  type Edge = AlirEdge[Node]

  def apply[VirtualLabel] = build[VirtualLabel] _

  def build[VirtualLabel](pool : AlirIntraProceduralGraph.NodePool,
                          cfg : ControlFlowGraph[VirtualLabel],
                          idg : ImmediateDominatorGraph[VirtualLabel]) //
                          : DominanceFrontierGraph[VirtualLabel] = {
    val result = new Dfg[VirtualLabel](pool)
    for (n <- cfg.nodes) {
      val preds = cfg.predecessors(n)
      if (preds.size >= 2)
        for (pred <- preds) {
          val idomN = idg.immediateDominator(n).get
          var runner = pred
          while (runner != idomN) {
            result.add(runner, n)
            runner = idg.immediateDominator(runner).get
          }
        }
    }

    //print(result)

    result
  }

  private class Dfg[VirtualLabel](val pool : AlirIntraProceduralGraph.NodePool)
      extends DominanceFrontierGraph[VirtualLabel]
      with AlirEdgeAccesses[DominanceFrontierGraph.Node] {
    def add(n1 : Node, n2 : Node) {
      addNode(n1)
      addNode(n2)
      addEdge(n1, n2)
    }

    override def toString = {
      val sb = new StringBuilder("DFG\n")

      for (n <- nodes)
        for (m <- dominanceFrontiers(n))
          sb.append("%s -> %s\n".format(n, m))

      sb.append("\n")

      sb.toString
    }
  }
}