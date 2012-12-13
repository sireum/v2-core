/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.symbol

import scala.collection.GenSeq
import scala.collection.mutable.WrappedArray
import org.sireum.pilar.ast._
import org.sireum.pilar.symbol.SymbolTableMessage._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object SymbolTableMessage {

  val DUPLICATE_CONST_ELEMENT = // 
    "Const element '%s' has already been defined at [%d, %d]"

  val DUPLICATE_ENUM_ELEMENT = // 
    "Enum element '%s' has already been defined at [%d, %d]"

  val DUPLICATE_EXTENSION_ELEMENT = // 
    "Extension element '%s' has already been defined at [%d, %d]"

  val DUPLICATE_FUN = // 
    "Fun '%s' has already been defined at [%d, %d]"

  val DUPLICATE_GLOBAL_VAR = // 
    "Global variable '%s' has already been defined at [%d, %d]"

  val DUPLICATE_PROCEDURE = // 
    "Procedure '%s' with similar signature has already been defined at [%d, %d]"

  val DUPLICATE_RECORD = // 
    "Attribute '%s' has already been defined at [%d, %d]"

  val DUPLICATE_ATTRIBUTE = // 
    "Attribute '%s' has already been defined at [%d, %d]"

  val DUPLICATE_TYPE_VAR = // 
    "Type variable '%s' has already been defined at [%d, %d]"

  val DUPLICATE_TYPE_ALIAS = // 
    "Type alias '%s' has already been defined at [%d, %d]"

  val DUPLICATE_VSET = // 
    "Virtual procedure set '%s' has already been defined at [%d, %d]"

  val DUPLICATE_PARAM = // 
    "Parameter '%s' has already been defined at [%d, %d]"

  val DUPLICATE_LOCAL_VAR = // 
    "Local variable '%s' has already been defined at [%d, %d]"

  val DUPLICATE_LOCATION = // 
    "Location '%s' has already been defined at [%d, %d]"

  val DUPLICATE_ANON_PARAM = // 
    "Anonymous function parameter '%s' has already been defined at [%d, %d]"

  val NOT_FOUND_LOCATION = // 
    "Location '%s' was not declared in procedure '%s'"

  val NOT_FOUND_TYPE_VAR = // 
    "Type variable '%s' was not declared in %s"

  val NOT_FOUND_EXTEND_RECORD = // 
    "Record extension to '%s' could not be resolved"

  val CIRCULAR_RECORD = // 
    "Circular record extension on '%s'"

  val LAST_LOCATION_NEED_EXPLICIT_JUMP = //
    "Last location in a procedure has to have an explicitly defined jump"

  val CATCH_TABLE_END_BEFORE_START = //
    "Ill-formed catch clause due to region end '%s' appear before '%s' at [%d, %d]"

  val OF_FILE = " of %s"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object H {
  val SCHEME = "pilar"
  val DEFAULT_PACKAGE_PATH = "default"

  val PACKAGE_TYPE = "package"
  def isPackage(r : Symbol) = r.uriType == PACKAGE_TYPE

  val CONST_TYPE = "const"
  def isConst(r : Symbol) = r.uriType == CONST_TYPE
  val CONST_ELEM_TYPE = "constelem"
  def isConstElem(r : Symbol) = r.uriType == CONST_ELEM_TYPE

  val ENUM_TYPE = "enum"
  def isEnum(r : Symbol) = r.uriType == ENUM_TYPE
  val ENUM_ELEM_TYPE = "enumelem"
  def isEnumElem(r : Symbol) = r.uriType == ENUM_ELEM_TYPE

  val EXTENSION_TYPE = "extension"
  def isExtension(r : Symbol) = r.uriType == EXTENSION_TYPE
  val TYPE_EXTENSION_TYPE = "typeext"
  def isTypeExtension(r : Symbol) = r.uriType == TYPE_EXTENSION_TYPE
  val EXTENSION_ELEM_TYPE = "extelem"
  def isExtensionElem(r : Symbol) = r.uriType == EXTENSION_ELEM_TYPE

  val FUN_TYPE = "fun"
  def isFun(r : Symbol) = r.uriType == FUN_TYPE

  val GLOBAL_VAR_TYPE = "globalvar"
  def isGlobalVar(r : Symbol) = r.uriType == GLOBAL_VAR_TYPE

  val PROCEDURE_TYPE = "procedure"
  def isProcedure(r : Symbol) = r.uriType == PROCEDURE_TYPE

  val RECORD_TYPE = "record"
  def isRecord(r : Symbol) = r.uriType == RECORD_TYPE

  val ATTRIBUTE_TYPE = "attribute"
  def isAttribute(r : Symbol) = r.uriType == ATTRIBUTE_TYPE

  val TYPE_ALIAS_TYPE = "typealias"
  def isTypeAlias(r : Symbol) = r.uriType == TYPE_ALIAS_TYPE

  val VSET_TYPE = "vset"
  def isVSet(r : Symbol) = r.uriType == VSET_TYPE

  val TYPE_VAR_TYPE = "typevar"
  def isTypeVar(r : Symbol) = r.uriType == TYPE_VAR_TYPE

  val LOCAL_VAR_TYPE = "localvar"
  def isLocalVar(r : Symbol) = r.uriType == LOCAL_VAR_TYPE

  val LOCATION_TYPE = "location"
  def isLocation(r : Symbol) = r.uriType == LOCATION_TYPE

  val ANON_LOCAL_TYPE = "anonlocal"
  def isAnonLocal(r : Symbol) = r.uriType == ANON_LOCAL_TYPE

  val NON_SCHEME_OFFSET = (SCHEME + ":/").length

  def isGlobalVar(symUri : ResourceUri) = {
    var i = symUri.lastIndexOf("/")

    if (i < 0) i = 0 else i = i + 1

    if (i + 1 < symUri.length)
      symUri.charAt(i) == '@' && symUri.charAt(i + 1) == '@'
    else
      false
  }

  def isConstElement(symUri : ResourceUri) = matchType(symUri, CONST_ELEM_TYPE)

  def isEnumElement(symUri : ResourceUri) = matchType(symUri, ENUM_ELEM_TYPE)

  def isConstOrEnumElement(symUri : ResourceUri) =
    isConstElement(symUri) || isEnumElement(symUri)

  def matchType(symUri : ResourceUri, typ : String) : Boolean = {
    val len = typ.length
    for (i <- 0 until len)
      if (symUri.charAt(NON_SCHEME_OFFSET + i) != typ.charAt(i))
        return false
    return true
  }

  def packageName(symDef : Option[SymbolDefinition]) =
    symDef match {
      case Some(sd) =>
        sd.uriPaths(0)
      case _ =>
        H.DEFAULT_PACKAGE_PATH
    }

  def packageName(symDef : SymbolDefinition) = symDef.uriPaths(0)

  def packagePath(symDef : Option[SymbolDefinition],
                  name : String) =
    ilist(packageName(symDef), name);

  def paths(symDef : SymbolDefinition, name : String) =
    symDef.uriPaths :+ name

  def symbolInit(s : Symbol, typ : String, paths : ISeq[String], relative : Boolean = false) =
    Resource.initResource(s, SCHEME, typ, paths, relative)

  def symbolInit(s : Symbol, typ : String, paths : ISeq[String], uri : String) =
    Resource.initResource(s, SCHEME, typ, paths, uri)

  def symbolSimpleName(s : Symbol) =
    s.uriPaths.last

  def symbolUri(typ : String, paths : ISeq[String], relative : Boolean = false) =
    Resource.getResourceUri(SCHEME, typ, paths.map { _.replaceAll(org.sireum.pilar.PILAR_PACKAGE_SEP, "/") }, relative)

  def typeVarSymbol(tvt : MMap[ResourceUri, NameDefinition], str : SymbolTableReporter,
                    nameDef : NameDefinition, symDef : SymbolDefinition) = {
    H.symbolInit(nameDef, H.TYPE_VAR_TYPE,
      H.paths(symDef, nameDef.name))

    val key = nameDef.uri
    tvt.get(key) match {
      case Some(other) =>
        import LineColumnLocation._
        import FileLocation._
        str.reportError(nameDef.fileUriOpt,
          nameDef.line, nameDef.column,
          DUPLICATE_TYPE_VAR.format(H.symbolSimpleName(nameDef),
            other.line, other.column))
      case _ =>
        tvt(key.intern) = nameDef
    }
  }

  def buildTypeVarMap(tvs : ISeq[(NameDefinition, ISeq[Annotation])]) = {
    val tvt = mmapEmpty[String, NameDefinition]
    tvs.foreach { tv =>
      val nameDef = tv._1
      tvt(H.symbolSimpleName(nameDef)) = nameDef
    }
    tvt
  }

  def resolveTypeVar(source : Option[FileResourceUri],
                     str : SymbolTableReporter, typeVarR : NameUser,
                     tvt : MMap[String, NameDefinition],
                     name : String) =
    tvt.get(typeVarR.name) match {
      case Some(nameDef) =>
        H.symbolInit(typeVarR, H.TYPE_VAR_TYPE,
          nameDef.uriPaths, nameDef.uri)
      case _ =>
        import LineColumnLocation._
        import FileLocation._
        str.reportError(source,
          typeVarR.line, typeVarR.column,
          NOT_FOUND_TYPE_VAR.format(typeVarR.name, name))
    }

  val EMPTY_DEPENDENCY = mmapEmpty[String, MSet[String]]

  abstract class StpWrapper(stp : SymbolTableProducer) extends SymbolTableProducer {
    def tables : SymbolTableData = stp.tables

    def procedureSymbolTableProducer //
    (procedureUri : ResourceUri) : ProcedureSymbolTableProducer =
      stp.procedureSymbolTableProducer(procedureUri)

    def toSymbolTable : SymbolTable = stp.toSymbolTable

    def reportError(fileUri : Option[String], line : Int,
                    column : Int, message : String) : Unit =
      stp.reportError(fileUri, line, column, message)

    def reportWarning(fileUri : Option[String], line : Int,
                      column : Int, message : String) : Unit =
      stp.reportWarning(fileUri, line, column, message)
  }

  class PackageElementMiner(stp : SymbolTableProducer)
      extends StpWrapper(stp)
      with PackageMiner with ConstMiner with EnumMiner with ExtensionMiner
      with FunMiner with GlobalVarMiner with ProcedureMiner with RecordMiner
      with TypeAliasMiner with VsetMiner {

    val packageElementMiner =
      Visitor.build(Visitor.map(
        ilist(packageMiner, constMiner, enumMiner,
          extensionMiner, funMiner, globalVarMiner,
          procedureMiner, recordMiner, typeAliasMiner,
          vsetMiner), false))
  }

  class ProcedureMinerResolver(pstp : ProcedureSymbolTableProducer)
      extends ProcedureSymbolTableProducer with ProcedureSymbolMiner
      with ProcedureSymbolResolver with JumpResolver {
    override def dependency = EMPTY_DEPENDENCY

    def tables : ProcedureSymbolTableData = pstp.tables

    def symbolTableProducer() : SymbolTableProducer = pstp.symbolTableProducer

    val procMiner =
      Visitor.build(Visitor.map(ilist(procedureHeaderMiner,
        procedureBodyMiner), false))

    val procResolver = Visitor.build(Visitor.map(ilist(
      procedureHeaderResolver, procedureBodyResolver,
      jumpResolver), false))
  }

  class PackageElementResolver(stp : SymbolTableProducer)
      extends StpWrapper(stp)
      with ConstResolver with EnumResolver with ExtensionResolver
      with FunResolver with GlobalVarResolver with ProcedureResolver
      with RecordResolver with TypeAliasResolver with VsetResolver
      with FunParamResolver {

    override def dependency = mmapEmpty[String, MSet[String]]

    val packageElementResolver =
      Visitor.buildEnd(
        Visitor.map(ilist(constResolver, enumResolver, extensionResolver,
          funResolver, globalVarResolver, procedureResolver,
          recordResolver, typeAliasResolver, vsetResolver,
          funParamResolver), false),
        Visitor.map(ilist(funParamResolverEnd), false))
  }

  def tearDown(tables : SymbolTableData, m : Model) = {
    val fileUri = m.sourceURI.get
    val declaredSymbols = tables.declaredSymbols(fileUri)
    tables.declaredSymbols -= fileUri
    tables.constTable --= declaredSymbols
    tables.constElementTable --= declaredSymbols
    tables.enumTable --= declaredSymbols
    tables.enumElementTable --= declaredSymbols
    tables.extensionTable --= declaredSymbols
    tables.extensionElementTable --= declaredSymbols
    tables.funTable --= declaredSymbols
    tables.globalVarTable --= declaredSymbols
    tables.procedureTable --= declaredSymbols
    tables.procedureAbsTable --= declaredSymbols
    tables.recordTable --= declaredSymbols
    tables.attributeTable --= declaredSymbols
    tables.typeVarTable --= declaredSymbols
    tables.typeAliasTable --= declaredSymbols
    tables.vsetTable --= declaredSymbols
    tables.dependency -= fileUri
    tables.dependency.values.foreach { s =>
      s -= fileUri
    }
  }

  def combineTable[T](m1 : MMap[String, MBuffer[T]],
                      m2 : MMap[String, MBuffer[T]]) =
    m2.foreach { p =>
      val key = p._1
      val value2 = p._2
      val value1 = m1.getOrElseUpdate(key, value2)
      if (value1 ne p._2) value1 ++= value2
    }

  def combineTable[T](str : SymbolTableReporter,
                      m1 : MMap[String, T],
                      m2 : MMap[String, T],
                      f : T => NameDefinition,
                      message : String) =
    m2.foreach { p =>
      val key = p._1
      val value = p._2
      m1.get(key) match {
        case Some(ce) =>
          val ceName = f(ce)
          val otherName = f(value)
          import LineColumnLocation._
          import FileLocation._
          str.reportError(otherName.fileUriOpt,
            otherName.line, otherName.column,
            message.format(otherName.name, ceName.line,
              ceName.column, ceName.fileUriOpt))
        case _ =>
          m1(key) = value
      }
    }

  def combine(stp1 : SymbolTableProducer,
              stp2 : SymbolTableProducer) : SymbolTableProducer = {
    val tables1 = stp1.tables
    val tables2 = stp2.tables
    val str = stp1

    combineMap(tables1.declaredSymbols, tables2.declaredSymbols)

    combineTable(tables1.constTable, tables2.constTable)

    combineTable(str, tables1.constElementTable, tables2.constElementTable,
      { x : ConstElement => x.name }, DUPLICATE_CONST_ELEMENT + OF_FILE)

    combineTable(tables1.enumTable, tables2.enumTable)

    combineTable(str, tables1.enumElementTable, tables2.enumElementTable,
      { x : EnumElement => x.name }, DUPLICATE_ENUM_ELEMENT + OF_FILE)

    combineTable(tables1.extensionTable, tables2.extensionTable)

    combineTable(str, tables1.extensionElementTable, tables2.extensionElementTable,
      { x : ExtElement => x.name }, DUPLICATE_EXTENSION_ELEMENT + OF_FILE)

    combineTable(str, tables1.funTable, tables2.funTable,
      { x : FunDecl => x.name }, DUPLICATE_FUN + OF_FILE)

    combineTable(str, tables1.globalVarTable, tables2.globalVarTable,
      { x : GlobalVarDecl => x.name }, DUPLICATE_GLOBAL_VAR + OF_FILE)

    combineTable(tables1.procedureTable, tables2.procedureTable)

    combineTable(str, tables1.procedureAbsTable, tables2.procedureAbsTable,
      { x : ProcedureDecl => x.name }, DUPLICATE_PROCEDURE + OF_FILE)

    combineTable(str, tables1.recordTable, tables2.recordTable,
      { x : RecordDecl => x.name }, DUPLICATE_RECORD + OF_FILE)

    combineTable(str, tables1.attributeTable, tables2.attributeTable,
      { x : AttributeDecl => x.name }, DUPLICATE_ATTRIBUTE + OF_FILE)

    combineTable(str, tables1.typeVarTable, tables2.typeVarTable,
      { x : NameDefinition => x }, DUPLICATE_TYPE_VAR + OF_FILE)

    combineTable(str, tables1.typeAliasTable, tables2.typeAliasTable,
      { x : TypeAliasDecl => x.name }, DUPLICATE_TYPE_ALIAS + OF_FILE)

    combineTable(str, tables1.vsetTable, tables2.vsetTable,
      { x : VSetDecl => x.name }, DUPLICATE_VSET + OF_FILE)

    stp1
  }

  def combineMap[T](m1 : MMap[String, MSet[T]],
                    m2 : MMap[String, MSet[T]]) = {
    m2.foreach { p =>
      val key = p._1
      val value2 = p._2
      val value1 = m1.getOrElseUpdate(key, value2)
      if (value1 ne p._2) value1 ++= value2
    }

    m1
  }

  def mapCalls[T](st : SymbolTable,
                  j : CallJump,
                  filterFunction : CallJump => (ResourceUri => Boolean),
                  mapFunction : ProcedureSymbolTable => T) =
    (j.callExp.exp : @unchecked) match {
      case ne : NameExp =>
        st.procedures(ne.name.uri). //
          filter(filterFunction(j)).
          map { st.procedureSymbolTable(_) }.map { mapFunction(_) }
    }

  def computeLocationDescriptor(l : LocationDecl, uri : Option[ResourceUri], index : Int) {
    l match {
      case l : ActionLocation =>
        l.action.commandDescriptorInfo(uri, index, 0, 0)
      case l : JumpLocation =>
        l.jump.commandDescriptorInfo(uri, index, 0, 0)
      case l : ComplexLocation =>
        var transIndex = 0
        for (t <- l.transformations) {
          var commandIndex = 0
          for (a <- t.actions) {
            a.commandDescriptorInfo(uri, index, transIndex, commandIndex)
            commandIndex = commandIndex + 1
          }
          if (t.jump.isDefined)
            t.jump.get.commandDescriptorInfo(uri, index, transIndex, commandIndex)
          transIndex += 1
        }
      case l : EmptyLocation =>
    }
  }
}
