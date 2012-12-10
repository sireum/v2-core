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
abstract class Slot

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case object ReturnSlot extends Slot {
  override def toString = "return"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class VarSlot(varUri: ResourceUri) extends Slot {
  override def toString =
    varUri.substring(varUri.lastIndexOf('/') + 1)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait VarAccesses {
  def localVarAccesses(procedureUri: ResourceUri): CSet[Slot]
  def globalVarReads(procedureUri: ResourceUri): CSet[Slot]
  def globalVarWrites(procedureUri: ResourceUri): CSet[Slot]
  def strongGlobalVarWrites(procedureUri: ResourceUri): CSet[Slot]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait DefRef {
  self =>

  def varAccesses: VarAccesses

  def definitions(a: Assignment): ISet[Slot]
  def strongDefinitions(a: Assignment): ISet[Slot]
  def references(a: Action): ISet[Slot]
  def references(j: Jump): ISet[Slot]
  def callReferences(j: CallJump): ISeq[ISet[Slot]]
  def callDefinitions(j: CallJump): ISeq[ISet[Slot]]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class BasicVarAccesses(st: SymbolTable) extends VarAccesses {
  def localVarAccesses(procedureUri: ResourceUri): CSet[Slot] =
    procedureLocalAccessCache(procedureUri)

  def globalVarReads(procedureUri: ResourceUri): CSet[Slot] =
    procedureGlobalReadCache(procedureUri)

  def globalVarWrites(procedureUri: ResourceUri): CSet[Slot] =
    procedureGlobalWriteCache(procedureUri)

  def strongGlobalVarWrites(procedureUri: ResourceUri): CSet[Slot] =
    Set()

  private val (procedureLocalAccessCache, procedureGlobalReadCache, procedureGlobalWriteCache) = {
    val localAccesses = mmapEmpty[ResourceUri, MSet[Slot]]
    val globalReads = mmapEmpty[ResourceUri, MSet[Slot]]
    val globalWrites = mmapEmpty[ResourceUri, MSet[Slot]]

    def init() {
      var accessLocalVars = msetEmpty[Slot]
      var readGlobalVars = msetEmpty[Slot]
      var writtenGlobalVars = msetEmpty[Slot]

      def addLocalAccess(ne: NameExp) =
        if (H.isLocalVar(ne.name))
          accessLocalVars += VarSlot(ne.name.uri)

      val visitor = Visitor.build({
        case a: Assignment =>
          val lhss = PilarAstUtil.getLHSs(a)
          for (NameExp(name) <- lhss.keys)
            if (name.hasResourceInfo && H.isGlobalVar(name))
              writtenGlobalVars += VarSlot(name.uri)
          Visitor.build({
            case ne: NameExp =>
              if (ne.name.hasResourceInfo)
                if (H.isGlobalVar(ne.name) && !lhss.contains(ne))
                  readGlobalVars += VarSlot(ne.name.uri)
                else
                  addLocalAccess(ne)
              false
          })(a)
          false
        case ne: NameExp =>
          if (ne.name.hasResourceInfo)
            if (H.isGlobalVar(ne.name))
              readGlobalVars += VarSlot(ne.name.uri)
            else
              addLocalAccess(ne)
          false
      })

      st.procedureSymbolTables.map { pst =>
        val p = pst.procedure
        visitor(p)
        localAccesses(pst.procedureUri) = accessLocalVars
        globalReads(pst.procedureUri) = readGlobalVars
        globalWrites(pst.procedureUri) = writtenGlobalVars
        accessLocalVars = msetEmpty[Slot]
        readGlobalVars = msetEmpty[Slot]
        writtenGlobalVars = msetEmpty[Slot]
      }
    }

    def fixPoint() {
      var changed = true
      def visitor(pUri: ResourceUri) =
        Visitor.build({
          case j: CallJump =>
            H.mapCalls(st, j,
              { j => { procedureAbsUri => true } },
              { pst =>
                val readGlobals = globalReads(pUri)
                val readSize = readGlobals.size
                readGlobals ++= globalReads(pst.procedureUri)
                if (readGlobals.size != readSize)
                  changed = true
                val writtenGlobals = globalWrites(pUri)
                val writtenSize = writtenGlobals.size
                writtenGlobals ++= globalWrites(pst.procedureUri)
                if (writtenGlobals.size != writtenSize)
                  changed = true
              })
            false
        })
      while (changed) {
        changed = false
        st.procedureSymbolTables.foreach { pst =>
          visitor(pst.procedureUri)(pst.procedure)
        }
      }
    }

    init()
    fixPoint()
    (localAccesses, globalReads, globalWrites)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class BasicVarDefRef(st: SymbolTable, val varAccesses: VarAccesses)
  extends DefRef {

  private val trueFilter = { j: CallJump =>
    { procedureAbsUri: ResourceUri => true }
  }

  def definitions(a: Assignment): ISet[Slot] = {
    import org.sireum.pilar.symbol.H

    val strongDefs = strongDefinitions(a)
    a match {
      case j: CallJump =>
        val writtenGlobals = H.mapCalls(st, j,
          trueFilter,
          { pst =>
            varAccesses.globalVarWrites(pst.procedureUri).toSet[Slot]
          })
        strongDefs ++ writtenGlobals.fold(isetEmpty[Slot])(iunion[Slot])
      case _ =>
        strongDefs
    }
  }

  def strongDefinitions(a: Assignment): ISet[Slot] =
    defCache.getOrElseUpdate(a, {
      val lhss = PilarAstUtil.getLHSs(a)
      var result = isetEmpty[Slot]
      for (ne @ NameExp(_) <- lhss.keys) {
        result = result + VarSlot(ne.name.uri)
      }
      result
    })

  def references(a: Action): ISet[Slot] =
    refCache.getOrElseUpdate(a, getRefs(a))

  def references(j: Jump): ISet[Slot] =
    refCache.getOrElseUpdate(j, getRefs(j))

  def callReferences(j: CallJump): ISeq[ISet[Slot]] = {
    val arg = j.callExp.arg
    arg match {
      case e: TupleExp =>
        val result = e.exps.map { exp => refCache.getOrElseUpdate(exp, getRefs(exp)) }
        result
      case e =>
        ilist(refCache.getOrElseUpdate(j, getRefs(e)))
    }
  }

  def callDefinitions(j: CallJump): ISeq[ISet[Slot]] = {
    val arg = j.callExp.arg
    arg match {
      case e: TupleExp =>
        e.exps.map { exp => isetEmpty[Slot] }
      case e =>
        ilist(isetEmpty[Slot])
    }
  }

  private def getRefs(n: PilarAstNode): ISet[Slot] = {
    if (n.isInstanceOf[CallJump]) {
      val j = n.asInstanceOf[CallJump]
      return H.mapCalls(st, j, trueFilter,
        { pst =>
          varAccesses.globalVarReads(pst.procedureUri).toSet
        }).fold(isetEmpty[Slot])(iunion[Slot])
    }
    var result = isetEmpty[Slot]
    val lhss = PilarAstUtil.getLHSs(n)
    Visitor.build({
      case ne: NameExp =>
        if (!lhss.contains(ne))
          result = result + VarSlot(ne.name.uri)
        false
    })(n)
    result
  }

  private val defCache = idmapEmpty[Assignment, ISet[Slot]]
  private val refCache = idmapEmpty[PilarAstNode, ISet[Slot]]
}