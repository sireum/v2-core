/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
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
object ReachingDefinitionAnalysis {
  type Result = MonotoneDataFlowAnalysisResult[RDFact]

  type RDFact = (Slot, DefDesc)

  def apply[VirtualLabel] = build[VirtualLabel] _

  def build[VirtualLabel] //
  (pst : ProcedureSymbolTable,
   cfg : ControlFlowGraph[VirtualLabel],
   defRef : DefRef,
   isInputParam : ResourceUri => Boolean,
   switchAsOrderedMatch : Boolean = false) : Result = {
    val gen = new Gen(defRef)
    val kill = new Kill(defRef)
    val isInputParamSlot = { slot : Slot =>
      slot match {
        case VarSlot(varUri) => isInputParam(varUri)
        case _               => false
      }
    }
    val iota : ISet[RDFact] = {
      val result = msetEmpty[RDFact]
      val varAccesses = defRef.varAccesses
      for (slot <- varAccesses.localVarAccesses(pst.procedureUri))
        if (isInputParamSlot(slot))
          result += ((slot, InitDefDesc))
        else
          result += ((slot, UnDefDesc))
      for (slot <- varAccesses.globalVarReads(pst.procedureUri))
        result += ((slot, InitDefDesc))
      result.toSet
    }
    val initial : ISet[RDFact] = isetEmpty
    val result = MonotoneDataFlowAnalysisFramework[RDFact, VirtualLabel](pst,
      cfg, true, true, false, gen, kill, iota, initial, switchAsOrderedMatch)

    //print("RDA\n")
    //print(result)

    result
  }

  protected class Gen(defRef : DefRef)
      extends MonotonicFunction[RDFact] {
    def apply(s : ISet[RDFact], a : Assignment) : ISet[RDFact] = {
      val ldd = DefDesc.desc(a)
      a match {
        case j : CallJump =>
          val strongDefs = defRef.strongDefinitions(j)
          val defs = defRef.definitions(j).diff(strongDefs)
          val callDefs = defRef.callDefinitions(j)
          var i = -1
          val paramDefs = callDefs.map { s =>
            i += 1
            s.diff(defs).map { d =>
              (d, ParamDefDesc(ldd.locUri, ldd.locIndex, ldd.transIndex,
                ldd.commandIndex, i)) : RDFact
            }
          }.fold(Set[RDFact]())(iunion[RDFact] _)
          paramDefs.union(defs.map { d =>
            (d, EffectDefDesc(ldd.locUri, ldd.locIndex, ldd.transIndex, ldd.commandIndex))
          }) ++ strongDefs.map { d => (d, ldd) }
        case _ =>
          defRef.definitions(a).map { d => (d, ldd) }
      }
    }

    def apply(s : ISet[RDFact], e : Exp) : ISet[RDFact] = isetEmpty
  }

  protected class Kill(defRef : DefRef)
      extends MonotonicFunction[RDFact] {
    def apply(s : ISet[RDFact], a : Assignment) : ISet[RDFact] = {
      val strongDefs = defRef.strongDefinitions(a)
      var result = s
      for (rdf @ (slot, _) <- s) {
        if (strongDefs.contains(slot)) {
          result = result - rdf
        }
      }
      result
    }

    def apply(s : ISet[RDFact], e : Exp) : ISet[RDFact] = s
  }
}