/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.alir

import org.jgrapht.ext.VertexNameProvider
import org.jgrapht.ext.DOTExporter
import java.io.Writer
import org.sireum.pilar.ast.LocationDecl
import org.jgrapht._
import org.jgrapht.graph._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object AlirIntraProceduralGraph {
  type NodePool = MMap[AlirIntraProceduralNode, AlirIntraProceduralNode]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait AlirIntraProceduralGraph //
[Node <: AlirIntraProceduralNode, VirtualLabel]
    extends AlirGraph[Node] {
  self =>

  protected val graph = new DirectedMultigraph(
    new EdgeFactory[Node, Edge] {
      def createEdge(source : Node, target : Node) =
        AlirEdge(self, source, target).asInstanceOf[Edge]
    })

  protected def pool : MMap[AlirIntraProceduralNode, Node]

  def addNode(node : Node) : Node = {
    require(pool(node) eq node)
    graph.addVertex(node)
    node
  }

  def addNode(locUri : Option[ResourceUri], locIndex : Int) : Node = {
    val node = newNode(locUri, locIndex).asInstanceOf[Node]
    val n =
      if (pool.contains(node)) pool(node)
      else {
        pool(node) = node
        node
      }
    graph.addVertex(n)
    n
  }

  def addVirtualNode(vlabel : VirtualLabel) : Node = {
    val node = newVirtualNode(vlabel).asInstanceOf[Node]
    val n =
      if (pool.contains(node)) pool(node)
      else {
        pool(node) = node
        node
      }
    graph.addVertex(n)
    n
  }

  def getNode(n : Node) : Node =
    pool(n)

  def getNode(locUri : Option[ResourceUri], locIndex : Int) : Node =
    pool(newNode(locUri, locIndex))

  def getNode(l : LocationDecl) : Node =
    if (l.name.isEmpty)
      getNode(None, l.index)
    else
      getNode(Some(l.name.get.uri), l.index)

  def getVirtualNode(vlabel : VirtualLabel) : Node =
    pool(newVirtualNode(vlabel))

  def toDot(w : Writer) = {
    val de = new DOTExporter[Node, Edge](vlabelProvider, vlabelProvider, null)
    de.export(w, graph)
  }

  protected val vlabelProvider = new VertexNameProvider[Node]() {
    def getVertexName(v : Node) : String = {
      v match {
        case AlirLocationUriNode(locUri, locIndex) => UriUtil.lastPath(locUri)
        case AlirLocationIndexNode(locIndex)       => locIndex.toString
        case AlirVirtualNode(vlabel)               => vlabel.toString
      }
    }
  }

  protected def newNode(locUri : Option[ResourceUri], locIndex : Int) =
    if (locUri.isEmpty)
      AlirLocationIndexNode(locIndex)
    else
      AlirLocationUriNode(locUri.get, locIndex)

  protected def newVirtualNode(vlabel : VirtualLabel) =
    AlirVirtualNode(vlabel)

}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class AlirIntraProceduralNode extends PropertyProvider {
  val propertyMap = mlinkedMapEmpty[Property.Key, Any]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class AlirLocationNode extends AlirIntraProceduralNode {
  def locIndex : Int
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class AlirLocationUriNode(locUri : ResourceUri, locIndex : Int)
    extends AlirLocationNode {
  override def toString = locUri
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class AlirLocationIndexNode(locIndex : Int)
    extends AlirLocationNode {
  override def toString = locIndex.toString
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class AlirVirtualNode[VirtualLabel](label : VirtualLabel)
    extends AlirIntraProceduralNode {
  override def toString = label.toString
}
