/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.ast

import scala.collection._

import org.sireum.pilar._
import org.sireum.pilar.symbol.SymbolDefinition
import org.sireum.pilar.symbol.SymbolUser
import org.sireum.pilar.state._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed trait PilarAstNode extends PropertyProvider {
  lazy val propertyMap = mmapEmpty[Property.Key, Any]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class Name
    extends PilarAstNode {
  def name : String
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NameDefinition(name : String)
  extends Name with SymbolDefinition

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NameUser(name : String)
  extends Name with SymbolUser

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Model(
  sourceURI : Option[FileResourceUri],
  annotations : ISeq[Annotation],
  packages : ISeq[PackageDecl])
    extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Annotation(
  name : NameUser,
  params : ISeq[AnnotationParam])
    extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class AnnotationParam extends PilarAstNode {
  def name : Option[NameUser]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class AnnotationAnnotationParam(
  name : Option[NameUser],
  annotation : Annotation)
    extends AnnotationParam

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ExpAnnotationParam(
  name : Option[NameUser],
  exp : Exp)
    extends AnnotationParam

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Annotable extends PilarAstNode {
  private lazy val markerCache = computeMarkerAnnotationCache
  private lazy val valueCache = computeValueAnnotationCache
  private lazy val annotationCache = computeAnnotationCache
  private var markerCacheInitialized = false

  def annotations : ISeq[Annotation]

  def hasMarkerAnnotation(name : String) : Boolean =
    if (annotations.size == 0) false
    else markerCache.contains(name)

  def getValueAnnotation(name : String) : Option[Exp] =
    if (annotations.size == 0) None
    else valueCache.get(name)

  def getAnnotation(name : String) : Option[Annotation] =
    if (annotations.size == 0) None
    else annotationCache.get(name)

  private def computeMarkerAnnotationCache() : MSet[String] = {
    val result = msetEmpty[String]
    annotations.foreach { a =>
      if (a.params.isEmpty) result += pilarSimpleName(a.name.name)
    }
    return result
  }

  private def computeValueAnnotationCache() : MMap[String, Exp] = {
    val result = mmapEmpty[String, Exp]
    annotations.foreach { a =>
      if (a.params.size == 1 && a.params(0).isInstanceOf[ExpAnnotationParam]) {
        val eap = a.params(0).asInstanceOf[ExpAnnotationParam]
        if (eap.name.isEmpty ||
          eap.name.get.name == "PILAR_VALUE_ANNOTATION_PARAM_ID")
          result(pilarSimpleName(a.name.name)) = eap.exp
      }
    }
    return result
  }

  private def computeAnnotationCache() : MMap[String, Annotation] = {
    val result = mmapEmpty[String, Annotation]
    annotations.foreach { a => result(pilarSimpleName(a.name.name)) = a }
    return result
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class PackageDecl(
  name : Option[NameDefinition],
  annotations : ISeq[Annotation],
  elements : ISeq[PackageElement])
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class PackageElement extends Annotable {
  def name : NameDefinition
  def annotations : ISeq[Annotation]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ConstDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  elements : ISeq[ConstElement])
    extends PackageElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ConstElement(
  name : NameDefinition,
  exp : Exp,
  annotations : ISeq[Annotation])
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class EnumDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  elements : ISeq[EnumElement])
    extends PackageElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class EnumElement(
  name : NameDefinition,
  annotations : ISeq[Annotation])
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class TypeAliasDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeSpec : TypeSpec)
    extends PackageElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class RecordDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  extendsClauses : ISeq[ExtendClause],
  attributes : ISeq[AttributeDecl])
    extends PackageElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ExtendClause(
  name : NameUser,
  annotations : ISeq[Annotation],
  typeArgs : ISeq[TypeSpec])
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class AttributeDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeSpec : Option[TypeSpec],
  binding : Option[NameUser])
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class GlobalVarDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeSpec : Option[TypeSpec])
    extends PackageElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ProcedureDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  params : ISeq[ParamDecl],
  returnType : Option[TypeSpec],
  varArity : Boolean,
  body : Body)
    extends PackageElement {

  def withoutBody =
    body match {
      case b : EmptyBody => this
      case _ =>
        ProcedureDecl(name, annotations, typeVars, params, returnType,
          varArity, EmptyBody())
    }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ParamDecl(
  typeSpec : Option[TypeSpec],
  name : NameDefinition,
  annotations : ISeq[Annotation])
    extends LocalVar

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class VSetDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  elements : ISeq[VSetElement])
    extends PackageElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class VSetElement(
  name : NameUser,
  annotations : ISeq[Annotation])
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class FunDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  matchings : ISeq[Matching])
    extends PackageElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ExtensionDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  elements : ISeq[ExtElement])
    extends PackageElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class ExtElement extends Annotable {
  def typeVars : ISeq[(NameDefinition, ISeq[Annotation])]
  def name : NameDefinition
  def annotations : ISeq[Annotation]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class TypeExtensionDecl(
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  name : NameDefinition,
  annotations : ISeq[Annotation],
  extendClauses : ISeq[ExtendClause],
  elements : ISeq[TypeExtElement])
    extends ExtElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class TypeExtElement extends ExtElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class TypeExtAttributeBinding(
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  name : NameDefinition,
  annotations : ISeq[Annotation],
  extName : NameUser)
    extends TypeExtElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class ParameterizedTypeExtElement extends TypeExtElement {
  def params : ISeq[ExtParam]
  def varArity : Boolean
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ActionExtensionDecl(
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  name : NameDefinition,
  annotations : ISeq[Annotation],
  params : ISeq[ExtParam],
  varArity : Boolean)
    extends ParameterizedTypeExtElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ExpExtensionDecl(
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  name : NameDefinition,
  annotations : ISeq[Annotation],
  params : ISeq[ExtParam],
  varArity : Boolean,
  returnTypeSpec : Option[TypeSpec])
    extends ParameterizedTypeExtElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ProcedureExtensionDecl(
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  name : NameDefinition,
  annotations : ISeq[Annotation],
  params : ISeq[ExtParam],
  varArity : Boolean,
  returnTypeSpec : Option[TypeSpec])
    extends ParameterizedTypeExtElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ExtParam(
  typeSpec : Option[TypeSpec],
  name : Option[NameDefinition],
  annotations : ISeq[Annotation])
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class Body extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class EmptyBody() extends Body

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ImplementedBody(
  locals : ISeq[LocalVarDecl],
  locations : ISeq[LocationDecl],
  catchClauses : ISeq[CatchClause])
    extends Body

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait LocalVar extends Annotable {
  def name() : NameDefinition
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class LocalVarDecl(
  typeSpec : Option[TypeSpec],
  name : NameDefinition,
  annotations : ISeq[Annotation])
    extends LocalVar

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class LocationDecl extends Annotable {
  def name : Option[NameDefinition]

  protected var _locationIndex : Int = -1

  def hasIndex = _locationIndex != -1

  def index(locIndex : Int) = {
    _locationIndex = locIndex
  }

  def index : Int = {
    assert(hasIndex)
    _locationIndex
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ActionLocation(
  name : Option[NameDefinition],
  annotations : ISeq[Annotation],
  action : Action)
    extends LocationDecl

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class JumpLocation(
  name : Option[NameDefinition],
  annotations : ISeq[Annotation],
  jump : Jump)
    extends LocationDecl

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class EmptyLocation(
  name : Option[NameDefinition],
  annotations : ISeq[Annotation])
    extends LocationDecl

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ComplexLocation(
  name : Option[NameDefinition],
  annotations : ISeq[Annotation],
  transformations : ISeq[Transformation])
    extends LocationDecl

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Transformation(
  annotations : ISeq[Annotation],
  guard : Option[Guard],
  actions : ISeq[Action],
  jump : Option[Jump])
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class Guard extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ExpGuard(
  annotations : ISeq[Annotation],
  cond : Exp)
    extends Guard

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ElseGuard(annotations : ISeq[Annotation])
  extends Guard

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Command extends Annotable {
  protected var _locationUri : Option[String] = None
  protected var _locationIndex : Int = -1
  protected var _transformationIndex : Int = -1
  protected var _commandIndex : Int = -1

  def hasCommandDescriptorInfo = _locationIndex != -1

  def commandDescriptorInfo(locUri : Option[ResourceUri], locIndex : Int,
                            transIndex : Int, commandIndex : Int) = {
    _locationUri = locUri
    _locationIndex = locIndex
    _transformationIndex = transIndex
    _commandIndex = commandIndex
  }

  def commandDescriptorInfo : (Option[ResourceUri], Int, Int, Int) = {
    assert(hasCommandDescriptorInfo)
    (_locationUri, _locationIndex, _transformationIndex, _commandIndex)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class Action extends Command

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class AssertAction(
  annotations : ISeq[Annotation],
  cond : Exp,
  message : Option[Exp])
    extends Action

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class AssumeAction(
  annotations : ISeq[Annotation],
  cond : Exp)
    extends Action

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ThrowAction(
  annotations : ISeq[Annotation],
  exp : Exp)
    extends Action

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed trait Assignment extends Command

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class AssignAction(
  annotations : ISeq[Annotation],
  lhs : Exp,
  op : String,
  rhs : Exp)
    extends Action with Assignment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class StartAction(
  annotations : ISeq[Annotation],
  name : NameUser,
  count : Option[Exp],
  arg : Option[Exp])
    extends Action

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ExtCallAction(
  annotations : ISeq[Annotation],
  callExp : CallExp)
    extends Action with Assignment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class Jump extends Command

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed trait Branch

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class GotoJump(
  annotations : ISeq[Annotation],
  target : NameUser)
    extends Jump with Branch

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ReturnJump(
  annotations : ISeq[Annotation],
  exp : Option[Exp])
    extends Jump with Branch

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class IfJump(
  annotations : ISeq[Annotation],
  ifThens : ISeq[IfThenJump],
  ifElse : Option[GotoJump])
    extends Jump

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class IfThenJump(
  cond : Exp,
  annotations : ISeq[Annotation],
  target : NameUser)
    extends Annotable with Branch

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class SwitchJump(
  annotations : ISeq[Annotation],
  cond : Exp,
  cases : ISeq[SwitchCaseJump],
  defaultCase : Option[GotoJump])
    extends Jump

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class SwitchCaseJump(
  cond : Exp,
  annotations : ISeq[Annotation],
  target : NameUser)
    extends Annotable with Branch

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class CallJump(
  annotations : ISeq[Annotation],
  lhs : Option[NameExp],
  callExp : CallExp,
  jump : Option[GotoJump])
    extends Jump with Assignment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class CatchClause(
  annotations : ISeq[Annotation],
  typeSpec : Option[TypeSpec],
  name : Option[NameUser],
  fromTarget : NameUser,
  toTarget : NameUser,
  jump : GotoJump)
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class Exp(var annotations : ISeq[Annotation] = ilistEmpty)
  extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class IfExp(
  ifThens : ISeq[IfThenExp],
  elseExp : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class IfThenExp(
  cond : Exp,
  annotations : ISeq[Annotation],
  exp : Exp)
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class SwitchExp(
  exp : Exp,
  cases : ISeq[SwitchCaseExp],
  defaultCase : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class SwitchCaseExp(
  cond : Exp,
  annotations : ISeq[Annotation],
  exp : Exp)
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class BinaryExp(
  op : BinaryOp,
  left : Exp,
  right : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class UnaryExp(
  op : UnaryOp,
  exp : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class CastExp(
  typeSpec : TypeSpec,
  exp : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class IndexingExp(
  exp : Exp,
  indices : ISeq[Exp])
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class AccessExp(
  exp : Exp,
  attributeName : NameUser)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class CallExp(
  exp : Exp,
  arg : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object LiteralType extends Enum {
  sealed abstract class Type extends EnumElem
  object BOOLEAN extends Type
  object NULL extends Type
  object INTEGER extends Type
  object INT extends Type
  object LONG extends Type
  object CHAR extends Type
  object FLOAT extends Type
  object DOUBLE extends Type
  object RATIONAL extends Type
  object STRING extends Type
  object SYMBOL extends Type
  object RAW extends Type

  def elements = List(BOOLEAN, NULL, INTEGER, INT, LONG, CHAR, FLOAT, DOUBLE,
    RATIONAL, STRING, SYMBOL, RAW)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class LiteralExp(
  typ : LiteralType.Type,
  literal : Any,
  text : String)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class TupleExp(
  exps : ISeq[Exp])
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NewExp(
  typeSpec : TypeSpec,
  dims : ISeq[ISeq[Exp]],
  typeFragments : ISeq[TypeFragment])
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class TypeFragment extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ArrayTypeFragment() extends TypeFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ListTypeFragment() extends TypeFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class MultiArrayTypeFragment(rank : Int)
  extends TypeFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class SetTypeFragment() extends TypeFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NameExp(name : NameUser)
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class TypeExp(typeSpec : TypeSpec)
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NewListRangedExp(
  lowRange : Exp,
  highRange : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NewListExp(elements : ISeq[Exp])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NewMultiArrayExp(elements : ISeq[MultiArrayFragment])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class MultiArrayFragment
  extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ExpMultiArrayFragment(exp : Exp)
  extends MultiArrayFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class MultiArrayMultiArrayFragment(fragments : ISeq[MultiArrayFragment])
  extends MultiArrayFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NewRecordExp(
  recordType : NamedTypeSpec,
  attributeInits : ISeq[AttributeInit])
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class AttributeInit(
  name : NameUser,
  exp : Exp)
    extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NewSetExp(elements : ISeq[Exp])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NewFunctionExp(elements : ISeq[(Exp, Exp)])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class FunExp(matchings : ISeq[Matching])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Matching(
  params : ISeq[ParamDecl],
  exp : Exp)
    extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class LetExp(
  bindings : ISeq[LetBinding],
  exp : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class LetBinding(
  names : ISeq[NameDefinition],
  exp : Exp)
    extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ValueExp(value : Value)
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ValuesExp(values : ISeq[Value])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ExternalExp(obj : Any)
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class TypeSpec(var annotations : ISeq[Annotation] = ilistEmpty)
  extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ProcedureTypeSpec(
  varArity : Boolean,
  typeParams : ISeq[TypeParam],
  returnType : Option[TypeSpec])
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class FunTypeSpec(
  varArity : Boolean,
  typeParams : ISeq[TypeParam],
  returnType : Option[TypeSpec])
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class TypeParam(
  typeSpec : TypeSpec,
  name : Option[String],
  annotations : ISeq[Annotation])
    extends Annotable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class TupleTypeSpec(elementTypes : ISeq[TypeParam])
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class FunctionTypeSpec(
  domainTypes : ISeq[TypeParam],
  imageType : TypeSpec)
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class RelationTypeSpec(elementTypes : ISeq[TypeParam])
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class SetTypeSpec(elementType : TypeSpec)
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ArrayTypeSpec(elementType : TypeSpec)
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ListTypeSpec(elementType : TypeSpec)
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class MultiArrayTypeSpec(
  elementType : TypeSpec,
  rank : Int)
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class TypeVarSpec(name : NameUser)
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NamedTypeSpec(
  name : NameUser,
  typeArgs : ISeq[TypeSpec])
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class NamedExtTypeSpec(
  extName : String,
  name : NameUser,
  typeArgs : ISeq[TypeSpec])
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object PilarOp {
  val BINOP_IMPLY = "==>"
  val BINOP_IMPLIED = "<=="
  val BINOP_OR = "|"
  val BINOP_AND = "&"
  val BINOP_BIT_OR = "^|"
  val BINOP_BIT_XOR = "^~"
  val BINOP_BIT_AND = "^&"
  val BINOP_EQ = "=="
  val BINOP_NEQ = "!="
  val BINOP_SUBTYPE = "<:"
  val BINOP_EQTYPE = "=:"
  val BINOP_SUPTYPE = ">:"
  val BINOP_LE = "<="
  val BINOP_GE = ">="
  val BINOP_LT = "<"
  val BINOP_GT = ">"
  val BINOP_SHL = "^<"
  val BINOP_SHR = "^>"
  val BINOP_USHR = "^>>"
  val BINOP_ADD = "+"
  val BINOP_SUB = "-"
  val BINOP_MUL = "*"
  val BINOP_DIV = "/"
  val BINOP_REM = "%"
  val UNOP_PLUS = "+"
  val UNOP_MINUS = "-"
  val UNOP_COMPLEMENT = "~"
  val UNOP_NOT = "!"
  val UNOP_MUL = "*"
  val UNOP_DIV = "/"
  val UNOP_REM = "%"
}