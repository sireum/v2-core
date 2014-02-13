/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
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
trait ExpEvaluatorExt[S, R] {
  def evalExpBefore : Option[(S, Exp) => S]
  def evalExpAfter : Option[(S, Exp, R) => R]
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
trait ActionEvaluatorExt[S, SR] {
  def evalActionBefore : Option[(S, Action) => S]
  def evalActionAfter : Option[(S, Action, SR) => SR]
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
trait JumpEvaluatorExt[S, SR] {
  def evalJumpBefore : Option[(S, Jump) => S]
  def evalJumpAfter : Option[(S, Jump, SR) => SR]
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
trait TransformationEvaluatorExt[S, SR] {
  def evalTransformationBefore : Option[(S, LocationDecl, Transformation) => S]
  def evalTransformationAfter : Option[(S, LocationDecl, Transformation, SR) => SR]
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
trait LocationEvaluatorExt[S, C] {
  def evalGuardBefore : Option[(S, LocationDecl, Transformation, Exp) => S]
  def evalGuardAfter : Option[(S, LocationDecl, Transformation, Exp, C) => C]
  def transitionsBefore : Option[(S, LocationDecl) => S]
  def transitionsAfter : Option[(S, LocationDecl, Transitions[S]) => Transitions[S]]
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

  def combine[S, R, C, SR](ev : Evaluator[S, R, C, SR], evxs : EvaluatorExt[S, R, C, SR]*) =
    if (evxs.length < 1) ev
    else
      new Evaluator[S, R, C, SR] {
        var mainEvaluator = ev
        def setMainEvaluator(eval : Evaluator[S, R, C, SR]) {
          mainEvaluator = eval
          ev.setMainEvaluator(eval)
        }

        val evalExp = new PartialFunction[(S, Exp), R] {
          val eebs = evxs.flatMap(_.evalExpBefore)
          val eeas = evxs.flatMap(_.evalExpAfter)
          def isDefinedAt(o : (S, Exp)) = ev.evalExp.isDefinedAt(o)
          def apply(o : (S, Exp)) : R = {
            val ee = ev.evalExp
            val (s, e) = o
            var sNext = s
            for (eeb <- eebs) {
              sNext = eeb(sNext, e)
            }
            var r = ee(sNext, e)
            for (eea <- eeas) {
              r = eea(s, e, r)
            }
            r
          }
        }

        val evalAction = new PartialFunction[(S, Action), SR] {
          val eabs = evxs.flatMap(_.evalActionBefore)
          val eaas = evxs.flatMap(_.evalActionAfter)
          def isDefinedAt(o : (S, Action)) = ev.evalAction.isDefinedAt(o)
          def apply(o : (S, Action)) : SR = {
            val ea = ev.evalAction
            val (s, a) = o
            var sNext = s
            for (eab <- eabs) {
              sNext = eab(sNext, a)
            }
            var sr = ea(sNext, a)
            for (eaa <- eaas) {
              sr = eaa(s, a, sr)
            }
            sr
          }
        }

        val evalJump = new PartialFunction[(S, Jump), SR] {
          val ejbs = evxs.flatMap(_.evalJumpBefore)
          val ejas = evxs.flatMap(_.evalJumpAfter)
          def isDefinedAt(o : (S, Jump)) = ev.evalJump.isDefinedAt(o)
          def apply(o : (S, Jump)) : SR = {
            val ej = ev.evalJump
            val (s, j) = o
            var sNext = s
            for (ejb <- ejbs) {
              sNext = ejb(sNext, j)
            }
            var sr = ej(sNext, j)
            for (eja <- ejas) {
              sr = eja(s, j, sr)
            }
            sr
          }
        }

        val evalTransformation = new PartialFunction[(S, LocationDecl, Transformation), SR] {
          val etbs = evxs.flatMap(_.evalTransformationBefore)
          val etas = evxs.flatMap(_.evalTransformationAfter)
          def isDefinedAt(o : (S, LocationDecl, Transformation)) = ev.evalTransformation.isDefinedAt(o)
          def apply(o : (S, LocationDecl, Transformation)) : SR = {
            val et = ev.evalTransformation
            val (s, l, t) = o
            var sNext = s
            for (etb <- etbs) {
              sNext = etb(sNext, l, t)
            }
            var sr = et(sNext, l, t)
            for (eta <- etas) {
              sr = eta(s, l, t, sr)
            }
            sr
          }
        }

        val evalGuard = new PartialFunction[(S, LocationDecl, Transformation, Exp), C] {
          val egbs = evxs.flatMap(_.evalGuardBefore)
          val egas = evxs.flatMap(_.evalGuardAfter)
          def isDefinedAt(o : (S, LocationDecl, Transformation, Exp)) = ev.evalGuard.isDefinedAt(o)
          def apply(o : (S, LocationDecl, Transformation, Exp)) : C = {
            val eg = ev.evalGuard
            val (s, l, t, e) = o
            var sNext = s
            for (egb <- egbs) {
              sNext = egb(sNext, l, t, e)
            }
            var c = eg(sNext, l, t, e)
            for (ega <- egas) {
              c = ega(s, l, t, e, c)
            }
            c
          }
        }

        val transitions = new PartialFunction[(S, LocationDecl), Transitions[S]] {
          val tbs = evxs.flatMap(_.transitionsBefore)
          val tas = evxs.flatMap(_.transitionsAfter)
          def isDefinedAt(o : (S, LocationDecl)) = ev.transitions.isDefinedAt(o)
          def apply(o : (S, LocationDecl)) : Transitions[S] = {
            val t = ev.transitions
            val (s, l) = o
            var sNext = s
            for (tb <- tbs) {
              sNext = tb(sNext, l)
            }
            var ts = t(sNext, l)
            for (ta <- tas) {
              ts = ta(s, l, ts)
            }
            ts
          }
        }
      }

