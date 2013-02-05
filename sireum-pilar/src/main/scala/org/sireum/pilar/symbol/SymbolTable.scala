/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.symbol

import org.sireum.pilar.ast._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolTable {
  def globalVars : Iterable[ResourceUri]
  def globalVar(globalUri : ResourceUri) : GlobalVarDecl
  def procedures : Iterable[ResourceUri]
  def procedures(procedureUri : ResourceUri) : Iterable[ResourceUri]
  def procedureSymbolTables : Iterable[ProcedureSymbolTable]
  def procedureSymbolTable(procedureAbsUri : ResourceUri) : ProcedureSymbolTable
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ProcedureSymbolTable {
  def symbolTable : SymbolTable

  def procedureUri : ResourceUri
  def procedure : ProcedureDecl
  def typeVars : ISeq[ResourceUri]
  def params : ISeq[ResourceUri]
  def isParam(localUri : ResourceUri) : Boolean
  def locals : Iterable[ResourceUri]
  def nonParamLocals : Iterable[ResourceUri]
  def locations : ISeq[LocationDecl]
  def typeVar(typeVarUri : ResourceUri) : NameDefinition
  def param(paramUri : ResourceUri) : ParamDecl
  def local(localUri : ResourceUri) : LocalVarDecl
  def location(locationIndex : Int) : LocationDecl
  def location(locationUri : ResourceUri) : LocationDecl
  def catchClauses(locationIndex : Int) : Iterable[CatchClause]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object SymbolTable {
  def apply(models : ISeq[Model],
            stpConstructor : Unit => SymbolTableProducer,
            parallel : Boolean) =
    buildSymbolTable(models, stpConstructor, parallel)

  def apply[P <: SymbolTableProducer] //
  (stp : SymbolTableProducer, stModels : ISeq[Model],
   changedOrDeletedModelFiles : Set[FileResourceUri],
   changedOrAddedModels : ISeq[Model],
   stpConstructor : Unit => P,
   parallel : Boolean) : Unit =
    fixSymbolTable(stp, stModels, changedOrDeletedModelFiles,
      changedOrAddedModels, stpConstructor, parallel)

  def minePackageElements[P <: SymbolTableProducer] //
  (models : ISeq[Model], stpConstructor : Unit => P,
   parallel : Boolean) : SymbolTableProducer = {
    if (models.isEmpty) return stpConstructor()

    val ms : GenSeq[Model] = if (parallel) models.par else models
    ms.map { model =>
      val stp = stpConstructor()
      new H.PackageElementMiner(stp).packageElementMiner(model)
      val tables = stp.tables
      model.sourceURI.foreach { fileUri =>
        val set = msetEmpty[ResourceUri]
        set ++= tables.constTable.keys
        set ++= tables.constElementTable.keys
        set ++= tables.enumTable.keys
        set ++= tables.enumElementTable.keys
        set ++= tables.extensionTable.keys
        set ++= tables.extensionElementTable.keys
        set ++= tables.funTable.keys
        set ++= tables.globalVarTable.keys
        set ++= tables.procedureTable.keys
        set ++= tables.procedureAbsTable.keys
        set ++= tables.recordTable.keys
        set ++= tables.attributeTable.keys
        set ++= tables.typeVarTable.keys
        set ++= tables.typeAliasTable.keys
        set ++= tables.vsetTable.keys
        tables.declaredSymbols(fileUri) = set
      }
      stp
    }.toIterable.reduce(H.combine)
  }

  def resolveRecordHierarchy(stp : SymbolTableProducer) : Unit = {
    val dm = mmapEmpty[String, MSet[String]]
    new Object with RecordHierarchyResolver {
      override def dependency = dm
    }.recordHierarchyResolver(stp)
    H.combineMap(stp.tables.dependency, dm)
  }

  def buildProcedureSymbolTables(stp : SymbolTableProducer, parallel : Boolean = true) : Unit = {
    val procedures = stp.tables.procedureAbsTable.keys.toSeq
    val col : GenSeq[ResourceUri] = if (parallel) procedures.par else procedures
    col.foreach { procedureUri =>
      val pstp = stp.procedureSymbolTableProducer(procedureUri)
      val pd = stp.tables.procedureAbsTable(procedureUri)
      pd.body match {
        case body : ImplementedBody =>
          pstp.tables.bodyTables =
            Some(BodySymbolTableData())
        case body : EmptyBody =>
      }
      val pmr = new H.ProcedureMinerResolver(pstp)
      pmr.procMiner(pd)
      pmr.procResolver(pd)
    }
  }

  def resolvePackageElements(models : ISeq[Model], stp : SymbolTableProducer,
                             parallel : Boolean) : Unit = {
    if (models.isEmpty) return

    val ms : GenSeq[Model] = if (parallel) models.par else models

    val dependencies = ms.map { model =>
      val per = new H.PackageElementResolver(stp)
      per.packageElementResolver(model)
      per.dependency
    }
    dependencies.foldLeft(stp.tables.dependency)(H.combineMap)
  }

  def buildSymbolTable(models : ISeq[Model],
                       stpConstructor : Unit => SymbolTableProducer,
                       parallel : Boolean) = {
    val stp = minePackageElements(models, stpConstructor, parallel)
    resolveRecordHierarchy(stp)
    resolvePackageElements(models, stp, parallel)
    buildProcedureSymbolTables(stp)
    stp.toSymbolTable
  }

  def fixSymbolTable[P <: SymbolTableProducer] //
  (stp : SymbolTableProducer, stModels : ISeq[Model],
   changedOrDeletedModelFiles : Set[FileResourceUri],
   changedOrAddedModels : ISeq[Model],
   stpConstructor : Unit => P,
   parallel : Boolean) : Unit = {

    val models = mlistEmpty[Model]
    stModels.foreach { m =>
      m.sourceURI match {
        case Some(uri) =>
          if (changedOrDeletedModelFiles.contains(uri))
            H.tearDown(stp.tables, m)
          else
            models += m
        case _ =>
      }
    }
    H.combine(stp, minePackageElements(changedOrAddedModels, stpConstructor, parallel))
    resolveRecordHierarchy(stp)
    models ++= changedOrAddedModels
    resolvePackageElements(models.toList, stp, parallel)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolTableReporter {
  def reportError(fileUri : Option[String], line : Int,
                  column : Int, message : String) : Unit

  def reportWarning(fileUri : Option[String], line : Int,
                    column : Int, message : String) : Unit
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolTableProducer extends SymbolTableReporter {
  def tables : SymbolTableData

  def procedureSymbolTableProducer //
  (procedureUri : ResourceUri) : ProcedureSymbolTableProducer

  def toSymbolTable : SymbolTable
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ProcedureSymbolTableProducer {
  def tables : ProcedureSymbolTableData

  def symbolTableProducer : SymbolTableProducer
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed case class SymbolTableData //
(declaredSymbols : MMap[FileResourceUri, MSet[ResourceUri]] = mmapEmpty,
 constTable : MMap[ResourceUri, MBuffer[ConstDecl]] = mmapEmpty,
 constElementTable : MMap[ResourceUri, ConstElement] = mmapEmpty,
 enumTable : MMap[ResourceUri, MBuffer[EnumDecl]] = mmapEmpty,
 enumElementTable : MMap[ResourceUri, EnumElement] = mmapEmpty,
 extensionTable : MMap[ResourceUri, MBuffer[ExtensionDecl]] = mmapEmpty,
 extensionElementTable : MMap[ResourceUri, ExtElement] = mmapEmpty,
 funTable : MMap[ResourceUri, FunDecl] = mmapEmpty,
 globalVarTable : MMap[ResourceUri, GlobalVarDecl] = mmapEmpty,
 procedureTable : MMap[ResourceUri, MBuffer[ResourceUri]] = mmapEmpty,
 procedureAbsTable : MMap[ResourceUri, ProcedureDecl] = mmapEmpty,
 recordTable : MMap[ResourceUri, RecordDecl] = mmapEmpty,
 attributeTable : MMap[ResourceUri, AttributeDecl] = mmapEmpty,
 typeVarTable : MMap[ResourceUri, NameDefinition] = mmapEmpty,
 typeAliasTable : MMap[ResourceUri, TypeAliasDecl] = mmapEmpty,
 vsetTable : MMap[ResourceUri, VSetDecl] = mmapEmpty,
 dependency : DependencyMap = mmapEmpty)

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed case class ProcedureSymbolTableData //
(localVarTable : MMap[ResourceUri, LocalVar] = mmapEmpty,
 params : MList[ResourceUri] = mlistEmpty,
 typeVarTable : MMap[ResourceUri, NameDefinition] = mlinkedMapEmpty,
 var bodyTables : Option[BodySymbolTableData] = None)

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed case class BodySymbolTableData //
(locationTable : MMap[ResourceUri, LocationDecl] = mlinkedMapEmpty,
 catchTable : MMap[Int, MBuffer[CatchClause]] = mmapEmpty)
