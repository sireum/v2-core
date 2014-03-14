/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.symbol

import org.sireum.pilar.ast._
import org.sireum.pilar.symbol._
import org.sireum.pilar.symbol.SymbolTableMessage._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolMiner

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait PackageMiner extends SymbolMiner {
  self : SymbolTableProducer =>

  val packageMiner : VisitorFunction = {
    case pd : PackageDecl =>
      packageSymbol(pd.name)
      false
    case a : Annotation =>
      false
  }

  def packageSymbol(nameDef : Option[NameDefinition]) : Unit =
    nameDef match {
      case Some(nd) => H.symbolInit(nd, H.PACKAGE_TYPE, ivector(nd.name))
      case _        =>
    }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ConstMiner extends SymbolMiner {
  self : SymbolTableProducer =>

  implicit def locPropKey : Property.Key

  val constMiner : VisitorFunction = {
    case pd : PackageDecl =>
      val packageSymDef = pd.name
      pd.elements.foreach {
        case cd : ConstDecl =>
          constDecl(cd, packageSymDef)
        case _ =>
      }
      false
    case cd : ConstDecl =>
      constDecl(cd, None)
      false
    case pe : PackageElement =>
      false
    case a : Annotation =>
      false
  }

  def constDecl(cd : ConstDecl,
                packageSymDef : Option[SymbolDefinition]) {
    constSymbol(cd, packageSymDef)
    val constSymDef = cd.name
    cd.elements.foreach { constElementSymbol(_, constSymDef) }
  }

  def constSymbol(constDecl : ConstDecl,
                  packageSymDef : Option[SymbolDefinition]) = {
    val nameDef = constDecl.name
    H.symbolInit(nameDef, H.CONST_TYPE,
      H.paths(packageSymDef, nameDef.name))

    val key = nameDef.uri
    val cds = mmapGetOrElseUpdateT(tables.constTable,
      key, mlistEmpty[ConstDecl], stringInternFunction)

    cds += constDecl
  }

  def constElementSymbol(constElement : ConstElement,
                         constSymDef : SymbolDefinition) = {
    val nameDef = constElement.name
    H.symbolInit(nameDef, H.CONST_ELEM_TYPE,
      H.paths(constSymDef, nameDef.name))

    val key = nameDef.uri
    val cet = tables.constElementTable
    cet.get(key) match {
      case Some(other) =>
        import LineColumnLocation._
        import FileLocation._
        if (nameDef ? locPropKey)
          reportError(nameDef.fileUriOpt,
            nameDef.line, nameDef.column,
            DUPLICATE_CONST_ELEMENT.format(H.symbolSimpleName(nameDef),
              other.name.line, other.name.column))
        else
          reportError(None, 0, 0,
            DUPLICATE_CONST_ELEMENT.format(H.symbolSimpleName(nameDef),
              0, 0))
      case _ =>
        cet(key.intern) = constElement
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EnumMiner extends SymbolMiner {
  self : SymbolTableProducer =>

  implicit def locPropKey : Property.Key

  val enumMiner : VisitorFunction = {
    case pd : PackageDecl =>
      val packageSymDef = pd.name
      pd.elements.foreach {
        case ed : EnumDecl =>
          enumDecl(ed, packageSymDef)
        case _ =>
      }
      false
    case ed : EnumDecl =>
      enumDecl(ed, None)
      false
    case pe : PackageElement =>
      false
    case a : Annotation =>
      false
  }

  def enumDecl(ed : EnumDecl, packageSymDef : Option[SymbolDefinition]) {
    enumSymbol(ed, packageSymDef)
    val enumSymDef = ed.name
    ed.elements.foreach { enumElementSymbol(_, enumSymDef) }
  }

  def enumSymbol(enumDecl : EnumDecl,
                 packageSymDef : Option[SymbolDefinition]) = {
    val nameDef = enumDecl.name
    H.symbolInit(nameDef, H.ENUM_TYPE,
      H.paths(packageSymDef, nameDef.name))

    val key = nameDef.uri
    val eds = mmapGetOrElseUpdateT(tables.enumTable, key,
      mlistEmpty[EnumDecl], stringInternFunction)

    eds += enumDecl
  }

  def enumElementSymbol(enumElement : EnumElement,
                        enumSymDef : SymbolDefinition) = {
    val nameDef = enumElement.name
    H.symbolInit(nameDef, H.ENUM_ELEM_TYPE,
      H.paths(enumSymDef, nameDef.name))

    val key = nameDef.uri
    val eet = tables.enumElementTable
    eet.get(key) match {
      case Some(other) =>
        import LineColumnLocation._
        import FileLocation._
        if (nameDef ? locPropKey)
          reportError(nameDef.fileUriOpt,
            nameDef.line, nameDef.column,
            DUPLICATE_ENUM_ELEMENT.format(H.symbolSimpleName(nameDef),
              other.name.line, other.name.column))
        else
          reportError(None, 0, 0,
            DUPLICATE_ENUM_ELEMENT.format(H.symbolSimpleName(nameDef),
              0, 0))
      case _ =>
        eet(key.intern) = enumElement
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ExtensionMiner extends SymbolMiner {
  self : SymbolTableProducer =>

  implicit def locPropKey : Property.Key

  val extensionMiner : VisitorFunction = {
    case pd : PackageDecl =>
      val packageSymDef = pd.name
      pd.elements.foreach {
        case ed : ExtensionDecl =>
          extensionDecl(ed, packageSymDef)
        case _ =>
      }
      false
    case ed : ExtensionDecl =>
      extensionDecl(ed, None)
      false
    case pe : PackageElement =>
      false
    case a : Annotation =>
      false
  }

  def extensionDecl(ed : ExtensionDecl,
                    packageSymDef : Option[SymbolDefinition]) {
    extensionSymbol(ed, packageSymDef)
    val extSymDef = ed.name
    ed.elements.foreach { ee =>
      extElementSymbol(ee, extSymDef)
      ee match {
        case ted : TypeExtensionDecl =>
          val tedSymDef = ted.name
          ted.elements.foreach { extElementSymbol(_, tedSymDef) }
        case _ =>
      }
    }
  }

  def extensionSymbol(extensionDecl : ExtensionDecl,
                      packageSymDef : Option[SymbolDefinition]) = {
    val nameDef = extensionDecl.name
    H.symbolInit(nameDef, H.EXTENSION_TYPE,
      H.paths(packageSymDef, nameDef.name))

    val key = nameDef.uri
    val eds = mmapGetOrElseUpdateT(tables.extensionTable, key,
      mlistEmpty[ExtensionDecl], stringInternFunction)

    eds += extensionDecl
  }

  def extElementSymbol(extElement : ExtElement,
                       extOrTypeExtSymDef : SymbolDefinition) = {
    val nameDef = extElement.name
    val rType = extElement match {
      case _ : TypeExtensionDecl => H.TYPE_EXTENSION_TYPE
      case _                     => H.EXTENSION_ELEM_TYPE
    }
    H.symbolInit(nameDef, rType,
      H.paths(extOrTypeExtSymDef, nameDef.name))

    val key = nameDef.uri
    val eet = tables.extensionElementTable
    eet.get(key) match {
      case Some(other) =>
        import LineColumnLocation._
        import FileLocation._
        if (nameDef ? locPropKey)
          reportError(nameDef.fileUriOpt,
            nameDef.line, nameDef.column,
            DUPLICATE_EXTENSION_ELEMENT.format(H.symbolSimpleName(nameDef),
              other.name.line, other.name.column))
        else
          reportError(None, 0, 0,
            DUPLICATE_EXTENSION_ELEMENT.format(H.symbolSimpleName(nameDef),
              0, 0))
      case _ =>
        eet(key.intern) = extElement
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait FunMiner extends SymbolMiner {
  self : SymbolTableProducer =>

  implicit def locPropKey : Property.Key

  val funMiner : VisitorFunction = {
    case pd : PackageDecl =>
      val packageSymDef = pd.name
      pd.elements.foreach {
        case fd : FunDecl => funSymbol(fd, packageSymDef)
        case _            =>
      }
      false
    case fd : FunDecl =>
      funSymbol(fd, None)
      false
    case pe : PackageElement =>
      false
    case a : Annotation =>
      false
  }

  def funSymbol(funDecl : FunDecl,
                packageSymDef : Option[SymbolDefinition]) = {
    val nameDef = funDecl.name
    H.symbolInit(nameDef, H.FUN_TYPE,
      H.paths(packageSymDef, nameDef.name))

    val key = nameDef.uri
    val ft = tables.funTable
    ft.get(key) match {
      case Some(other) =>
        import LineColumnLocation._
        import FileLocation._
        if (nameDef ? locPropKey)
          reportError(nameDef.fileUriOpt,
            nameDef.line, nameDef.column,
            DUPLICATE_FUN.format(H.symbolSimpleName(nameDef),
              other.name.line, other.name.column))
        else
          reportError(None, 0, 0,
            DUPLICATE_FUN.format(H.symbolSimpleName(nameDef),
              0, 0))
      case _ =>
        tables.funTable(key.intern) = funDecl
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait GlobalVarMiner extends SymbolMiner {
  self : SymbolTableProducer =>

  implicit def locPropKey : Property.Key

  val globalVarMiner : VisitorFunction = {
    case pd : PackageDecl =>
      val packageSymDef = pd.name
      pd.elements.foreach {
        case gvd : GlobalVarDecl => globalVarSymbol(gvd, packageSymDef)
        case _                   =>
      }
      false
    case gvd : GlobalVarDecl =>
      globalVarSymbol(gvd, None)
      false
    case pe : PackageElement =>
      false
    case a : Annotation =>
      false
  }

  def globalVarSymbol(globalVarDecl : GlobalVarDecl,
                      packageSymDef : Option[SymbolDefinition]) = {
    val nameDef = globalVarDecl.name
    H.symbolInit(nameDef, H.GLOBAL_VAR_TYPE,
      H.paths(packageSymDef, nameDef.name))

    val key = nameDef.uri
    val gvt = tables.globalVarTable
    gvt.get(key) match {
      case Some(other) =>
        import LineColumnLocation._
        import FileLocation._
        if (nameDef ? locPropKey)
          reportError(nameDef.fileUriOpt, nameDef.line,
            nameDef.column,
            DUPLICATE_GLOBAL_VAR.format(H.symbolSimpleName(nameDef),
              other.name.line, other.name.column))
        else
          reportError(None, 0, 0,
            DUPLICATE_GLOBAL_VAR.format(H.symbolSimpleName(nameDef),
              0, 0))
      case _ =>
        tables.globalVarTable(key.intern) = globalVarDecl
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ProcedureMiner extends SymbolMiner {
  self : SymbolTableProducer =>

  implicit def locPropKey : Property.Key

  val procedureMiner : VisitorFunction = {
    case pd : PackageDecl =>
      val packageSymDef = pd.name
      pd.elements.foreach {
        case pd : ProcedureDecl =>
          procedureSymbol(pd, packageSymDef)
        case _ =>
      }
      false
    case pd : ProcedureDecl =>
      procedureSymbol(pd, None)
      false
    case pe : PackageElement =>
      false
    case a : Annotation =>
      false
  }

  def procedureSymbol(procedureDecl : ProcedureDecl,
                      packageSymDef : Option[SymbolDefinition]) = {
    val nameDef = procedureDecl.name
    H.symbolInit(nameDef, H.PROCEDURE_TYPE,
      H.paths(packageSymDef, nameDef.name))

    val key = nameDef.uri
    val pds = mmapGetOrElseUpdateT(tables.procedureTable, key,
      mlistEmpty[ResourceUri], stringInternFunction)

    import LineColumnLocation._
    if (nameDef ? locPropKey)
      H.symbolInit(nameDef, H.PROCEDURE_TYPE,
        H.paths(packageSymDef, nameDef.name + "/" +
          nameDef.line + "/" + nameDef.column +
          "/" + Integer.toHexString(procedureDecl.withoutBody.
            toString.hashCode)))
    else
      H.symbolInit(nameDef, H.PROCEDURE_TYPE,
        H.paths(packageSymDef, nameDef.name + "/" +
          Integer.toHexString(procedureDecl.withoutBody.
            toString.hashCode)))

    val absKey = nameDef.uri

    val pat = tables.procedureAbsTable
    pat.get(absKey) match {
      case Some(other) =>
        import FileLocation._
        if (nameDef ? locPropKey)
          reportError(nameDef.fileUriOpt, nameDef.line,
            nameDef.column,
            DUPLICATE_PROCEDURE.format(H.symbolSimpleName(nameDef),
              other.name.line, other.name.column))
        else
          reportError(None, 0, 0,
            DUPLICATE_PROCEDURE.format(H.symbolSimpleName(nameDef),
              0, 0))
      case _ =>
        pds += absKey.intern
        tables.procedureAbsTable(absKey.intern) = procedureDecl
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait RecordMiner extends SymbolMiner {
  self : SymbolTableProducer =>

  implicit def locPropKey : Property.Key

  val recordMiner : VisitorFunction = {
    case pd : PackageDecl =>
      val packageSymDef = pd.name
      pd.elements.foreach {
        case rd : RecordDecl =>
          recordDecl(rd, packageSymDef)
        case _ =>
      }
      false
    case rd : RecordDecl =>
      recordDecl(rd, None)
      false
    case pe : PackageElement =>
      false
    case a : Annotation =>
      false
  }

  def recordDecl(rd : RecordDecl,
                 packageSymDef : Option[SymbolDefinition]) {
    recordSymbol(rd, packageSymDef)
    val recordSymDef = rd.name
    rd.typeVars.foreach { tv =>
      H.typeVarSymbol(tables.typeVarTable, self, tv._1, recordSymDef)
    }
    rd.attributes.foreach {
      recordAttributeSymbol(_, recordSymDef)
    }
  }

  def recordAttributeSymbol(attribute : AttributeDecl,
                            recordSymDef : SymbolDefinition) = {
    val nameDef = attribute.name
    H.symbolInit(nameDef, H.RECORD_TYPE,
      H.paths(recordSymDef, nameDef.name))

    val key = nameDef.uri
    val at = tables.attributeTable
    at.get(key) match {
      case Some(other) =>
        import LineColumnLocation._
        import FileLocation._
        if (nameDef ? locPropKey)
          reportError(nameDef.fileUriOpt,
            nameDef.line, nameDef.column,
            DUPLICATE_ATTRIBUTE.format(H.symbolSimpleName(nameDef),
              other.name.line, other.name.column))
        else
          reportError(None, 0, 0,
            DUPLICATE_ATTRIBUTE.format(H.symbolSimpleName(nameDef),
              0, 0))
      case _ =>
        at(key.intern) = attribute
    }
  }

  def recordSymbol(recordDecl : RecordDecl,
                   packageSymDef : Option[SymbolDefinition]) = {
    val nameDef = recordDecl.name
    H.symbolInit(nameDef, H.RECORD_TYPE,
      H.paths(packageSymDef, nameDef.name))

    val key = nameDef.uri
    val rt = tables.recordTable
    rt.get(key) match {
      case Some(other) =>
        import LineColumnLocation._
        import FileLocation._
        if (nameDef ? locPropKey)
          reportError(nameDef.fileUriOpt,
            nameDef.line, nameDef.column,
            DUPLICATE_RECORD.format(H.symbolSimpleName(nameDef),
              other.name.line, other.name.column))
        else
          reportError(None, 0, 0,
            DUPLICATE_RECORD.format(H.symbolSimpleName(nameDef),
              0, 0))

      case _ =>
        rt(key.intern) = recordDecl
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait TypeAliasMiner extends SymbolMiner {
  self : SymbolTableProducer =>

  implicit def locPropKey : Property.Key

  val typeAliasMiner : VisitorFunction = {
    case pd : PackageDecl =>
      val packageSymDef = pd.name
      pd.elements.foreach {
        case tad : TypeAliasDecl => typeAliasSymbol(tad, packageSymDef)
        case _                   =>
      }
      false
    case pe : PackageElement =>
      false
    case a : Annotation =>
      false
  }

  def typeAliasSymbol(typeAliasDecl : TypeAliasDecl,
                      packageSymDef : Option[SymbolDefinition]) = {
    val nameDef = typeAliasDecl.name
    H.symbolInit(nameDef, H.TYPE_ALIAS_TYPE,
      H.paths(packageSymDef, nameDef.name))

    val key = nameDef.uri
    val tat = tables.typeAliasTable
    tat.get(key) match {
      case Some(other) =>
        import LineColumnLocation._
        import FileLocation._
        if (nameDef ? locPropKey)
          reportError(nameDef.fileUriOpt,
            nameDef.line, nameDef.column,
            DUPLICATE_TYPE_ALIAS.format(H.symbolSimpleName(nameDef),
              other.name.line, other.name.column))
        else
          reportError(None, 0, 0,
            DUPLICATE_TYPE_ALIAS.format(H.symbolSimpleName(nameDef),
              0, 0))
      case _ =>
        tat(key.intern) = typeAliasDecl
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait VsetMiner extends SymbolMiner {
  self : SymbolTableProducer =>

  val vsetMiner : VisitorFunction = {
    case pd : PackageDecl =>
      val packageSymDef = pd.name
      pd.elements.foreach {
        case vd : VSetDecl =>
          vsetSymbol(vd, packageSymDef)
        case _ =>
      }
      false
    case pe : PackageElement =>
      false
    case a : Annotation =>
      false
  }

  def vsetSymbol(vsetDecl : VSetDecl,
                 packageSymDef : Option[SymbolDefinition]) = {
    val nameDef = vsetDecl.name
    H.symbolInit(nameDef, H.VSET_TYPE,
      H.paths(packageSymDef, nameDef.name))

    val key = nameDef.uri
    val vt = tables.vsetTable
    vt.get(key) match {
      case Some(other) =>
        import LineColumnLocation._
        import FileLocation._
        reportError(nameDef.fileUriOpt,
          nameDef.line, nameDef.column,
          DUPLICATE_VSET.format(H.symbolSimpleName(nameDef),
            other.name.line, other.name.column))
      case _ =>
        vt(key.intern) = vsetDecl
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ProcedureSymbolMiner extends SymbolMiner {
  self : ProcedureSymbolTableProducer =>

  implicit def locPropKey : Property.Key

  val procedureHeaderMiner : VisitorFunction = {
    case pd : ProcedureDecl =>
      val procedureSymDef = pd.name
      pd.typeVars.foreach { tv =>
        H.typeVarSymbol(tables.typeVarTable, self.symbolTableProducer,
          tv._1, procedureSymDef)
      }
      val visitor = Visitor.build {
        case p : ParamDecl =>
          localVarSymbol(p, procedureSymDef, true)
          false
      }
      pd.params.foreach { visitor(_) }
      false
  }

  val procedureBodyMiner : VisitorFunction = {
    case pd : ProcedureDecl =>
      val procedureSymDef = pd.name
      pd.body match {
        case ib : ImplementedBody =>
          var index = 0
          ib.locations.foreach { l =>
            locationSymbol(l, index)
            index += 1
          }
          ib.locals.foreach { localVarSymbol(_, procedureSymDef, false) }
        case _ =>
      }
      false
  }

  def localVarSymbol(localVar : LocalVar,
                     procedureSymDef : SymbolDefinition, isParam : Boolean) = {
    val nameDef = localVar.name
    H.symbolInit(nameDef, H.LOCAL_VAR_TYPE,
      H.paths(procedureSymDef, nameDef.name))

    val key = nameDef.uri
    val lvt = tables.localVarTable
    val params = tables.params
    lvt.get(key) match {
      case Some(other) =>
        val msg = if (isParam) DUPLICATE_PARAM else DUPLICATE_LOCAL_VAR
        import LineColumnLocation._
        import FileLocation._
        if (nameDef ? locPropKey)
          symbolTableProducer.reportError(nameDef.fileUriOpt,
            nameDef.line, nameDef.column,
            msg.format(H.symbolSimpleName(nameDef),
              other.name.line, other.name.column))
        else
          symbolTableProducer.reportError(None, 0, 0,
            msg.format(H.symbolSimpleName(nameDef), 0, 0))
      case _ =>
        lvt(key.intern) = localVar
        if (isParam)
          params += nameDef.uri
    }
  }

  def locationSymbol(loc : LocationDecl, index : Int) = {
    loc.name match {
      case Some(nameDef) =>
        H.symbolInit(nameDef, H.LOCATION_TYPE, ivector(nameDef.name), true)
        val key = nameDef.uri
        val lt = tables.bodyTables.get.locationTable
        lt.get(key) match {
          case Some(other) =>
            val otherSymDef = other.name.get
            import LineColumnLocation._
            import FileLocation._
            if (nameDef ? locPropKey)
              symbolTableProducer.reportError(nameDef.fileUriOpt,
                nameDef.line, nameDef.column,
                DUPLICATE_LOCATION.format(nameDef.name,
                  otherSymDef.line, otherSymDef.column))
            else
              symbolTableProducer.reportError(None, 0, 0,
                DUPLICATE_LOCATION.format(nameDef.name, 0, 0))
          case _ =>
            lt(key.intern) = loc
            loc.index(index)
            H.computeLocationDescriptor(loc, Some(nameDef.uri), index)
        }
      case None =>
        loc.index(index)
        H.computeLocationDescriptor(loc, None, index)
    }
  }
}