/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.parser

import org.antlr.runtime.tree.Tree

import org.apache.commons.lang3.StringEscapeUtils

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.WrappedArray
import scala.collection._

import org.sireum.pilar._
import org.sireum.pilar.ast._
import org.sireum.pilar.ast.PilarAstUtil._
import org.sireum.pilar.parser.PilarParser.ErrorReporter
import org.sireum.util._
import org.sireum.util.math._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
protected final class ParserVisitorContext {
  var result : Option[Any] = None
  var varArity : Boolean = false
  var reporter : ErrorReporter = null
  var typeSpec : Option[TypeSpec] = None
  var source : Option[FileResourceUri] = None
  var lineOffset : Int = 0

  def noResult() : Boolean = {
    return result eq None
  }

  def pushResult(r : Any) : Unit = {
    assert(noResult)
    result = Some(r)
  }

  def pushResult(r : PilarAstNode, t : Tree) : Unit = {
    assert(noResult)

    r.setLocationLineColumn(lineOffset + t.getLine, t.getCharPositionInLine)

    pushResult(r)
  }

  def popResult[T]() : T = {
    assert(!noResult)
    var r = result
    result = None
    return r match {
      case Some(t : T) => t
      case _           => sys.error("unexpected")
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
protected final class ParserVisitor(context : ParserVisitorContext)
    extends TreeVisitor[ParserVisitorContext](context) {
  val tt2pair = { x : TypeVarSpec =>
    new Pair(getNameDefinition(Id(x.name.name, x.name.locationLine,
      x.name.locationColumn)), x.annotations)
  }
  val tto2pair = { x : Option[ISeq[TypeVarSpec]] =>
    x match {
      case None                          => ilistEmpty[(NameDefinition, ISeq[Annotation])]
      case Some(tt : ISeq[TypeVarSpec]) => tt.map(tt2pair)
    }
  }
  val e2maf = { x : Any =>
    x match {
      case e : Exp =>
        val r = ExpMultiArrayFragment(e)
        r.setLocationLineColumn(e.locationLine, e.locationColumn)
        r
      case maf : MultiArrayFragment =>
        maf
    }
  }

  def getChildren[T](index : Int, t : Tree) : ISeq[T] = {
    var result = ilistEmpty[T]
    val list = t.getChild(index)
    if (list == null) {
      return ilistEmpty
    }
    val count = list.getChildCount
    for (i <- 0 until count) {
      visit(list.getChild(i))
      result = context.popResult[T] :: result
    }
    return result.reverse
  }

  def getChildrenBox[T](index : Int, t : Tree,
                        f : Any => T) : ISeq[T] = {
    var result = ilistEmpty[T]
    val list = t.getChild(index)
    if (list == null) {
      return ilistEmpty
    }
    val count = list.getChildCount
    for (i <- 0 until count) {
      visit(list.getChild(i))
      result = f(context.popResult[T]) :: result
    }
    return result.reverse
  }

  def getChildrenFlat[T](index : Int, t : Tree) : ISeq[T] = {
    var result = ilistEmpty[T]
    val list = t.getChild(index)
    if (list == null) {
      return ilistEmpty
    }
    val count = list.getChildCount
    for (i <- 0 until count) {
      visit(list.getChild(i))
      val v = context.popResult[Any] match {
        case l : ISeq[T] => l.foreach { x => result = x :: result }
        case e : T        => result = e :: result
        case _            => sys.error("unexpected case")
      }
    }
    return result.reverse
  }

  def getChild[T](index : Int, t : Tree) : T = {
    visit(t.getChild(index))
    return context.popResult[T]
  }

  def hasNonEmptyChild(index : Int, t : Tree) : Boolean = {
    return t.getChild(index).getChildCount > 0
  }

  def getOptChild[T](n : Int, t : Tree) : Option[T] =
    if (t.getChild(n).getChildCount > 0)
      Some(getChild[T](n, t))
    else
      None

  override def visitACCESS(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp (accessed): 0
    val exp = getChild[Exp](n, t)
    n += 1

    // id (element): 0
    val name = getChild[Id](n, t)
    n += 1

    val result = AccessExp(exp, getNameUser(name))
    context.pushResult(result, t)

    return false
  }

  override def visitACTION_EXT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // optional type var tuple: 0
    val typeVars = tto2pair(getOptChild[ISeq[TypeVarSpec]](n, t))
    n += 1

    // id: 1
    val id = getChild[Id](n, t)
    n += 1

    // optional ext param list: 2
    val params = getChildren[ExtParam](0, t.getChild(n))
    n += 1

    // annotation list: 3
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ActionExtensionDecl(typeVars,
      getNameDefinition(id),
      annList,
      params,
      context.varArity)
    context.pushResult(result, t)
    context.varArity = false
    return false
  }

  override def visitACTION_EXT_CALL(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val callExp = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    if (!callExp.isInstanceOf[CallExp]) {
      context.reporter.report(context.source, callExp.locationLine,
        callExp.locationColumn,
        "Expecting a call expression instead of a general one")
      CallExp(callExp, LiteralExp(LiteralType.NULL, null, "null"))
    } else {
      val result = ExtCallAction(annList, callExp.asInstanceOf[CallExp])
      context.pushResult(result, t)
    }
    return false
  }

  override def visitANN_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val exp = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    exp.annotations = annList
    context.pushResult(exp)
    return false
  }

  override def visitANNOTATED_TYPE(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // type: 0
    val typeSpec = getChild[TypeSpec](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    typeSpec.annotations = annList
    context.pushResult(typeSpec)
    return false
  }

  override def visitANNOTATION(t : Tree) : Boolean = {
    assert(context.noResult)

    // location info
    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // optional params: 1
    val params = getChildrenBox[AnnotationParam](0, t.getChild(n),
      { x : Any =>
        x match {
          case ap : AnnotationParam => ap
          case e : Exp              => ExpAnnotationParam(None, e)
          case _                    => sys.error("unexpected case")
        }
      })
    n += 1

    val result = Annotation(getNameUser(id), params)
    context.pushResult(result, t)
    return false
  }

  override def visitANNOTATION_PARAM_IDED(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // optional id: 0
    val s = getOptChild[Id](n, t).map(getNameUser)
    n += 1

    // annotation or exp: 1
    {
      getChild[PilarAstNode](n, t) match {
        case e : Exp =>
          context.pushResult(ExpAnnotationParam(s, e), t)
        case a : Annotation =>
          context.pushResult(AnnotationAnnotationParam(s, a), t)
        case _ => sys.error("unexpected case")
      }
    }
    n += 1

    return false
  }

  override def visitARRAY_FRAGMENT(t : Tree) : Boolean = {
    val result =
      if (!context.noResult)
        ArrayTypeSpec(context.popResult[TypeSpec])
      else
        ArrayTypeFragment()
    context.pushResult(result, t)
    return false
  }

  override def visitASSERT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val exp = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = AssertAction(annList, exp, None)
    context.pushResult(result, t)

    return false
  }

  override def visitASSIGN(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // lhs list: 0
    val lhss = getChildren[Exp](n, t)
    n += 1

    // op: 1
    val op = t.getChild(n).getText;
    n += 1

    // rhs list: 2
    var rhss = getChildren[Exp](n, t)
    n += 1

    val lhssSize = lhss.length
    val rhssSize = rhss.length

    // annotation list: 3
    val annList = getChildren[Annotation](n, t)
    n += 1

    var result : AssignAction = null

    (lhssSize, rhssSize) match {
      case (1, 1) =>
        result = AssignAction(annList, lhss(0), op, rhss(0))
      case (1, _) =>
        result = AssignAction(annList, lhss(0), op, TupleExp(rhss))
      case (_, 1) =>
        val lhs = TupleExp(lhss)
        result = AssignAction(annList, lhs, op, rhss(0))
      case _ =>
        if (lhssSize != rhssSize) {
          context.reporter.report(context.source,
            t.getLine, t.getCharPositionInLine,
            "Wrong numbers of LHSs and RHSs in parallel assignment: "
              + lhssSize + " != " + rhssSize)
        }
        val lhs = TupleExp(lhss)
        val rhs = TupleExp(rhss)
        result = AssignAction(annList, lhs, op, rhs)
    }

    context.pushResult(result, t)
    return false
  }

  override def visitASSUME(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val exp = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = AssumeAction(annList, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitATTR_INIT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // exp: 1
    val exp = getChild[Exp](n, t)
    n += 1

    val result = AttributeInit(getNameUser(id), exp)
    context.pushResult(result, t)
    return false
  }

  override def visitATTRIBUTE(t : Tree) : Boolean = {
    assert(context.noResult && context.typeSpec == None)

    var n = 0

    // optional type: 0
    context.typeSpec = getOptChild[TypeSpec](n, t)
    n += 1

    // attribute fragment list: 1
    val result = getChildren[AttributeDecl](n, t)
    n += 1

    context.pushResult(result)
    context.typeSpec = None
    return false
  }

  override def visitATTRIBUTE_FRAGMENT(
    t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // optional name: 2
    var bindingName = getOptChild[Id](n, t).map(getNameUser)
    n += 1

    val result =
      AttributeDecl(getNameDefinition(id), annList,
        context.typeSpec, bindingName)
    context.pushResult(result)
    return false
  }

  override def visitBINARY(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // op: 0
    val op = t.getChild(n).getText;
    n += 1

    // exp (left): 1
    val left = getChild[Exp](n, t)
    n += 1

    // exp (right): 2
    val right = getChild[Exp](n, t)
    n += 1

    val result = BinaryExp(op, left, right)
    context.pushResult(result, t)
    return false
  }

  override def visitBODY(t : Tree) : Boolean = {
    assert(context.noResult)

    // location info
    val line = t.getLine
    val column = t.getCharPositionInLine

    var n = 0

    // optional local variable list: 0
    val localVars = getChildrenFlat[LocalVarDecl](0, t.getChild(n))
    n += 1

    // location list: 1
    val locations = getChildren[LocationDecl](n, t)
    n += 1

    // catch clause list: 2
    val catchClauses = getChildren[CatchClause](n, t)
    n += 1

    val result = ImplementedBody(localVars, locations, catchClauses)
    context.pushResult(result, t)
    return false
  }

  override def visitCALL(t : Tree) : Boolean = {
    assert(context.noResult)

    // location info
    var n = 0

    // exp: 0
    val exp = getChild[Exp](n, t)
    n += 1

    // exp (argument): 1
    val arg = getChild[Exp](n, t)
    n += 1

    val result = CallExp(exp, arg)
    context.pushResult(result, t)
    return false
  }

  override def visitCALL_JUMP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // optional name exp: 0
    val lhs = getOptChild[NameExp](n, t)
    n += 1

    // exp: 1
    val e = getChild[Exp](n, t)
    val exp =
      if (e.isInstanceOf[CallExp]) e.asInstanceOf[CallExp]
      else {
        context.reporter.report(context.source, e.locationLine,
          e.locationColumn,
          "Expecting a call expression instead of a general one")
        CallExp(e, LiteralExp(LiteralType.NULL, null, "null"))
      }
    n += 1

    // optional jump: 2
    val jump = getOptChild[GotoJump](n, t)
    n += 1

    // annotation list: 3
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = CallJump(annList, lhs, exp, jump)
    context.pushResult(result, t)
    return false
  }

  override def visitCAST(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // type: 0
    val typeSpec = getChild[TypeSpec](n, t)
    n += 1

    // exp: 1
    val exp = getChild[Exp](n, t)
    n += 1

    val result = CastExp(typeSpec, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitCATCH_CLAUSE(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // annotation list: 0
    val annList = getChildren[Annotation](n, t)
    n += 1

    // optional type: 1
    val typeSpec = getOptChild[TypeSpec](n, t)
    n += 1

    // optional id (local name): 2
    val local = getOptChild[Id](n, t).map(getNameUser)
    n += 1

    // id (from): 3
    val from = getNameUser(getChild[Id](n, t))
    n += 1

    // id (to): 4
    val to = getNameUser(getChild[Id](n, t))
    n += 1

    // jump: 5
    val jump = getChild[GotoJump](n, t)
    n += 1

    val result = CatchClause(annList, typeSpec, local, from, to, jump)
    context.pushResult(result, t)
    return false
  }

  override def visitCHAR(t : Tree) : Boolean = {
    assert(context.noResult)

    val s = t.getChild(0).getText()
    val text = s.substring(1, s.size - 1)
    val c = StringEscapeUtils.unescapeJava(text).charAt(0)

    val result = LiteralExp(LiteralType.CHAR, c, text)
    context.pushResult(result, t)
    return false
  }

  override def visitCONST(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // const element list: 2
    val elements = getChildren[ConstElement](n, t)
    n += 1

    val result = ConstDecl(getNameDefinition(id), annList, elements)
    context.pushResult(result, t)
    return false
  }

  override def visitCONST_ELEMENT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // exp: 1
    val exp = getChild[Exp](n, t)
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ConstElement(getNameDefinition(id), exp, annList)
    context.pushResult(result, t)
    return false
  }

  override def visitELSE_GUARD(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // annotation list: 0
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ElseGuard(annList)
    context.pushResult(result, t)
    return false
  }

  override def visitENUM(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // enum element list: 2
    var elements = getChildren[EnumElement](n, t)
    n += 1

    val result = EnumDecl(getNameDefinition(id), annList, elements)
    context.pushResult(result, t)
    return false
  }

  override def visitENUM_ELEMENT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = EnumElement(getNameDefinition(id), annList)
    context.pushResult(result, t)
    return false
  }

  override def visitEXP_EXT(t : Tree) : Boolean = {
    assert(context.noResult && !context.varArity)

    var n = 0

    // optional type var tuple: 0
    val typeVars = tto2pair(getOptChild[ISeq[TypeVarSpec]](n, t))
    n += 1

    // optional type: 1
    var returnTypeSpec = getOptChild[TypeSpec](n, t)
    n += 1

    // id: 2
    val id = getChild[Id](n, t)
    n += 1

    // optional ext param list: 3
    val params = getChildren[ExtParam](0, t.getChild(n))
    n += 1

    // annotation list: 4
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ExpExtensionDecl(typeVars,
      getNameDefinition(id),
      annList,
      params,
      context.varArity,
      returnTypeSpec)
    context.pushResult(result, t)
    context.varArity = false
    return false
  }

  override def visitEXP_GUARD(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val exp = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ExpGuard(annList, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitEXT_PARAM(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // optional type: 0
    val typeSpec = getOptChild[TypeSpec](n, t)
    n += 1

    // optional id: 1
    val name = getOptChild[Id](n, t).map(getNameDefinition)
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ExtParam(typeSpec, name, annList)
    context.pushResult(result, t)
    return false
  }

  override def visitEXT_PARAM_VARIABLE(t : Tree) : Boolean = {
    visitEXT_PARAM(t)
    context.varArity = true
    return false
  }

  override def visitEXTENDCLAUSE_ELEMENT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // name: 0
    val name = getChild[Id](n, t)
    n += 1

    // optional type tuple: 1
    val typeArgs = getOptChild[ISeq[TypeSpec]](n, t).getOrElse(ilistEmpty)
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ExtendClause(getNameUser(name), annList, typeArgs)
    context.pushResult(result, t)
    return false
  }

  override def visitEXTENSION(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // optional type var tuple: 0
    val typeVars = tto2pair(getOptChild[ISeq[TypeVarSpec]](n, t))
    n += 1

    // id: 1
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    // ext element list: 3
    val elements = getChildren[ExtElement](n, t)
    n += 1

    val result = ExtensionDecl(getNameDefinition(id), annList, typeVars, elements)
    context.pushResult(result, t)
    return false
  }

  override def visitFALSE(t : Tree) : Boolean = {
    assert(context.noResult)

    val result = LiteralExp(LiteralType.BOOLEAN, false, "false")
    context.pushResult(result, t)
    return false
  }

  override def visitFLOAT(t : Tree) : Boolean = {
    assert(context.noResult)

    val s = t.getChild(0).getText()

    val line = t.getLine
    val column = t.getCharPositionInLine

    var result : LiteralExp = null

    if (s.endsWith("f") || s.endsWith("F")) {
      try {
        result = LiteralExp(LiteralType.FLOAT, s.toFloat, s)
      } catch {
        case ex : Exception =>
          context.reporter.report(context.source,
            line, column, "Cannot parse float: " + s)
      }
    } else {
      try {
        result = LiteralExp(LiteralType.DOUBLE, s.toDouble, s)
      } catch {
        case ex : Exception =>
          context.reporter.report(context.source,
            line, column, "Cannot parse double: " + s)
      }
    }

    context.pushResult(result, t)
    return false
  }

  override def visitFUN(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // exp: 2
    val exp = getChild[FunExp](n, t)
    n += 1

    val result = FunDecl(getNameDefinition(id), annList, exp.matchings)
    context.pushResult(result, t)
    return false
  }

  override def visitFUN_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // matching list: 0
    val matchings = getChildren[Matching](n, t)
    n += 1

    val result = FunExp(matchings)
    context.pushResult(result, t)
    return false
  }

  override def visitFUN_TYPE(t : Tree) : Boolean = {
    assert(context.noResult)

    val oldVarArity = context.varArity
    context.varArity = false

    var n = 0

    // type param list: 0
    val typeParams = getChildren[TypeParam](n, t)
    n += 1

    // optional annotatedType : 1
    val typeSpec = getOptChild[TypeSpec](n, t)
    n += 1

    val result = FunTypeSpec(context.varArity, typeParams, typeSpec)
    context.pushResult(result, t)
    context.varArity = oldVarArity
    return false
  }

  override def visitFUNCTION_TYPE(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // type param list: 0
    if (hasNonEmptyChild(n, t)) {
      visitTUPLE_TYPE(t)
    } else {
      val typeParam = getChild[TypeParam](n, t)
      context.pushResult(TupleTypeSpec(ilist(typeParam)))
    }
    val domainTypeSpec = context.popResult[TupleTypeSpec].elementTypes
    n += 1

    // annotatedType: 1
    val imageTypeSpec = getChild[TypeSpec](n, t)
    n += 1

    val result = FunctionTypeSpec(domainTypeSpec, imageTypeSpec)
    context.pushResult(result, t)
    return false
  }

  override def visitGLOBAL(t : Tree) : Boolean = {
    assert(context.noResult && context.typeSpec == None)

    var n = 0

    // optional type: 0
    context.typeSpec = getOptChild[TypeSpec](n, t)
    n += 1

    // global fragment list: 1
    val result = getChildren[GlobalVarDecl](n, t)
    n += 1

    context.pushResult(result)
    context.typeSpec = None
    return false
  }

  override def visitGLOBAL_FRAGMENT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result =
      GlobalVarDecl(getNameDefinition(id),
        annList,
        context.typeSpec)
    context.pushResult(result, t)
    return false
  }

