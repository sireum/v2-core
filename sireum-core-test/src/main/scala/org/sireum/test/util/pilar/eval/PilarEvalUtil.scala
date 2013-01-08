/*
Copyright (c) 2011 - 2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.util.pilar.eval

import org.sireum.extension._
import org.sireum.util._
import org.sireum.pilar.state._
import org.sireum.pilar.symbol._
import org.sireum.pilar.eval._
import org.sireum.pilar.ast._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object PilarEvalUtil {

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final class TypeProviderImpl extends TypeProvider {
    def typeUri(symUri : ResourceUri) : ResourceUri = {
      val i = symUri.indexOf('_')
      val ext = if (i >= 0) symUri.substring(i + 1) else ""
      ext match {
        case "obj"  => ObjectExtension.RefType
        case "vobj" => ObjectExtension.ValType
        case _      => IntegerExtension.Type
      }
    }
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  class PilarSymbolProviderImpl[S <: State[S]](st : Option[SymbolTable])
      extends PilarSymbolProvider[S] {
    def initLocation(procUri : ResourceUri) : Option[ResourceUri] =
      st match {
        case Some(st) =>
          val pst = st.procedureSymbolTable(procUri)
          val ld = pst.location(0)
          ld.name.map { nd => nd.uri }
        case _ =>
          None
      }

    def location(s : S, nu : NameUser) : S =
      st match {
        case Some(st) =>
          val pst = st.procedureSymbolTable(s.callStack.head.procedure)
          val ld = pst.location(nu.uri)
          s.location(ld.name.map { nd => nd.uri }, ld.index)
        case _ =>
          s.location(Some(nu.name), 1)
      }

    def nextLocation(s : S) : S =
      st match {
        case Some(st) =>
          val pst = st.procedureSymbolTable(s.callStack.head.procedure)
          val ld = pst.location(s.callStack.head.locationIndex + 1)
          s.location(ld.name.map { nd => nd.uri }, ld.index)
        case _ =>
          s.location(Some("next"), 1)
      }

    def initStore(s : S, procUri : ResourceUri,
                  args : Value*) : State.Store =
      st match {
        case Some(st) =>
          val store : MMap[ResourceUri, Value] = mmapEmpty
          val pst = st.procedureSymbolTable(procUri)
          val params = pst.params
          val numOfParams = params.size
          assert(args.size == numOfParams)
          numOfParams match {
            case 0 =>
            case 1 =>
              store(params(0)) = args(0)
            case n =>
              for (i <- 0 until n)
                store(params(i)) = args(i)
          }
          store.toMap
        case _ =>
          imapEmpty
      }
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final class EvaluatorConfigurationImpl[S <: State[S]](
    st : Option[SymbolTable],
    ev : Evaluator[S, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] with EvaluatorModule[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]],
    sem : SemanticsExtensionModule[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]],
    exts : ExtensionCompanion*)
      extends EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]
      with EvaluatorHeapConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {
    self =>
    type R = ISeq[(S, Value)]
    type C = ISeq[(S, Boolean)]
    type SR = ISeq[S]
    var symbolProvider : SymbolProvider[S] = new PilarSymbolProviderImpl[S](st)
    var typeProvider : TypeProvider = new TypeProviderImpl
    var elseGuardExpander : Option[ElseGuardExpander] = None
    var computeDisabledTransitions : Boolean = false
    var extensions : ISeq[ExtensionCompanion] = exts.toList
    val propertyMap = mmapEmpty[Property.Key, Any]
    def valueToV(v : Value) : Value = v
    def vToValue(v : Value) : Value = v
    var evaluator : Evaluator[S, R, C, SR] = ev
    var semanticsExtension : SemanticsExtensionConsumer[S, Value, R, C, SR] = null

    ev.initialize(this)
    sem.initialize(this)
  }
}