  def module[S, R, C, SR](_ev : Evaluator[S, R, C, SR]) =
    new Evaluator[S, R, C, SR] with EvaluatorModule {
      var ev : Evaluator[S, R, C, SR] = this
      def mainEvaluator = ev
      def setMainEvaluator(eval : Evaluator[S, R, C, SR]) {
        ev = eval
        _ev.setMainEvaluator(eval)
      }

      def initialize(config : ExtensionConfig) {
        _ev match {
          case em : EvaluatorModule =>
            em.initialize(config)
            em.setMainEvaluator(this)
          case _ =>
        }
      }

      import PartialFunctionUtil._

      val evalExp = ev.evalExp
      val evalAction = ev.evalAction
      val evalJump = ev.evalJump
      val evalTransformation = ev.evalTransformation
      val evalGuard = ev.evalGuard
      val transitions = ev.transitions
    }

  def context[S <: EvaluationContextProvider[S], V](
    ev : Evaluator[S, ISeq[(S, V)], ISeq[(S, Boolean)], ISeq[S]]) : Evaluator[S, ISeq[(S, V)], ISeq[(S, Boolean)], ISeq[S]] with EvaluatorModule =
    module(combine(ev, new EvaluationContextEvaluatorExt[S, V]))
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EvaluatorExt[S, R, C, SR]
    extends ExpEvaluatorExt[S, R]
    with ActionEvaluatorExt[S, SR]
    with JumpEvaluatorExt[S, SR]
    with TransformationEvaluatorExt[S, SR]
    with LocationEvaluatorExt[S, C] {
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
  private def peek[T <: ContextStackElement] : T = peekContext(0)

  @inline
  private def peekAt[T <: ContextStackElement](i : Int) : T = peekContext(i)

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

  def pushTransformation(l : LocationDecl, t : Transformation) : C = push(l).push(t)
  def popTransformation : (C, LocationDecl, Transformation) = {
    val t : Transformation = peek
    val c1 = pop
    val l : LocationDecl = c1.peek
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
    (peekAt(2), peek)
}

final class EvaluationContextEvaluatorExt[S <: EvaluationContextProvider[S], V]
    extends EvaluatorExt[S, ISeq[(S, V)], ISeq[(S, Boolean)], ISeq[S]] {
  type R = ISeq[(S, V)]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]

  val evalExpBefore = Some((s : S, e : Exp) => s.pushExp(e))
  val evalExpAfter = Some((s : S, e : Exp, r : R) =>
    r.map { re =>
      val (s2, v) = re
      val (s3, e2) = s2.popExp
      assert(e2 eq e)
      (s3, v)
    })
  val evalActionBefore = Some((s : S, a : Action) => s.pushAction(a))
  val evalActionAfter = Some((s : S, a : Action, sr : SR) =>
    sr.map { s2 =>
      val (s3, a2) = s2.popAction
      assert(a2 eq a)
      s3
    })
  val evalJumpBefore = Some((s : S, j : Jump) => s.pushJump(j))
  val evalJumpAfter = Some((s : S, j : Jump, sr : SR) =>
    sr.map { s2 =>
      val (s3, j2) = s2.popJump
      assert(j2 eq j)
      s3
    })
  val evalTransformationBefore =
    Some((s : S, l : LocationDecl, t : Transformation) =>
      s.pushTransformation(l, t))
  val evalTransformationAfter =
    Some((s : S, l : LocationDecl, t : Transformation, sr : SR) =>
      sr.map { s2 =>
        val (s3, l2, t2) = s2.popTransformation
        assert((l2 eq l) && (t2 eq t))
        s3
      })
  val evalGuardBefore =
    Some((s : S, l : LocationDecl, t : Transformation, e : Exp) =>
      s.pushGuard(l, t, e))
  val evalGuardAfter =
    Some((s : S, l : LocationDecl, t : Transformation, e : Exp, c : C) =>
      c.map { sb =>
        val (s2, b) = sb
        val (s3, l2, t2, e2) = s2.popGuard
        assert((l2 eq l) && (t2 eq t) && (e2 eq e))
        (s3, b)
      })
  val transitionsBefore = None
  val transitionsAfter = None
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolProvider[S] {
  def extUri(e : NameExp, extPresent : ResourceUri => Boolean) : Option[ResourceUri] = {
    val name = e.name
    if (name.hasResourceInfo && extPresent(name.uri))
      Some(name.uri)
    else if (extPresent(name.name))
      Some(name.name)
    else None
  }
  def isVar(e : NameExp) : Boolean
  def procedureUri(e : NameExp) : Option[ResourceUri]
  def funUri(e : NameExp) : Option[ResourceUri]
  def isFieldAccess(f : NameUser) : Boolean
  def initLocation(procUri : ResourceUri) : Option[ResourceUri]
  def location(s : S, nu : NameUser) : S
  def nextLocation(s : S) : S
  def initStore(s : S, procUri : ResourceUri, args : Value*) : State.Store
}

trait PilarSymbolProvider[S] extends SymbolProvider[S] {
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
  def typeValue(typ : TypeSpec) : Value
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
trait EvaluatorModule {
  def initialize(ec : ExtensionConfig)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait TypeProviderConfig {
  def typeProvider : TypeProvider
}

object TypeProviderConfig {
  import language.implicitConversions

  implicit def ec2tpc(ec : ExtensionConfig) : TypeProviderConfig =
    ec.asInstanceOf[TypeProviderConfig]

  implicit object TypeProviderAdapter
      extends Adapter[ExtensionConfig, TypeProviderConfig] {
    def adapt(ec : ExtensionConfig) : TypeProviderConfig = ec
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EvaluatorConfiguration
    extends PropertyProvider
    with ExtensionConfig
    with SemanticsExtensionConfig
    with TypeProviderConfig
    with EvaluatorConfig
    with EvaluatorHeapConfig {
  def symbolProvider[S] : SymbolProvider[S]
  def typeProvider : TypeProvider
  def elseGuardExpander : Option[ElseGuardExpander]
  def computeDisabledTransitions : Boolean
  def extensions : ISeq[ExtensionCompanion]
  def evaluator[S, R, C, SR] : Evaluator[S, R, C, SR]
  def evalConfig : EvaluatorConfiguration = this
  def valueToV[V](v : Value) : V
  def vToValue[V](v : V) : Value
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EvaluatorConfig {
  def evalConfig : EvaluatorConfiguration
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object EvaluatorConfig {
  import language.implicitConversions

  implicit def ec2tpc(ec : ExtensionConfig) : EvaluatorConfig =
    ec.asInstanceOf[EvaluatorConfig]

  implicit object EvaluatorConfigAdapter
      extends Adapter[ExtensionConfig, EvaluatorConfig] {
    def adapt(ec : ExtensionConfig) : EvaluatorConfig = ec
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EvaluatorHeapConfiguration {
  self : PropertyProvider =>

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
trait EvaluatorHeapConfig {
  def heapEvalConfig : EvaluatorHeapConfiguration
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object EvaluatorHeapConfig {
  import language.implicitConversions

  implicit def ec2tpc(ec : ExtensionConfig) : EvaluatorHeapConfig =
    ec.asInstanceOf[EvaluatorHeapConfig]

  implicit object EvaluatorHeapConfigAdapter
      extends Adapter[ExtensionConfig, EvaluatorHeapConfig] {
    def adapt(ec : ExtensionConfig) : EvaluatorHeapConfig = ec
  }
}