  override def visitGLOBALID(t : Tree) : Boolean = {
    val s = t.getText()
    context.pushResult(Id(s, t.getLine, t.getCharPositionInLine))
    return false
  }

  override def visitGOTO(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = GotoJump(annList, getNameUser(id))
    context.pushResult(result, t)
    return false
  }

  override def visitID(t : Tree) : Boolean = {
    val s = t.getText()
    context.pushResult(new Id(s, t.getLine, t.getCharPositionInLine))
    return false
  }

  override def visitIF_ELSE_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // annotation list: 0
    val annList = getChildren[Annotation](n, t)
    n += 1

    // exp: 1
    val exp = getChild[Exp](n, t)
    n += 1

    exp.annotations = annList
    context.pushResult(exp)
    return false
  }

  override def visitIF_ELSE_JUMP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // annotation list: 0
    val annList = getChildren[Annotation](n, t)
    n += 1

    // id: 1
    val id = getChild[Id](n, t)
    n += 1

    val result = GotoJump(annList, getNameUser(id))
    context.pushResult(result, t)
    return false
  }

  override def visitIF_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // if-then-exp list: 0
    val ifThenExps = getChildren[IfThenExp](n, t)
    n += 1

    // if-else-exp: 1
    val ifElseExp = getChild[Exp](n, t)
    n += 1

    val result = IfExp(ifThenExps, ifElseExp)
    context.pushResult(result, t)
    return false
  }

  override def visitIF_JUMP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // if-then-jump list: 0
    val ifThenJumps = getChildren[IfThenJump](n, t)
    n += 1

    // optional if-else-jump: 1
    val ifElseJump = getOptChild[GotoJump](n, t)
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = IfJump(annList, ifThenJumps, ifElseJump)
    context.pushResult(result, t)
    return false
  }

  override def visitIF_THEN_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp (cond): 0
    val cond = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // exp: 2
    val exp = getChild[Exp](n, t)
    n += 1

    val result = IfThenExp(cond, annList, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitIF_THEN_JUMP(t : Tree) : Boolean = {
    assert(context.noResult)

    // location info
    val line = t.getLine
    val column = t.getCharPositionInLine

    var n = 0

    // exp: 0
    val cond = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // id: 2
    val id = getChild[Id](n, t)
    n += 1

    val result = IfThenJump(cond, annList, getNameUser(id))
    context.pushResult(result, t)
    return false
  }

  override def visitINDEXING(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp (indexed): 0
    val exp = getChild[Exp](n, t)
    n += 1

    // exp list (indices): 1
    val indices = getChildren[Exp](n, t)
    n += 1

    val result = IndexingExp(exp, indices)
    context.pushResult(result, t)
    return false
  }

  override def visitINT(t : Tree) : Boolean = {
    assert(context.noResult)

    val s = t.getChild(0).getText
    var temp = s.toUpperCase

    var radix = 0

    if (temp.startsWith("0X")) {
      radix = 16
      temp = temp.substring(2)
    } else if (temp.startsWith("0B")) {
      radix = 2
      temp = temp.substring(2)
    } else if (temp.equals("0")) {
      radix = 10
    } else if (temp.startsWith("0")) {
      radix = 8
    } else {
      radix = 10
    }

    var choice = -1
    if (temp.endsWith("L")) {
      temp = temp.substring(0, temp.length() - 1)
      choice = 0
    } else if (s.endsWith("II")) {
      temp = temp.substring(0, temp.length() - 2)
      choice = 1
    } else {
      choice = 2
    }

    // location info
    val line = t.getLine
    val column = t.getCharPositionInLine

    var bi : BigInt = null
    try {
      bi = BigInt(temp, radix)
    } catch {
      case ex : Exception =>
        context.reporter.report(context.source,
          line, column, "Cannot parse integer: " + s)
        return false
    }

    var result : LiteralExp = null
    choice match {
      case 0 =>
        if (bi < Long.MinValue || bi > Long.MaxValue) {
          context.reporter.report(context.source,
            line, column, "Cannot parse long: " + s)
        } else {
          result = LiteralExp(LiteralType.LONG, bi.longValue, s)
        }
      case 1 =>
        result = LiteralExp(LiteralType.INTEGER, bi, s)
      case _ =>
        if (bi < Int.MinValue || bi > Int.MaxValue) {
          context.reporter.report(context.source,
            line, column, "Cannot parse int: " + s)
        } else {
          result = LiteralExp(LiteralType.INT, bi.intValue, s)
        }
    }

    context.pushResult(result, t)
    return false
  }

  override def visitLET_BINDING(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id list: 0
    val ids = getChildren[Id](n, t).map(getNameDefinition)
    n += 1

    // exp: 1
    val exp = getChild[Exp](n, t)
    n += 1

    val result = LetBinding(ids, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitLET_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // binding list: 0
    val bindings = getChildren[LetBinding](n, t)
    n += 1

    // exp: 1
    val exp = getChild[Exp](n, t)
    n += 1

    val result = LetExp(bindings, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitLHS(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val exp = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    exp.annotations = annList
    context.pushResult(exp)
    return false
  }

  override def visitLIST_FRAGMENT(t : Tree) : Boolean = {
    val result =
      if (!context.noResult)
        ListTypeSpec(context.popResult[TypeSpec])
      else
        ListTypeFragment()
    context.pushResult(result, t)
    return false
  }

  override def visitLOCAL(t : Tree) : Boolean = {
    assert(context.noResult && context.typeSpec == None)

    var n = 0

    // optional type: 0
    context.typeSpec = getOptChild[TypeSpec](n, t)
    n += 1

    // local fragment list: 1
    val result = getChildren[LocalVarDecl](n, t)
    context.pushResult(result)
    n += 1

    context.typeSpec = None
    return false
  }

  override def visitLOCAL_FRAGMENT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result =
      LocalVarDecl(
        context.typeSpec,
        getNameDefinition(id),
        annList)
    context.pushResult(result, t)
    return false
  }

  override def visitLOCATION(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // locid: 0
    val cid = getChild[Id](n, t)
    var id = cid.id
    if (id.endsWith(".")) {
      id = id.substring(1, id.length() - 1)
    } else {
      id = id.substring(1)
    }
    val locId =
      if (id.length() > 0)
        Some(getNameDefinition(Id(id, cid.line, cid.column)))
      else
        None
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // transformation list: 2
    val transformations = getChildren[Transformation](n, t)
    n += 1

    var action : Action = null
    var jump : Jump = null

    var resultType =
      if (transformations.size == 1) {
        val t = transformations(0)
        if (t.isInstanceOf[Transformation] && t.guard.isEmpty) {
          val st = t.asInstanceOf[Transformation]
          val aSize = st.actions.size
          val jEmpty = st.jump.isEmpty
          if (aSize == 1 && jEmpty) {
            action = st.actions(0)
            0
          } else if (aSize == 0) {
            if (jEmpty) 2
            else {
              jump = st.jump.get
              1
            }
          } else 3
        } else 3
      } else 3

    val result = resultType match {
      case 0 => ActionLocation(locId, annList, action)
      case 1 => JumpLocation(locId, annList, jump)
      case 2 => EmptyLocation(locId, annList)
      case _ => ComplexLocation(locId, annList, transformations)
    }
    context.pushResult(result, t)
    return false
  }

  override def visitLOCID(t : Tree) : Boolean = {
    val s = t.getText()
    context.pushResult(Id(s, t.getLine, t.getCharPositionInLine))
    return false
  }

  override def visitMAPPING(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val exp0 = getChild[Exp](n, t)
    n += 1

    // exp: 1
    val exp1 = getChild[Exp](n, t)
    n += 1

    context.pushResult(Pair(exp0, exp1))
    return false
  }

  override def visitMATCHING(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // optional param list: 0
    val params = getChildren[ParamDecl](0, t.getChild(n))
    n += 1

    // exp: 1
    val exp = getChild[Exp](n, t)
    n += 1

    val result = Matching(params, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitMODEL(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // annotation list: 0
    val annList = getChildren[Annotation](n, t)
    n += 1

    // default package elements: 1
    val defaultPackageElements =
      getChildrenFlat[PackageElement](n, t)
    val defaultPackage =
      PackageDecl(None, ilistEmpty, defaultPackageElements)
    n += 1

    // package list: 2
    val packages =
      getChildren[PackageDecl](n, t) :+ defaultPackage
    n += 1

    var result = Model(context.source, annList, packages)
    context.pushResult(result, t)
    return false
  }

  override def visitMULTIARRAY_FRAGMENT(t : Tree) : Boolean = {
    val rank = t.getChild(0).getChildCount

    val result : PilarAstNode =
      if (!context.noResult)
        MultiArrayTypeSpec(context.popResult[TypeSpec], rank)
      else
        MultiArrayTypeFragment(rank)

    context.pushResult(result, t)

    return false
  }

  override def visitNAME(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id list (package name): 0
    val name = getChildren[Id](n, t)
    n += 1

    // id: 1
    val id = getChild[Id](n, t)
    n += 1

    val qname =
      if (!name.isEmpty)
        (name.map { _.id + PILAR_PACKAGE_SEP }).
          reduce({ (s1, s2) => s1 + s2 }) + id.id
      else
        id.id

    context.pushResult(Id(qname, t.getLine, t.getCharPositionInLine))
    return false
  }

  override def visitNAME_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // name: 0
    val name = getChild[Id](n, t)
    n += 1

    val result = NameExp(getNameUser(name))
    context.pushResult(result, t)
    return false
  }

  override def visitNAME_TYPE(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // name: 0
    val name = getChild[Id](n, t)
    n += 1

    // optional id: 1
    var id = getOptChild[Id](n, t)
    n += 1

    // optional type tuple: 2
    val typeTuple = getOptChild[ISeq[TypeSpec]](n, t).getOrElse(ilistEmpty)
    n += 1

    var result =
      id match {
        case Some(id) =>
          NamedExtTypeSpec(name.id, getNameUser(id), typeTuple)
        case _ => NamedTypeSpec(getNameUser(name), typeTuple)
      }

    context.pushResult(result, t)
    return false
  }

  override def visitNEW_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // type: 0
    val typeSpec = getChild[TypeSpec](n, t)
    n += 1

    // multi-array fragment list: 1
    val expss = getChildrenBox[ISeq[Exp]](n, t,
      { x => x.asInstanceOf[ISeq[Exp]] })
    n += 1

    // type fragment list: 2
    var typeFragments = getChildren[TypeFragment](n, t)
    n += 1

    val result = NewExp(typeSpec, expss, typeFragments)
    context.pushResult(result, t)
    return false
  }

  override def visitNEW_MULTI_ARRAY_FRAGMENT(
    t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp list: 0
    val result = getChildren[Exp](n, t)
    n += 1

    context.pushResult(result)
    return false
  }

  override def visitNFUNCTION(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // optional mapping list: 0
    val expPairs = getChildren[(Exp, Exp)](0, t.getChild(n))
    n += 1

    val result = NewFunctionExp(expPairs)
    context.pushResult(result, t)
    return false
  }

  override def visitNLIST(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp list: 0
    val exps = getChildren[Exp](n, t)
    n += 1

    val result = NewListExp(exps)
    context.pushResult(result, t)
    return false
  }

  override def visitNLIST_RANGE(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp (low): 0
    val lowExp = getChild[Exp](n, t)
    n += 1

    // exp (high): 0
    val highExp = getChild[Exp](n, t)
    n += 1

    val result = NewListRangedExp(lowExp, highExp)
    context.pushResult(result, t)
    return false
  }

  override def visitNMULTI_ARRAY(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // multi array fragments list: 0
    val mafs = getChildrenBox[MultiArrayFragment](n, t, e2maf)
    n += 1

    val result = NewMultiArrayExp(mafs)
    context.pushResult(result, t)
    return false
  }

  override def visitNMULTI_ARRAY_FRAGMENT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // multi array fragments list: 0
    var mafs = getChildrenBox[MultiArrayFragment](n, t, e2maf)
    n += 1

    val result = MultiArrayMultiArrayFragment(mafs)
    context.pushResult(result, t)
    return false
  }

  override def visitNRECORD(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // type: 0
    val typeSpec = getChild[NamedTypeSpec](n, t)
    n += 1

    // attr init list: 1
    val attrInits = getChildren[AttributeInit](n, t)
    n += 1

    val result = NewRecordExp(typeSpec, attrInits)
    context.pushResult(result, t)
    return false
  }

  override def visitNSET(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp list: 0
    val exps = getChildren[Exp](n, t)
    n += 1

    val result = NewSetExp(exps)
    context.pushResult(result, t)
    return false
  }

  override def visitNULL(t : Tree) : Boolean = {
    assert(context.noResult)

    val result = LiteralExp(LiteralType.NULL, null, "null")
    context.pushResult(result, t)
    return false
  }

  override def visitPACKAGE(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // name: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // package elements: 2
    val elements = getChildrenFlat[PackageElement](n, t)
    n += 1

    val result = PackageDecl(Some(getNameDefinition(id)), annList, elements)
    context.pushResult(result, t)
    return false
  }

  override def visitPARAM(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // optional type: 0
    val typeSpec = getOptChild[TypeSpec](n, t)
    n += 1

    // id: 1
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ParamDecl(typeSpec, getNameDefinition(id), annList)
    context.pushResult(result, t)
    return false
  }

  override def visitPARAM_VARIABLE(t : Tree) : Boolean = {
    visitPARAM(t)
    context.varArity = true
    return false
  }

  override def visitPROC_EXT(t : Tree) : Boolean = {
    assert(context.noResult && !context.varArity)

    var n = 0

    // optional type var tuple: 0
    val typeVars = tto2pair(getOptChild[ISeq[TypeVarSpec]](n, t))
    n += 1

    // optional type: 1
    var returnTypeSpec = getOptChild[TypeSpec](n, t)
    n += 1

    // id: 2
    val id = getChild[Id](n, t)
    n += 1

    // optional ext param list: 3
    val params = getChildren[ExtParam](0, t.getChild(n))
    n += 1

    // annotation list: 4
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ProcedureExtensionDecl(typeVars,
      getNameDefinition(id),
      annList,
      params,
      context.varArity,
      returnTypeSpec)
    context.pushResult(result, t)
    context.varArity = false
    return false
  }

  override def visitPROCEDURE(t : Tree) : Boolean = {
    assert(context.noResult && context.varArity == false)

    var n = 0

    // optional type var tuple: 0
    val typeVars = tto2pair(getOptChild[ISeq[TypeVarSpec]](n, t))
    n += 1

    // optional type: 1
    val typeSpec = getOptChild(n, t)
    n += 1

    // id: 2
    val id = getChild[Id](n, t)
    n += 1

    // parameter list: 3
    val params = getChildren[ParamDecl](n, t)
    n += 1

    // annotation list: 4
    val annList = getChildren[Annotation](n, t)
    n += 1

    // body: 5
    val body = getChild[Body](n, t)
    n += 1

    val result =
      ProcedureDecl(getNameDefinition(id), annList, typeVars,
        params, typeSpec, context.varArity, body)
    context.pushResult(result, t)
    context.varArity = false
    return false
  }

  override def visitPROCEDURE_TYPE(t : Tree) : Boolean = {
    assert(context.noResult)

    val oldVarArity = context.varArity
    context.varArity = false

    var n = 0

    // type param list: 0
    val typeParams = getChildren[TypeParam](n, t)
    n += 1

    // optional annotatedType : 1
    val typeSpec = getOptChild[TypeSpec](n, t)
    n += 1

    val result = ProcedureTypeSpec(context.varArity, typeParams, typeSpec)
    context.pushResult(result, t)
    context.varArity = oldVarArity
    return false
  }

  override def visitPROCEDURE_TYPE_PARAM_VARIABLE(t : Tree) : Boolean = {
    visitTYPE_PARAM(t)
    context.varArity = true
    return false
  }

  override def visitRATIONAL(t : Tree) : Boolean = {
    assert(context.noResult)

    val s = t.getChild(0).getText()

    var isPos = false
    var start = 0
    if (s.charAt(0) == '-') {
      start = 1
      isPos = false
    } else {
      start = 0
      isPos = true
    }

    val divIndex = s.indexOf('/')

    val c = SireumNumber(0)
    val n = SireumNumber(BigInt(s.substring(start, divIndex)))
    val d = SireumNumber(BigInt(s.substring(divIndex + 1)))

    val r = SireumNumber(isPos, c, n, d)

    val result = LiteralExp(LiteralType.RATIONAL, r, s)
    context.pushResult(result, t)
    return false
  }

  override def visitRAW(t : Tree) : Boolean = {
    assert(context.noResult)

    var s = t.getChild(0).getText()
    s = s.substring(1, s.length() - 1)
    s = StringEscapeUtils.unescapeJava(s)

    val result = LiteralExp(LiteralType.RAW, s, s)
    context.pushResult(result, t)
    return false
  }

  override def visitRECORD(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // optional type var tuple: 1
    val typeVars = tto2pair(getOptChild[ISeq[TypeVarSpec]](n, t))
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    // optional extend clause list: 3
    val extendClauses = getChildren[ExtendClause](0, t.getChild(n))
    n += 1

    // attribute list: 4
    val attributes = getChildrenFlat[AttributeDecl](n, t)
    n += 1

    val result = RecordDecl(getNameDefinition(id), annList, typeVars,
      extendClauses, attributes)
    context.pushResult(result, t)
    return false
  }

  override def visitRELATION_TYPE(t : Tree) : Boolean = {
    assert(context.noResult)

    visitTUPLE_TYPE(t)
    val tt = context.popResult[TupleTypeSpec]

    val result = RelationTypeSpec(tt.elementTypes)
    context.pushResult(result, t)
    return false
  }

  override def visitRETURN(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // optional exp: 0
    val exp = getOptChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ReturnJump(annList, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitRHS(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // annotation list: 0
    val annList = getChildren[Annotation](n, t)
    n += 1

    // exp: 1
    val exp = getChild[Exp](n, t)
    n += 1

    exp.annotations = annList
    context.pushResult(exp)
    return false
  }

  override def visitSET_FRAGMENT(t : Tree) : Boolean = {

    val result =
      if (!context.noResult)
        SetTypeSpec(context.popResult[TypeSpec])
      else
        SetTypeFragment()

    context.pushResult(result, t)
    return false
  }

  override def visitSTART(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // name: 0
    val name = getChild[Id](n, t)
    n += 1

    // optional exp (instance count): 1
    val count = getOptChild[Exp](n, t)
    n += 1

    // optional exp (argument): 1
    val arg = getOptChild[Exp](n, t)
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = StartAction(annList, getNameUser(name), count, arg)
    context.pushResult(result, t)
    return false
  }

  override def visitSTRING(t : Tree) : Boolean = {
    assert(context.noResult)

    var s = t.getChild(0).getText()
    s = s.substring(1, s.length() - 1)
    s = StringEscapeUtils.unescapeJava(s)

    val result = LiteralExp(LiteralType.STRING, s, s)
    context.pushResult(result, t)
    return false
  }

  override def visitSWITCH_CASE_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp (case): 0
    val cond = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // exp: 2
    val exp = getChild[Exp](n, t)
    n += 1

    val result = SwitchCaseExp(cond, annList, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitSWITCH_CASE_JUMP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val cond = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // id: 2
    val id = getChild[Id](n, t)
    n += 1

    val result = SwitchCaseJump(cond, annList, getNameUser(id))
    context.pushResult(result, t)
    return false
  }

  override def visitSWITCH_DEFAULT_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // annotation list: 0
    val annList = getChildren[Annotation](n, t)
    n += 1

    // exp: 1
    val exp = getChild[Exp](n, t)
    n += 1

    exp.annotations = annList
    context.pushResult(exp)
    return false
  }

  override def visitSWITCH_DEFAULT_JUMP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // annotation list: 0
    val annList = getChildren[Annotation](n, t)
    n += 1

    // id: 1
    val id = getChild[Id](n, t)
    n += 1

    val result = GotoJump(annList, getNameUser(id))
    context.pushResult(result, t)
    return false
  }

  override def visitSWITCH_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val cond = getChild[Exp](n, t)
    n += 1

    // switch case exp list: 1
    val switchCases = getChildren[SwitchCaseExp](n, t)
    n += 1

    // optional switch default exp: 2
    val defaultExp = getChild[Exp](n, t)
    n += 1

    val result = SwitchExp(cond, switchCases, defaultExp)
    context.pushResult(result, t)
    return false
  }

  override def visitSWITCH_JUMP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val cond = getChild[Exp](n, t)
    n += 1

    // switch case jump list: 1
    val switchCaseJumps =
      getChildren[SwitchCaseJump](n, t)
    n += 1

    // optional switch default jump: 2
    val defaultJump = getOptChild[GotoJump](n, t)
    n += 1

    // annotation list: 3
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = SwitchJump(annList, cond, switchCaseJumps, defaultJump)
    context.pushResult(result, t)
    return false
  }

  override def visitSYMBOL(t : Tree) : Boolean = {
    assert(context.noResult)

    val s = t.getChild(0).getText().substring(1)

    val result = LiteralExp(LiteralType.SYMBOL, s, s)
    context.pushResult(result, t)
    return false
  }

  override def visitTHROW(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // exp: 0
    val exp = getChild[Exp](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = ThrowAction(annList, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitTRANSFORMATION(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // annotation list: 0
    val annList = getChildren[Annotation](n, t)
    n += 1

    // optional guard: 1
    val guard = getOptChild[Guard](n, t)
    n += 1

    // action list: 2
    val actions = getChildren[Action](n, t)
    n += 1

    // optional jump: 3
    val jump = getOptChild[Jump](n, t)
    n += 1

    val result = Transformation(annList, guard, actions, jump)
    context.pushResult(result, t)
    return false
  }

  override def visitTRUE(t : Tree) : Boolean = {
    assert(context.noResult)

    val result = LiteralExp(LiteralType.BOOLEAN, true, "true")
    context.pushResult(result, t)
    return false
  }

  override def visitTUPLE(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // annotated exp list: 0
    val exps = getChildren[Exp](n, t)
    n += 1

    val result = TupleExp(exps)
    context.pushResult(result, t)
    return false
  }

  override def visitTUPLE_TYPE(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // type param list: 0
    val typeParams = getChildren[TypeParam](n, t)
    n += 1

    val result = TupleTypeSpec(typeParams)
    context.pushResult(result, t)
    return false
  }

  override def visitTYPE_EXP(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // type: 0
    val typeSpec = getChild[TypeSpec](n, t)
    n += 1

    val result = TypeExp(typeSpec)
    context.pushResult(result, t)
    return false
  }

  override def visitTYPE_EXT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // optional type var tuple: 0
    val typeVars = tto2pair(getOptChild[ISeq[TypeVarSpec]](n, t))
    n += 1

    // id: 1
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    // extend clause: 3
    val extendClauses = getChildren[ExtendClause](n, t)
    n += 1

    // type extension member list: 4
    val members = getChildren[TypeExtElement](n, t)
    n += 1

    val result =
      TypeExtensionDecl(typeVars, getNameDefinition(id), annList, extendClauses,
        members)
    context.pushResult(result, t)
    return false
  }

  override def visitTYPE_EXT_ATTRIBUTE_BINDING(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val extId = getChild[Id](n, t)
    n += 1

    // id: 1
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result =
      TypeExtAttributeBinding(
        ilistEmpty,
        getNameDefinition(extId), annList,
        getNameUser(id))
    context.pushResult(result, t)
    return false
  }

  override def visitTYPE_PARAM(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // type: 0
    val typeSpec = getChild[TypeSpec](n, t)
    n += 1

    // optional id: 1
    val id = getOptChild[Id](n, t).map { _.id }
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = TypeParam(typeSpec, id, annList)
    context.pushResult(result, t)
    return false
  }

  override def visitTYPE_TUPLE(t : Tree) : Boolean = {
    assert(context.noResult)

    val result = getChildren[TypeSpec](0, t)

    context.pushResult(result)
    return false
  }

  override def visitTYPEALIAS(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // type: 0
    val typeSpec = getChild[TypeSpec](n, t)
    n += 1

    // id: 1
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 2
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = TypeAliasDecl(getNameDefinition(id), annList, typeSpec)
    context.pushResult(result, t)
    return false
  }

  override def visitTYPEVAR(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = TypeVarSpec(getNameUser(id))
    result.annotations = annList
    context.pushResult(result, t)
    return false
  }

  override def visitTYPEVAR_TUPLE(t : Tree) : Boolean = {
    assert(context.noResult)

    val result = getChildren[TypeVarSpec](0, t)

    context.pushResult(result)
    return false
  }

  override def visitTYPEVARID(t : Tree) : Boolean = {
    val s = t.getText()
    context.pushResult(Id(s, t.getLine, t.getCharPositionInLine))
    return false
  }

  override def visitTYPEVARID_TYPE(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // type var id: 0
    val id = getChild[Id](n, t)
    n += 1

    val result = TypeVarSpec(getNameUser(id))
    context.pushResult(result, t)
    return false
  }

  override def visitUNARY(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // op: 0
    val op = t.getChild(n).getText;
    n += 1;

    // exp: 1
    val exp = getChild[Exp](n, t)
    n += 1

    val result = UnaryExp(op, exp)
    context.pushResult(result, t)
    return false
  }

  override def visitVSET(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // id: 0
    val id = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    // vset element list: 2
    val elements = getChildren[VSetElement](n, t)
    n += 1

    val result = VSetDecl(getNameDefinition(id), annList, elements)
    context.pushResult(result, t)
    return false
  }

  override def visitVSET_ELEMENT(t : Tree) : Boolean = {
    assert(context.noResult)

    var n = 0

    // name: 0
    val name = getChild[Id](n, t)
    n += 1

    // annotation list: 1
    val annList = getChildren[Annotation](n, t)
    n += 1

    val result = VSetElement(getNameUser(name), annList)
    context.pushResult(result, t)
    return false
  }

  def getNameUser(id : Id) : NameUser = {
    val result = NameUser(id.id)
    result.setLocationLineColumn(id.line, id.column)
    return result
  }

  def getNameDefinition(id : Id) : NameDefinition = {
    val result = NameDefinition(id.id)
    result.setLocationLineColumn(id.line, id.column)
    result.locationFile = context.source.getOrElse(null)
    return result
  }
}

