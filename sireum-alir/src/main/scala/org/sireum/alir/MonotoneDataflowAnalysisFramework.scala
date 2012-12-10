/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
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
trait MonotoneDataFlowAnalysisResult[LatticeElement] {
  def entrySet : ControlFlowGraph.Node => ISet[LatticeElement]
  def entries(n : ControlFlowGraph.Node, esl : EntrySetListener[LatticeElement]) : Unit
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait MonotonicFunction[LatticeElement] {
  import org.sireum.pilar.ast._

  def apply(s : ISet[LatticeElement], a : Assignment) : ISet[LatticeElement]
  def apply(s : ISet[LatticeElement], e : Exp) : ISet[LatticeElement]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EntrySetListener[LatticeElement] {
  def action(a : Action, s : ISet[LatticeElement]) {}
  def gotoJump(j : GotoJump, s : ISet[LatticeElement]) {}
  def ifJump(j : IfJump, s : ISet[LatticeElement]) {}
  def ifThen(it : IfThenJump, s : ISet[LatticeElement]) {}
  def ifElse(gj : GotoJump, s : ISet[LatticeElement]) {}
  def switchJump(j : SwitchJump, s : ISet[LatticeElement]) {}
  def switchCase(sc : SwitchCaseJump, s : ISet[LatticeElement]) {}
  def switchDefault(sd : GotoJump, s : ISet[LatticeElement]) {}
  def returnJump(j : ReturnJump, s : ISet[LatticeElement]) {}
  def callJump(j : CallJump, s : ISet[LatticeElement]) {}
  def exitSet(b : Option[Branch], s : ISet[LatticeElement]) {}
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object MonotoneDataFlowAnalysisFramework {
  def apply[LatticeElement, VirtualLabel] = build[LatticeElement, VirtualLabel] _

  def build[LatticeElement, VirtualLabel] //
  (pst : ProcedureSymbolTable,
   cfg : ControlFlowGraph[VirtualLabel],
   forward : Boolean, lub : Boolean, rapid : Boolean,
   gen : MonotonicFunction[LatticeElement],
   kill : MonotonicFunction[LatticeElement],
   iota : ISet[LatticeElement],
   initial : ISet[LatticeElement],
   switchAsOrderedMatch : Boolean = false) : //
   MonotoneDataFlowAnalysisResult[LatticeElement] = {
    type N = ControlFlowGraph.Node
    val confluence = if (lub) iunion[LatticeElement] _ else iintersect[LatticeElement] _
    val bigConfluence : Iterable[ISet[LatticeElement]] => ISet[LatticeElement] =
      if (lub) bigIUnion else bigIIntersect

    val flow = if (forward) cfg else cfg.reverse
    val es = idmapEmpty[N, ISet[LatticeElement]]
    es(flow.entryNode) = iota
      def getEntrySet(n : N) = es.getOrElse(n, initial)

    class Mdaf(val entrySet : N => ISet[LatticeElement],
               initial : ISet[LatticeElement])
        extends MonotoneDataFlowAnalysisResult[LatticeElement] {
      type DFF = ISet[LatticeElement]

      override def toString = {
        val sb = new StringBuilder

        for (n <- cfg.nodes) {
          sb.append("%s = %s\n".format(n, entrySet(n).toString))
        }
        sb.append("\n")

        sb.toString
      }

      protected def next(l : LocationDecl) = cfg.getNode(pst.location(l.index + 1))

      protected def node(locUri : ResourceUri) = cfg.getNode(pst.location(locUri))

      protected def fA(a : Assignment, in : DFF) : DFF =
        kill(in, a).union(gen(in, a))

      protected def fE(e : Exp, in : DFF) : DFF =
        kill(in, e).union(gen(in, e))

      protected def fOE(eOpt : Option[Exp], in : DFF) : DFF =
        if (eOpt.isDefined) fE(eOpt.get, in) else in

      protected def actionF(in : DFF, a : Action) =
        a match {
          case a : AssignAction => fA(a, in)
          case a : AssertAction => fE(a.cond, in)
          case a : AssumeAction => fE(a.cond, in)
          case a : ThrowAction  => fE(a.exp, in)
          case a : StartAction =>
            if (forward)
              fOE(a.arg, fOE(a.count, in))
            else
              fOE(a.count, fOE(a.arg, in))
          case a : ExtCallAction => fA(a, in)
        }

      def update(s : DFF, n : N) : Boolean = {
        val oldS = getEntrySet(n)
        if (oldS != s) {
          es.update(n, s)
          true
        } else
          false
      }

      protected def visitBackward(
        l : LocationDecl,
        esl : Option[EntrySetListener[LatticeElement]]) : Boolean = {
        val eslb = esl.getOrElse(null)
          def jumpF(j : Jump) : DFF =
            j match {
              case j : IfJump =>
                var result = initial
                val numOfIfThens = j.ifThens.size
                for (i <- 0 until numOfIfThens) {
                  val ifThen = j.ifThens(i)
                  val sn = node(ifThen.target.uri)
                  var r = getEntrySet(sn)
                  for (k <- tozero(i)) {
                    val it = j.ifThens(k)
                    r = fE(it.cond, r)
                  }
                  result = confluence(result, r)
                }
                {
                  val ifElse = j.ifElse
                  val ifElseDefined = ifElse.isDefined
                  val sn =
                    if (ifElseDefined) node(ifElse.get.target.uri)
                    else next(l)
                  var r = getEntrySet(sn)
                  for (k <- tozero(numOfIfThens - 1)) {
                    val it = j.ifThens(k)
                    r = fE(it.cond, r)
                  }
                  if (ifElseDefined && esl.isDefined) eslb.ifElse(ifElse.get, r)
                  result = confluence(result, r)
                }
                if (esl.isDefined) eslb.ifJump(j, result)
                result
              case j : SwitchJump =>
                var result = initial
                val numOfCases = j.cases.size
                for (i <- 0 until numOfCases) {
                  val switchCase = j.cases(i)
                  val sn = node(switchCase.target.uri)
                  var r = getEntrySet(sn)
                  if (switchAsOrderedMatch)
                    for (k <- tozero(i)) {
                      val sc = j.cases(k)
                      r = fE(sc.cond, r)
                    }
                  else
                    r = fE(switchCase.cond, r)
                  if (esl.isDefined) eslb.switchCase(switchCase, r)
                  result = confluence(result, r)
                }
                {
                  val switchDefault = j.defaultCase
                  val switchDefaultDefined = switchDefault.isDefined
                  val sn =
                    if (switchDefaultDefined)
                      node(switchDefault.get.target.uri)
                    else next(l)
                  var r = getEntrySet(sn)
                  if (switchAsOrderedMatch)
                    for (k <- tozero(numOfCases - 1)) {
                      val sc = j.cases(k)
                      r = fE(sc.cond, r)
                    }
                  if (esl.isDefined && switchDefaultDefined)
                    eslb.switchDefault(switchDefault.get, r)
                  result = confluence(result, r)
                }
                if (esl.isDefined)
                  eslb.switchJump(j, result)
                result
              case j : GotoJump =>
                val sn = node(j.target.uri)
                val result = getEntrySet(sn)
                if (esl.isDefined)
                  eslb.gotoJump(j, result)
                result
              case j : ReturnJump =>
                val result = fOE(j.exp, getEntrySet(cfg.exitNode))
                if (esl.isDefined)
                  eslb.returnJump(j, result)
                result
              case j : CallJump =>
                val s =
                  if (j.jump.isEmpty)
                    getEntrySet(next(l))
                  else
                    jumpF(j.jump.get)
                val result = fA(j, s)
                if (esl.isDefined)
                  eslb.callJump(j, result)
                result
            }
        val ln = cfg.getNode(l)
        l match {
          case l : ComplexLocation =>
            val result = bigConfluence(l.transformations.map { t =>
              var r =
                if (t.jump.isEmpty)
                  getEntrySet(next(l))
                else
                  jumpF(t.jump.get)
              val numOfActions = t.actions.size
              for (i <- untilzero(numOfActions)) {
                val a = t.actions(i)
                r = actionF(r, a)
                if (esl.isDefined) eslb.action(a, r)
              }
              if (esl.isDefined) eslb.exitSet(None, r)
              r
            })
            update(result, ln)
          case l : ActionLocation =>
            val result = actionF(getEntrySet(next(l)), l.action)
            if (esl.isDefined) {
              eslb.action(l.action, result)
              eslb.exitSet(None, result)
            }
            update(result, ln)
          case l : JumpLocation =>
            val result = jumpF(l.jump)
            if (esl.isDefined) {
              eslb.exitSet(None, result)
            }
            update(result, ln)
          case l : EmptyLocation =>
            false
        }
      }

      protected def visitForward(
        l : LocationDecl,
        esl : Option[EntrySetListener[LatticeElement]]) : Boolean = {
        val eslb = esl.getOrElse(null)
          def jumpF(s : DFF, j : Jump) : Boolean =
            j match {
              case j : IfJump =>
                var r = s
                if (esl.isDefined) eslb.ifJump(j, s)
                var updated = false
                for (ifThen <- j.ifThens) {
                  r = fE(ifThen.cond, r)
                  val sn = node(ifThen.target.uri)
                  if (esl.isDefined) {
                    eslb.ifThen(ifThen, r)
                    eslb.exitSet(Some(ifThen), r)
                  }
                  updated = update(confluence(r, getEntrySet(sn)), sn) || updated
                }
                if (j.ifElse.isEmpty) {
                  val sn = next(l)
                  if (esl.isDefined) eslb.exitSet(None, r)
                  update(confluence(r, getEntrySet(sn)), sn) || updated
                } else {
                  val ifElse = j.ifElse.get
                  val sn = node(ifElse.target.uri)
                  if (esl.isDefined) {
                    eslb.ifElse(ifElse, r)
                    eslb.exitSet(Some(ifElse), r)
                  }
                  update(confluence(r, getEntrySet(sn)), sn) || updated
                }
              case j : SwitchJump =>
                var r = s
                if (esl.isDefined) eslb.switchJump(j, s)
                var updated = false
                for (switchCase <- j.cases) {
                  r =
                    if (switchAsOrderedMatch)
                      fE(switchCase.cond, r)
                    else
                      fE(switchCase.cond, s)
                  val sn = node(switchCase.target.uri)
                  if (esl.isDefined) {
                    eslb.switchCase(switchCase, r)
                    eslb.exitSet(Some(switchCase), r)
                  }
                  updated = update(confluence(r, getEntrySet(sn)), sn) || updated
                }
                if (j.defaultCase.isEmpty) {
                  val sn = next(l)
                  if (esl.isDefined) eslb.exitSet(None, r)
                  update(confluence(r, getEntrySet(sn)), sn) || updated
                } else {
                  val switchDefault = j.defaultCase.get
                  val sn = node(switchDefault.target.uri)
                  if (esl.isDefined) {
                    eslb.switchDefault(switchDefault, r)
                    eslb.exitSet(Some(switchDefault), r)
                  }
                  update(confluence(r, getEntrySet(sn)), sn) || updated
                }
              case j : GotoJump =>
                val sn = node(j.target.uri)
                if (esl.isDefined) {
                  eslb.gotoJump(j, s)
                  eslb.exitSet(Some(j), s)
                }
                update(confluence(s, getEntrySet(sn)), sn)
              case j : ReturnJump =>
                val sn = cfg.exitNode
                val r = if (j.exp.isEmpty) s else fE(j.exp.get, s)
                if (esl.isDefined) {
                  eslb.returnJump(j, r)
                  eslb.exitSet(Some(j), r)
                }
                update(confluence(r, getEntrySet(sn)), sn)
              case j : CallJump =>
                if (esl.isDefined) eslb.callJump(j, s)
                if (j.jump.isEmpty) {
                  val sn = next(l)
                  val r = fA(j, s)
                  if (esl.isDefined) eslb.exitSet(None, r)
                  update(confluence(r, getEntrySet(sn)), sn)
                } else
                  jumpF(fA(j, s), j.jump.get)
            }

        val ln = cfg.getNode(l)
        var s = getEntrySet(ln)
        l match {
          case l : ComplexLocation =>
            val bs = l.transformations.map { t =>
              var r = s
              t.actions.foreach { a =>
                if (esl.isDefined) eslb.action(a, r)
                r = actionF(r, a)
              }
              if (t.jump.isDefined)
                jumpF(r, t.jump.get)
              else {
                val sn = next(l)
                if (esl.isDefined) eslb.exitSet(None, r)
                update(confluence(r, getEntrySet(sn)), sn)
              }
            }
            bs.exists(_ == true)
          case l : ActionLocation =>
            val sn = next(l)
            if (esl.isDefined) eslb.action(l.action, s)
            val r = actionF(s, l.action)
            if (esl.isDefined) eslb.exitSet(None, r)
            update(confluence(r, getEntrySet(sn)), sn)
          case l : JumpLocation =>
            jumpF(s, l.jump)
          case l : EmptyLocation =>
            if (esl.isDefined) eslb.exitSet(None, s)
            false
        }
      }

      def visit(l : LocationDecl,
                esl : Option[EntrySetListener[LatticeElement]] = None) : Boolean =
        if (forward) visitForward(l, esl)
        else visitBackward(l, esl)

      def entries(n : N, esl : EntrySetListener[LatticeElement]) = {
        n match {
          case n : AlirLocationNode =>
            visit(pst.location(n.locIndex), Some(esl))
          case _ =>
        }
      }
    }

    val mdaf = new Mdaf(getEntrySet _, initial)

    val workList = mlistEmpty[N]

    workList += flow.entryNode
    while (!workList.isEmpty) {
      val n = workList.remove(0)
      n match {
        case n : AlirLocationNode =>
          if (mdaf.visit(pst.location(n.locIndex)))
            workList ++= cfg.successors(n)
        case n =>
          for (succ <- cfg.successors(n)) {
            mdaf.update(getEntrySet(n), succ)
            workList += succ
          }
      }
    }

    mdaf
  }
}