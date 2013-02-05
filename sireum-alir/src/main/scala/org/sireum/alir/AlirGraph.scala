/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.alir

import org.jgrapht.alg.StrongConnectivityInspector
import org.jgrapht._
import org.jgrapht.graph._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait AlirGraph[Node] {
  type Edge = AlirEdge[Node]

  protected def graph : DirectedGraph[Node, Edge]

  def nodes : Iterable[Node] = {
    import scala.collection.JavaConversions._

    graph.vertexSet
  }

  def numOfNodes : Int = graph.vertexSet.size

  def edges : Iterable[Edge] = {
    import scala.collection.JavaConversions._

    graph.edgeSet
  }

  def getEdges(n1 : Node, n2 : Node) : CSet[Edge] = {
    import scala.collection.JavaConversions._

    graph.getAllEdges(n1, n2)
  }

  def hasEdge(n1 : Node, n2 : Node) : Boolean = graph.containsEdge(n1, n2)

  def numOfEdges : Int = graph.edgeSet.size

  def hasNode(n : Node) : Boolean = graph.containsVertex(n)

  def getNode(n : Node) : Node

  def prePostNodeOrder(n : Node) =
    DirectedGraphUtil.computePrePostNodeOrder(graph, n)

  def stronglyConnectedSets : Iterable[CSet[Node]] = {
    import scala.collection.JavaConversions._

    val sci = new StrongConnectivityInspector[Node, Edge](graph)
    sci.stronglyConnectedSets.map { s => s : CSet[Node] }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class AlirEdge[Node](val owner : AlirGraph[Node],
                           val source : Node, val target : Node)
    extends PropertyProvider {
  val propertyMap = mlinkedMapEmpty[Property.Key, Any]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait AlirEdgeAccesses[Node] {
  self : AlirGraph[Node] =>

  def addNode(node : Node) : Node

  def addEdge(source : Node, target : Node) : Edge =
    graph.addEdge(getNode(source), getNode(target))

  def addEdge(e : Edge) = graph.addEdge(e.source, e.target, e)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait AlirSuccPredAccesses[Node] {
  self : AlirGraph[Node] =>

  def successors(node : Node) : CSet[Node] = {
    import scala.collection.JavaConversions._

    successorEdges(node).map(edgeTarget)
  }

  def successorEdges(node : Node) : CSet[Edge] = {
    import scala.collection.JavaConversions._

    if (graph.containsVertex(node))
      graph.outgoingEdgesOf(node)
    else
      Set()
  }

  def successor(edge : Edge) = edgeTarget(edge)

  def predecessor(edge : Edge) = edgeSource(edge)

  def predecessors(node : Node) : CSet[Node] = {
    import scala.collection.JavaConversions._

    predecessorEdges(node).map(edgeSource)
  }

  def predecessorEdges(node : Node) : CSet[Edge] = {
    import scala.collection.JavaConversions._

    if (graph.containsVertex(node))
      graph.incomingEdgesOf(node)
    else
      Set()
  }

  protected def edgeSource(edge : AlirEdge[Node]) = edge.source
  protected def edgeTarget(edge : AlirEdge[Node]) = edge.target
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait AlirDependentAccesses[Node] {
  self : AlirGraph[Node] =>

  def dependents(node : Node) : CSet[Node] = {
    import scala.collection.JavaConversions._

    dependentEdges(node).map(edgeTarget)
  }

  def dependentEdges(node : Node) : CSet[Edge] = {
    import scala.collection.JavaConversions._

    if (graph.containsVertex(node))
      graph.outgoingEdgesOf(node)
    else
      Set()
  }

  def dependent(edge : Edge) = {
    edgeTarget(edge)
  }

  def dependee(edge : Edge) = {
    edgeSource(edge)
  }

  def dependee(node : Node) : CSet[Node] = {
    import scala.collection.JavaConversions._

    dependeeEdges(node).map(edgeSource)
  }

  def dependeeEdges(node : Node) : CSet[Edge] = {
    import scala.collection.JavaConversions._

    if (graph.containsVertex(node))
      graph.incomingEdgesOf(node)
    else
      Set()
  }

  protected def edgeSource(edge : AlirEdge[Node]) = edge.source
  protected def edgeTarget(edge : AlirEdge[Node]) = edge.target
}
