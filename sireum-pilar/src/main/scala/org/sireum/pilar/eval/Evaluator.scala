/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.eval

import org.sireum.pilar.ast._
import org.sireum.pilar.symbol._
import org.sireum.pilar.state._
import org.sireum.util._
import org.sireum.extension._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ExpEvaluator[S, R] {
  def evalExp : (S, Exp) --> R
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ActionEvaluator[S, SR] {
  def evalAction : (S, Action) --> SR
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait JumpEvaluator[S, SR] {
  def evalJump : (S, Jump) --> SR
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait TransformationEvaluator[S, SR] {
  def evalTransformation : (S, LocationDecl, Transformation) --> SR
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait LocationEvaluator[S, C] {
  def evalGuard : (S, LocationDecl, Transformation, Exp) --> C
  def transitions : (S, LocationDecl) --> Transitions[S]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Transitions[S] {
  def enabled : ISeq[(S, LocationDecl, Transformation)]
  def disabled : ISeq[(S, LocationDecl, Transformation)]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Evaluator[S, R, C, SR]
    extends ExpEvaluator[S, R]
    with ActionEvaluator[S, SR]
    with JumpEvaluator[S, SR]
    with TransformationEvaluator[S, SR]
    with LocationEvaluator[S, C] {
  def mainEvaluator : Evaluator[S, R, C, SR]
  def setMainEvaluator(eval : Evaluator[S, R, C, SR])
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolProvider[S] {
  def isVar(e : NameExp) = {
    import org.sireum.pilar.symbol.H
    val name = e.name
    name.hasResourceInfo && (H.isGlobalVar(name) || H.isLocalVar(name) || H.isAnonLocal(name))
  }
  def procedureUri(e : NameExp) =
    if (e.name.hasResourceInfo && H.isProcedure(e.name))
      Some(e.name.uri)
    else None
  def funUri(e : NameExp) =
    if (e.name.hasResourceInfo && H.isFun(e.name))
      Some(e.name.uri)
    else None
  def isFieldAccess(f : NameUser) : Boolean = {
    import H._
    f.hasResourceInfo && !(isConstElem(f) || isEnumElem(f))
  }
  def extUri(e : NameExp, extPresent : ResourceUri => Boolean) : Option[ResourceUri] = {
    val name = e.name
    if (name.hasResourceInfo && extPresent(name.uri))
      Some(name.uri)
    else if (extPresent(name.name))
      Some(name.name)
    else None
  }
  def initLocation(procUri : ResourceUri) : Option[ResourceUri]
  def location(s : S, nu : NameUser) : S
  def nextLocation(s : S) : S
  def initStore(s : S, procUri : ResourceUri, args : Value*) : State.Store
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait TypeProvider {
  def typeUri(symUri : ResourceUri) : ResourceUri
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ElseGuardExpander {
  def conjunct(e1 : Exp, e2 : Exp) : Exp
  def negate(e : Exp) : Exp
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EvaluatorConfig[S, V, R, C, SR] {
  def symbolProvider : SymbolProvider[S]
  def typeProvider : TypeProvider
  def elseGuardExpander : Option[ElseGuardExpander]
  def semanticExtensionConsumer(ev : Evaluator[S, R, C, SR]) : SemanticsExtensionConsumer[S, V, R, C, SR]

  def computeDisabledTransitions : Boolean
  def valueToV(v : Value) : V
  def vToValue(v : V) : Value
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EvaluatorModule[S, V, R, C, SR] {
  def initialize(ec : EvaluatorConfiguration[S, V, R, C, SR])
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EvaluatorConfiguration[S, V, R, C, SR] extends PropertyProvider {
  var symbolProvider : SymbolProvider[S]
  var typeProvider : TypeProvider
  var elseGuardExpander : Option[ElseGuardExpander]
  var computeDisabledTransitions : Boolean
  var extensions : ISeq[ExtensionCompanion]
  var semanticsExtension : SemanticsExtensionConsumer[S, V, R, C, SR]
  var evaluator : Evaluator[S, R, C, SR]

  def valueToV(v : Value) : V
  def vToValue(v : V) : Value
  def adapter[T] : T = {
    require(isInstanceOf[T])
    asInstanceOf[T]
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EvaluatorHeapConfiguration[S, V, R, C, SR] {
  self : EvaluatorConfiguration[S, V, R, C, SR] =>

  val HEAP_KEY = getClass.getName + ".numOfHeaps"

  import org.sireum.pilar.state.Heap._

  def heapId(heapIdKey : AnyRef) : HeapId =
    self.getPropertyOrElseUpdate(heapIdKey, {
      val r = numOfHeaps
      numOfHeaps = r + 1
      r
    })

  def numOfHeaps : Int =
    self.getPropertyOrElseUpdate(HEAP_KEY, 0)

  def numOfHeaps_=(n : Int) {
    self(HEAP_KEY) = n
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EvaluationContextProvider[C <: EvaluationContextProvider[C]] {
  type ContextStackElement = PilarAstNode
  protected[eval] def contextStack : IList[ContextStackElement]
  def make(newContextStack : IList[ContextStackElement]) : C

  def peekContext[T <: ContextStackElement](i : Int = 0) : T = contextStack(i).asInstanceOf[T]
  def pushContext[T <: ContextStackElement](c : T) = make(c :: contextStack)
  def popContext[T <: ContextStackElement] = make(contextStack.tail)

  @inline
  private def peek = peekContext(0)

  @inline
  private def peekAt(i : Int) = peekContext(i)

  @inline
  private def push = pushContext _

  @inline
  private def pop = popContext

  def pushExp(e : Exp) : C = push(e)
  def popExp : (C, Exp) = (pop, peek)

  def pushAction(a : Action) : C = push(a)
  def popAction : (C, Action) = (pop, peek)

  def pushJump(j : Jump) : C = push(j)
  def popJump : (C, Jump) = (pop, peek)

  def pushTransformation(l : LocationDecl, t : Transformation) : C = push(t).push(l)
  def popTransformation : (C, LocationDecl, Transformation) = {
    val t = peek
    val c1 = pop
    val l = c1.peek
    val c2 = c1.pop
    (c2, l, t)
  }

  def pushGuard(l : LocationDecl, t : Transformation, e : Exp) : C =
    push(l).push(t).push(e)
  def popGuard : (C, LocationDecl, Transformation, Exp) = {
    val e = peek
    val c1 = pop
    val t = c1.peek
    val c2 = c1.pop
    val l = c2.peek
    val c3 = c2.pop
    (c3, l, t, e)
  }

  def pushLocation(l : LocationDecl) : C = push(l)
  def popLocation : (C, LocationDecl) = (pop, peek)

  def peekKind : EvaluationContextKind =
    contextStack match {
      case e :: t :: l :: _ if //
      (e.isInstanceOf[Exp] && t.isInstanceOf[Transformation] &&
        l.isInstanceOf[LocationDecl]) => EvaluationContextKind.Guard
      case a :: t :: l :: _ if //
      (a.isInstanceOf[Action] && t.isInstanceOf[Transformation] &&
        l.isInstanceOf[LocationDecl]) => EvaluationContextKind.Action
      case j :: t :: l :: _ if //
      (j.isInstanceOf[Jump] && t.isInstanceOf[Transformation] &&
        l.isInstanceOf[LocationDecl]) => EvaluationContextKind.Jump
      case a :: l :: _ if //
      (a.isInstanceOf[Action] && l.isInstanceOf[ActionLocation]) =>
        EvaluationContextKind.ActionLocation
      case j :: l :: _ if //
      (j.isInstanceOf[Jump] && l.isInstanceOf[JumpLocation]) =>
        EvaluationContextKind.JumpLocation
      case e :: _ if e.isInstanceOf[Exp] => EvaluationContextKind.Exp
    }

  def peekExp : Exp = peek
  def peekAction : (LocationDecl, Transformation, Action) =
    (peekAt(2), peekAt(1), peek)
  def peekJump : (LocationDecl, Transformation, Jump) =
    (peekAt(2), peekAt(1), peek)
  def peekGuard : (LocationDecl, Transformation, Exp) =
    (peekAt(2), peekAt(1), peek)
  def peekActionLocation : (ActionLocation, Action) =
    (peekAt(1), peek)
  def peekJumpLocation : (JumpLocation, Jump) =
    (peekAt(1), peek)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Evaluator {
  def combine[S, R, C, SR](evs : Evaluator[S, R, C, SR]*) = {
    require(evs.length > 0)
    evs.length match {
      case 1 => evs(0)
      case _ =>
        new Evaluator[S, R, C, SR] {
          var ev : Evaluator[S, R, C, SR] = this
          def mainEvaluator = ev
          def setMainEvaluator(eval : Evaluator[S, R, C, SR]) {
            for (e <- evs) {
              e.setMainEvaluator(eval)
            }
            ev = eval
          }

          import PartialFunctionUtil._

          val evalExp : (S, Exp) --> R = orElses(evs.map { ev => ev.evalExp })
          val evalAction : (S, Action) --> SR = orElses(evs.map { ev => ev.evalAction })
          val evalJump : (S, Jump) --> SR = orElses(evs.map { ev => ev.evalJump })
          val evalTransformation : (S, LocationDecl, Transformation) --> SR = orElses(evs.map { ev => ev.evalTransformation })
          val evalGuard : (S, LocationDecl, Transformation, Exp) --> C = orElses(evs.map { ev => ev.evalGuard })
          val transitions : (S, LocationDecl) --> Transitions[S] = orElses(evs.map { ev => ev.transitions })
        }
    }
  }

  def context[S <: EvaluationContextProvider[S], V](
    ev : Evaluator[S, ISeq[(S, V)], ISeq[(S, Boolean)], ISeq[S]]) =
    new Evaluator[S, ISeq[(S, V)], ISeq[(S, Boolean)], ISeq[S]] {
      type R = ISeq[(S, V)]
      type C = ISeq[(S, Boolean)]
      type SR = ISeq[S]
      var ev : Evaluator[S, R, C, SR] = this
      def mainEvaluator = ev
      def setMainEvaluator(eval : Evaluator[S, R, C, SR]) {
        ev.setMainEvaluator(eval)
        ev = eval
      }

      import PartialFunctionUtil._

      val evalExp : (S, Exp) --> R =
        new PartialFunction[(S, Exp), R] {
          def isDefinedAt(se : (S, Exp)) = ev.evalExp.isDefinedAt(se)
          def apply(se : (S, Exp)) = {
            val (s, e) = se
            val s1 = s.pushExp(e)
            val r = ev.evalExp(s1, e)
            r.map { re =>
              val (s2, v) = re
              val (s3, e2) = s2.popExp
              assert(e2 eq e)
              (s3, v)
            }
          }
        }

      val evalAction : (S, Action) --> SR =
        new PartialFunction[(S, Action), SR] {
          def isDefinedAt(sa : (S, Action)) = ev.evalAction.isDefinedAt(sa)
          def apply(sa : (S, Action)) = {
            val (s, a) = sa
            val s1 = s.pushAction(a)
            val sr = ev.evalAction(s1, a)
            sr.map { s2 =>
              val (s3, a2) = s2.popAction
              assert(a2 eq a)
              s3
            }
          }
        }

      val evalJump : (S, Jump) --> SR =
        new PartialFunction[(S, Jump), SR] {
          def isDefinedAt(sj : (S, Jump)) = ev.evalJump.isDefinedAt(sj)
          def apply(sj : (S, Jump)) = {
            val (s, j) = sj
            val s1 = s.pushJump(j)
            val sr = ev.evalJump(s1, j)
            sr.map { s2 =>
              val (s3, j2) = s2.popJump
              assert(j2 eq j)
              s3
            }
          }
        }

      val evalTransformation : (S, LocationDecl, Transformation) --> SR =
        new PartialFunction[(S, LocationDecl, Transformation), SR] {
          def isDefinedAt(slt : (S, LocationDecl, Transformation)) =
            ev.evalTransformation.isDefinedAt(slt)
          def apply(slt : (S, LocationDecl, Transformation)) = {
            val (s, l, t) = slt
            val s1 = s.pushTransformation(l, t)
            val sr = ev.evalTransformation(s1, l, t)
            sr.map { s2 =>
              val (s3, l2, t2) = s2.popTransformation
              assert((l2 eq l) && (t2 eq t))
              s3
            }
          }
        }

      val evalGuard : (S, LocationDecl, Transformation, Exp) --> C =
        new PartialFunction[(S, LocationDecl, Transformation, Exp), C] {
          def isDefinedAt(slte : (S, LocationDecl, Transformation, Exp)) =
            ev.evalGuard.isDefinedAt(slte)
          def apply(slt : (S, LocationDecl, Transformation, Exp)) = {
            val (s, l, t, e) = slt
            val s1 = s.pushGuard(l, t, e)
            val sr = ev.evalGuard(s1, l, t, e)
            sr.map { sb =>
              val (s2, b) = sb
              val (s3, l2, t2, e2) = s2.popGuard
              assert((l2 eq l) && (t2 eq t) && (e2 eq e))
              (s3, b)
            }
          }
        }

      val transitions : (S, LocationDecl) --> Transitions[S] = ev.transitions
    }
}
