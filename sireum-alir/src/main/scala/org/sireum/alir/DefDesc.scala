/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.alir

import org.sireum.pilar.ast._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object DefDesc {
  def desc(c : Command) = {
    val (locUri, locIndex, transIndex, commandIndex) = c.commandDescriptorInfo
    LLocDefDesc(locUri, locIndex, transIndex, commandIndex)
  }

  def getNode[VirtualLabel](cfg : ControlFlowGraph[VirtualLabel], dd : DefDesc) : ControlFlowGraph.Node =
    dd match {
      case ldd : LocDefDesc =>
        cfg.getNode(ldd.locUri, ldd.locIndex)
      case ExitDefDesc =>
        cfg.exitNode
      case _ =>
        cfg.entryNode
    }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class DefDesc {
  def isUndefined = this == UnDefDesc

  def isDefinedInitially = this == InitDefDesc
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case object UnDefDesc extends DefDesc {
  override def toString = "?"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case object InitDefDesc extends DefDesc {
  override def toString = "*"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case object ExitDefDesc extends DefDesc {
  override def toString = "$"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class LocDefDesc extends DefDesc {
  def locUri : Option[ResourceUri]
  def locIndex : Int
  def transIndex : Int
  def commandIndex : Int

  def hasSameDesc(that : DefDesc) =
    if (that.isInstanceOf[LocDefDesc]) {
      val t = that.asInstanceOf[LocDefDesc]
      locIndex == t.locIndex &&
        transIndex == t.transIndex &&
        commandIndex == t.commandIndex
    } else false

  override def toString = {
    val ln = if (locUri.isEmpty) locIndex.toString else locUri.get
    if (transIndex == 0)
      if (commandIndex == 0)
        ln
      else ln + "[" + commandIndex + "]"
    else ln + "[" + transIndex + ", " + commandIndex + "]"
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class LLocDefDesc(
  locUri : Option[ResourceUri],
  locIndex : Int,
  transIndex : Int,
  commandIndex : Int)
    extends LocDefDesc

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ReturnDefDesc(
  locUri : Option[ResourceUri],
  locIndex : Int,
  transIndex : Int,
  commandIndex : Int)
    extends LocDefDesc {

  override def toString = super.toString + ".return"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ParamDefDesc(
  locUri : Option[ResourceUri],
  locIndex : Int,
  transIndex : Int,
  commandIndex : Int,
  paramIndex : Int)
    extends LocDefDesc {

  override def toString = super.toString + "." + paramIndex
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class UseDefDesc(
  locUri : Option[ResourceUri],
  locIndex : Int,
  transIndex : Int,
  commandIndex : Int)
    extends LocDefDesc {

  override def toString = super.toString + ".use"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class EffectDefDesc(
  locUri : Option[ResourceUri],
  locIndex : Int,
  transIndex : Int,
  commandIndex : Int)
    extends LocDefDesc {

  override def toString = super.toString + ".effect"
}
