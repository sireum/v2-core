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
  def evalTransformation : (S, Transformation) --> SR
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait LocationEvaluator[S] {
  def transitions : (S, LocationDecl) --> Transitions[S]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Transitions[S] {
  def enabled : ISeq[(S, Transformation)]
  def disabled : ISeq[(S, Transformation)]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Evaluator[S, R, SR]
    extends ExpEvaluator[S, R]
    with ActionEvaluator[S, SR]
    with JumpEvaluator[S, SR]
    with TransformationEvaluator[S, SR]
    with LocationEvaluator[S] {
  def mainEvaluator : Evaluator[S, R, SR]
  def setMainEvaluator(eval : Evaluator[S, R, SR])
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
  def semanticExtensionConsumer(ev : Evaluator[S, R, SR]) : SemanticsExtensionConsumer[S, V, R, C, SR]

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
  var evaluator : Evaluator[S, R, SR]

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
object Evaluator {
  def combine[S, R, SR](evs : Evaluator[S, R, SR]*) = {
    require(evs.length > 0)
    evs.length match {
      case 1 => evs(0)
      case _ =>
        new Evaluator[S, R, SR] {
          var ev : Evaluator[S, R, SR] = this
          def mainEvaluator = ev
          def setMainEvaluator(eval : Evaluator[S, R, SR]) {
            for (e <- evs) {
              e.setMainEvaluator(eval)
            }
            ev = eval
          }

          import PartialFunctionUtil._

          val evalExp : (S, Exp) --> R = orElses(evs.map { ev => ev.evalExp })
          val evalAction : (S, Action) --> SR = orElses(evs.map { ev => ev.evalAction })
          val evalJump : (S, Jump) --> SR = orElses(evs.map { ev => ev.evalJump })
          val evalTransformation : (S, Transformation) --> SR = orElses(evs.map { ev => ev.evalTransformation })
          val transitions : (S, LocationDecl) --> Transitions[S] = orElses(evs.map { ev => ev.transitions })
        }
    }
  }
}
