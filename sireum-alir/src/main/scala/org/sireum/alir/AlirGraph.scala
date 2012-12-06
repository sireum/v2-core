/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
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
  
  def addNode(node : Node) : Node

  def addEdge(e : Edge) = graph.addEdge(e.source, e.target, e)

  def hasEdge(n1 : Node, n2 : Node) : Boolean = graph.containsEdge(n1, n2)

  def getEdge(n1 : Node, n2 : Node) : Edge = graph.getEdge(n1, n2)

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
final case class AlirEdge[Node](owner : AlirGraph[Node],
                                source : Node, target : Node)
    extends PropertyProvider {
  protected lazy val propertyMap = mmapEmpty[Property.Key, Any]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait AlirEdgeAccesses[Node] {
  self : AlirGraph[Node] =>

  def addEdge(source : Node, target : Node) : Edge =
    graph.addEdge(getNode(source), getNode(target))

  override def getEdge(source : Node, target : Node) : Edge =
    graph.getEdge(getNode(source), getNode(target))

  def getOrAddEdge(n1 : Node, n2 : Node) : Edge =
    if (hasEdge(n1, n2))
      getEdge(n1, n2)
    else {
      addNode(n1)
      addNode(n2)
      addEdge(n1, n2)
    }
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
