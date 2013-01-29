/*
Copyright (c) 2011-2012 Jason Belt, Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

/**
 * The following class will be called reflectively.  Create the file
 * PilarSymbolResolverDef.scala in the directory corresponding to org.sireum.core.module
 * and paste the code into it
 */

package org.sireum.core.module

import org.sireum.pilar.ast._
import org.sireum.pilar.symbol._
import org.sireum.util._
import org.sireum.pipeline._

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object PilarSymbolResolverDef{
    val ERROR_TAG_TYPE = MarkerType(
    "org.sireum.pilar.tag.error.symtab",
    None,
    "Pilar Symbol Resolution Error",
    MarkerTagSeverity.Error,
    MarkerTagPriority.Normal,
    ivector(MarkerTagKind.Problem, MarkerTagKind.Text))
  val WARNING_TAG_TYPE = MarkerType(
    "org.sireum.pilar.tag.error.symtab",
    None,
    "Pilar Symbol Resolution Warning",
    MarkerTagSeverity.Warning,
    MarkerTagPriority.Normal,
    ivector(MarkerTagKind.Problem, MarkerTagKind.Text))
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class PilarSymbolResolverDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends PilarSymbolResolverModule {
  val ms = this.models
  val par = this.parallel
  val fst = { _ : Unit => new ST }

  val result =
    if (this.hasExistingModels.isDefined) {
      require(this.hasExistingModels.isDefined)
      val ems = this.hasExistingModels.get //job.existingModels
      val est = this.hasExistingModels.get.asInstanceOf[ST]
      val changedOrAddedFiles =
        ms.map { _.sourceURI }.filter { !_.isEmpty }.map { _.get }.toSet
      SymbolTable(est, ems, changedOrAddedFiles, ms, fst, par)
      est
    } else {
      SymbolTable(ms, fst, par)
    }

  val st = result.asInstanceOf[ST]
  info.tags ++= st.tags

  if (st.hasErrors)
    info.hasError = true

  this.symbolTable_=(result)

  class ST extends SymbolTable with SymbolTableProducer {
    st =>

    import PilarSymbolResolverDef.ERROR_TAG_TYPE
    import PilarSymbolResolverDef.WARNING_TAG_TYPE
    
    val tables = SymbolTableData()
    val tags = marrayEmpty[LocationTag]
    var hasErrors = false

    def reportError(source : Option[FileResourceUri], line : Int,
                    column : Int, message : String) : Unit = {
      tags += Tag.toTag(source, line, column, message, ERROR_TAG_TYPE)
      hasErrors = true
    }

    def reportWarning(fileUri : Option[String], line : Int,
                      column : Int, message : String) : Unit =
      tags += Tag.toTag(fileUri, line, column, message, WARNING_TAG_TYPE)

    val pdMap = mmapEmpty[ResourceUri, PST]

    def globalVars = tables.globalVarTable.keys
    def globalVar(globalUri : ResourceUri) = tables.globalVarTable(globalUri)

    def procedures = tables.procedureTable.keys

    def procedures(procedureUri : ResourceUri) = tables.procedureTable(procedureUri)

    def procedureSymbolTables = pdMap.values

    def procedureSymbolTable(procedureAbsUri : ResourceUri) : ProcedureSymbolTable =
      procedureSymbolTableProducer(procedureAbsUri)

    def procedureSymbolTableProducer(procedureAbsUri : ResourceUri) = {
      assert(tables.procedureAbsTable.contains(procedureAbsUri))
      pdMap.getOrElseUpdate(procedureAbsUri, new PST(procedureAbsUri))
    }

    class PST(val procedureUri : ResourceUri)
        extends ProcedureSymbolTable with ProcedureSymbolTableProducer {
      val tables = ProcedureSymbolTableData()
      var nextLocTable : CMap[ResourceUri, ResourceUri] = null
      def symbolTable = st
      def symbolTableProducer = st
      def procedure = st.tables.procedureAbsTable(procedureUri)
      def typeVars : ISeq[ResourceUri] = tables.typeVarTable.keys.toList
      def params : ISeq[ResourceUri] = tables.params.toList
      def isParam(localUri : ResourceUri) = tables.params.contains(localUri)
      def locals : Iterable[ResourceUri] = tables.localVarTable.keys
      def nonParamLocals : Iterable[ResourceUri] = tables.localVarTable.keys.filterNot(isParam)
      def locations =
        tables.bodyTables match {
          case Some(bt) => procedure.body.asInstanceOf[ImplementedBody].locations
          case _        => ivectorEmpty
        }
      def typeVar(typeVarUri : ResourceUri) : NameDefinition =
        tables.typeVarTable(typeVarUri)
      def param(paramUri : ResourceUri) : ParamDecl =
        tables.localVarTable(paramUri).asInstanceOf[ParamDecl]
      def local(localUri : ResourceUri) : LocalVarDecl =
        tables.localVarTable(localUri).asInstanceOf[LocalVarDecl]
      def location(locationIndex : Int) = locations(locationIndex)
      def location(locationUri : ResourceUri) =
        tables.bodyTables.get.locationTable(locationUri)
      def catchClauses(locationIndex : Int) : Iterable[CatchClause] =
        tables.bodyTables.get.catchTable.getOrElse(locationIndex,
          Array.empty[CatchClause] : Iterable[CatchClause])
    }

    def toSymbolTable : SymbolTable = this
  }

}
/*
package org.sireum.core.module

import org.sireum.pilar.ast._
import org.sireum.pilar.symbol._
import org.sireum.util._
import org.sireum.pipeline._

object PilarSymbolResolutionConsumerView {
  import PilarSymbolResolutionModule._

  final class PSRCV(job : PipelineJob) {
    @inline
    def symbolTable : SymbolTable = job(SYMBOL_TABLE_KEY)
  }

  @inline
  implicit def job2psrcv(job : PipelineJob) : PSRCV = new PSRCV(job)
}

object PilarSymbolResolutionProducerView {
  import PilarSymbolResolutionModule._

  final class PSRPV(job : PipelineJob) {
    @inline
    def symbolTable(st : SymbolTable) {
      job(SYMBOL_TABLE_KEY) = st
    }
  }

  @inline
  implicit def job2psrpv(job : PipelineJob) : PSRPV = new PSRPV(job)
}

object PilarSymbolResolutionModule extends PipelineModule {

  def title : String = "Pilar Symbol Resolution Module"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) = {
    import SireumConsumerView._
    import PilarSourceConsumerView._
    import PilarParserConsumerView._
    import PilarSymbolResolutionProducerView._

    val ms = job.models
    val par = job.parallel
    val fst = { _ : Unit => new ST }

    val result =
      if (job.hasExistingModels) {
        require(job.hasExistingSymbolTable)
        val ems = job.existingModels
        val est = job.existingSymbolTable.asInstanceOf[ST]
        val changedOrAddedFiles =
          ms.map { _.sourceURI }.filter { !_.isEmpty }.map { _.get }.toSet
        SymbolTable(est, ems, changedOrAddedFiles, ms, fst, par)
        est
      } else {
        SymbolTable(ms, fst, par)
      }

    val st = result.asInstanceOf[ST]
    info.tags ++= st.tags

    if (st.hasErrors)
      info.hasError = true

    job.symbolTable(result)
  }

  val SYMBOL_TABLE_KEY = "Pilar Symbol Table"
  val ERROR_TAG_TYPE = MarkerType(
    "org.sireum.pilar.tag.error.symtab",
    None,
    "Pilar Symbol Resolution Error",
    MarkerTagSeverity.Error,
    MarkerTagPriority.Normal,
    Array(MarkerTagKind.Problem, MarkerTagKind.Text))
  val WARNING_TAG_TYPE = MarkerType(
    "org.sireum.pilar.tag.error.symtab",
    None,
    "Pilar Symbol Resolution Warning",
    MarkerTagSeverity.Warning,
    MarkerTagPriority.Normal,
    Array(MarkerTagKind.Problem, MarkerTagKind.Text))

  class ST extends SymbolTable with SymbolTableProducer {
    st =>

    val tables = SymbolTableData()
    val tags = marrayEmpty[LocationTag]
    var hasErrors = false

    def reportError(source : Option[FileResourceUri], line : Int,
                    column : Int, message : String) : Unit = {
      tags += Tag.toTag(source, line, column, message, ERROR_TAG_TYPE)
      hasErrors = true
    }

    def reportWarning(fileUri : Option[String], line : Int,
                      column : Int, message : String) : Unit =
      tags += Tag.toTag(fileUri, line, column, message, WARNING_TAG_TYPE)

    val pdMap = mmapEmpty[ResourceUri, PST]

    def globalVars = tables.globalVarTable.keys
    def globalVar(globalUri : ResourceUri) = tables.globalVarTable(globalUri)

    def procedures = tables.procedureTable.keys

    def procedures(procedureUri : ResourceUri) = tables.procedureTable(procedureUri)

    def procedureSymbolTables = pdMap.values

    def procedureSymbolTable(procedureAbsUri : ResourceUri) : ProcedureSymbolTable =
      procedureSymbolTableProducer(procedureAbsUri)

    def procedureSymbolTableProducer(procedureAbsUri : ResourceUri) = {
      assert(tables.procedureAbsTable.contains(procedureAbsUri))
      pdMap.getOrElseUpdate(procedureAbsUri, new PST(procedureAbsUri))
    }

    class PST(val procedureUri : ResourceUri)
        extends ProcedureSymbolTable with ProcedureSymbolTableProducer {
      val tables = ProcedureSymbolTableData()
      var nextLocTable : CMap[ResourceUri, ResourceUri] = null
      def symbolTable = st
      def symbolTableProducer = st
      def procedure = st.tables.procedureAbsTable(procedureUri)
      def typeVars : ISeq[ResourceUri] = tables.typeVarTable.keys.toSeq
      def params : ISeq[ResourceUri] = tables.params.toSeq
      def isParam(localUri : ResourceUri) = tables.params.contains(localUri)
      def locals : Iterable[ResourceUri] = tables.localVarTable.keys
      def nonParamLocals : Iterable[ResourceUri] = tables.localVarTable.keys.filterNot(isParam)
      def locations =
        tables.bodyTables match {
          case Some(bt) => procedure.body.asInstanceOf[ImplementedBody].locations
          case _        => Seq()
        }
      def typeVar(typeVarUri : ResourceUri) : NameDefinition =
        tables.typeVarTable(typeVarUri)
      def param(paramUri : ResourceUri) : ParamDecl =
        tables.localVarTable(paramUri).asInstanceOf[ParamDecl]
      def local(localUri : ResourceUri) : LocalVarDecl =
        tables.localVarTable(localUri).asInstanceOf[LocalVarDecl]
      def location(locationIndex : Int) = locations(locationIndex)
      def location(locationUri : ResourceUri) =
        tables.bodyTables.get.locationTable(locationUri)
      def catchClauses(locationIndex : Int) : Iterable[CatchClause] =
        tables.bodyTables.get.catchTable.getOrElse(locationIndex,
          Array.empty[CatchClause] : Iterable[CatchClause])
    }

    def toSymbolTable : SymbolTable = this
  }
}
*/ 