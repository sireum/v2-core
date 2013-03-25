/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
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
object PilarAstNode {
  trait XStreamer extends Location.XStreamer {
    self : org.sireum.util.XStreamer =>
    self.aliasPackage("ast", "org.sireum.pilar.ast")
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed trait PilarAstNode extends PropertyProviderInitLinked

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class Name
    extends PilarAstNode {
  def name : String
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NameDefinition(name : String)
  extends Name with SymbolDefinition

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NameUser(name : String)
  extends Name with SymbolUser

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed trait Annotable[T <: Annotable[T]] extends PilarAstNode {
  private lazy val markerCache = computeMarkerAnnotationCache
  private lazy val valueCache = computeValueAnnotationCache
  private lazy val annotationCache = computeAnnotationCache
  private var markerCacheInitialized = false

  def annotations : ISeq[Annotation]

  def make(anns : ISeq[Annotation]) : T

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
sealed trait AnnotableProperty[T <: AnnotableProperty[T]]
    extends Product with Annotable[T] {
  private val annPropKey = ".annotations"

  def annotations : ISeq[Annotation] =
    this.getPropertyOrElse(annPropKey, ivectorEmpty)

  def annotations_=(anns : ISeq[Annotation]) {
    this(annPropKey) = anns
  }

  def make(anns : ISeq[Annotation]) : T = {
    val elements = new Array[Object](productArity)
    for (i <- 0 until elements.length) {
      elements(i) = productElement(i).asInstanceOf[Object]
    }
    val r = ProductUtil.make(getClass, elements : _*)
    r.propertyMap ++= propertyMap
    r.annotations = anns
    r.asInstanceOf[T]
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class Model(
  sourceURI : Option[FileResourceUri],
  annotations : ISeq[Annotation],
  packages : ISeq[PackageDecl])
    extends Annotable[Model] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class Annotation(
  name : NameUser,
  params : ISeq[AnnotationParam])
    extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class AnnotationParam extends PilarAstNode {
  def name : Option[NameUser]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class AnnotationAnnotationParam(
  name : Option[NameUser],
  annotation : Annotation)
    extends AnnotationParam

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ExpAnnotationParam(
  name : Option[NameUser],
  exp : Exp)
    extends AnnotationParam

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class PackageDecl(
  name : Option[NameDefinition],
  annotations : ISeq[Annotation],
  elements : ISeq[PackageElement])
    extends Annotable[PackageDecl] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class PackageElement extends Annotable[PackageElement] {
  def name : NameDefinition
  def annotations : ISeq[Annotation]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ConstDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  elements : ISeq[ConstElement])
    extends PackageElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ConstElement(
  name : NameDefinition,
  exp : Exp,
  annotations : ISeq[Annotation])
    extends Annotable[ConstElement] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class EnumDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  elements : ISeq[EnumElement])
    extends PackageElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class EnumElement(
  name : NameDefinition,
  annotations : ISeq[Annotation])
    extends Annotable[EnumElement] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class TypeAliasDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeSpec : TypeSpec)
    extends PackageElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class RecordDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  extendsClauses : ISeq[ExtendClause],
  attributes : ISeq[AttributeDecl])
    extends PackageElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ExtendClause(
  name : NameUser,
  annotations : ISeq[Annotation],
  typeArgs : ISeq[TypeSpec])
    extends Annotable[ExtendClause] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class AttributeDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeSpec : Option[TypeSpec],
  binding : Option[NameUser])
    extends Annotable[AttributeDecl] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class GlobalVarDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeSpec : Option[TypeSpec])
    extends PackageElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ProcedureDecl(
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

  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ParamDecl(
  typeSpec : Option[TypeSpec],
  name : NameDefinition,
  annotations : ISeq[Annotation])
    extends LocalVar {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class VSetDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  elements : ISeq[VSetElement])
    extends PackageElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class VSetElement(
  name : NameUser,
  annotations : ISeq[Annotation])
    extends Annotable[VSetElement] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class FunDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  matchings : ISeq[Matching])
    extends PackageElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ExtensionDecl(
  name : NameDefinition,
  annotations : ISeq[Annotation],
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  elements : ISeq[ExtElement])
    extends PackageElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class ExtElement extends Annotable[ExtElement] {
  def typeVars : ISeq[(NameDefinition, ISeq[Annotation])]
  def name : NameDefinition
  def annotations : ISeq[Annotation]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class TypeExtensionDecl(
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  name : NameDefinition,
  annotations : ISeq[Annotation],
  extendClauses : ISeq[ExtendClause],
  elements : ISeq[TypeExtElement])
    extends ExtElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class TypeExtElement extends ExtElement

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class TypeExtAttributeBinding(
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  name : NameDefinition,
  annotations : ISeq[Annotation],
  extName : NameUser)
    extends TypeExtElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class ParameterizedTypeExtElement extends TypeExtElement {
  def params : ISeq[ExtParam]
  def varArity : Boolean
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ActionExtensionDecl(
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  name : NameDefinition,
  annotations : ISeq[Annotation],
  params : ISeq[ExtParam],
  varArity : Boolean)
    extends ParameterizedTypeExtElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ExpExtensionDecl(
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  name : NameDefinition,
  annotations : ISeq[Annotation],
  params : ISeq[ExtParam],
  varArity : Boolean,
  returnTypeSpec : Option[TypeSpec])
    extends ParameterizedTypeExtElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ProcedureExtensionDecl(
  typeVars : ISeq[(NameDefinition, ISeq[Annotation])],
  name : NameDefinition,
  annotations : ISeq[Annotation],
  params : ISeq[ExtParam],
  varArity : Boolean,
  returnTypeSpec : Option[TypeSpec])
    extends ParameterizedTypeExtElement {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ExtParam(
  typeSpec : Option[TypeSpec],
  name : Option[NameDefinition],
  annotations : ISeq[Annotation])
    extends Annotable[ExtParam] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class Body extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class EmptyBody() extends Body

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ImplementedBody(
  locals : ISeq[LocalVarDecl],
  locations : ISeq[LocationDecl],
  catchClauses : ISeq[CatchClause])
    extends Body

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed trait LocalVar extends Annotable[LocalVar] {
  def name() : NameDefinition
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class LocalVarDecl(
  typeSpec : Option[TypeSpec],
  name : NameDefinition,
  annotations : ISeq[Annotation])
    extends LocalVar {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class LocationDecl extends Annotable[LocationDecl] {
  private val propKey = classOf[LocationDecl].getName

  def name : Option[NameDefinition]

  def hasIndex = this ? propKey

  def index(locIndex : Int) { this(propKey) = locIndex }

  def index : Int = { require(hasIndex); this(propKey) }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ActionLocation(
  name : Option[NameDefinition],
  annotations : ISeq[Annotation],
  action : Action)
    extends LocationDecl {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class JumpLocation(
  name : Option[NameDefinition],
  annotations : ISeq[Annotation],
  jump : Jump)
    extends LocationDecl {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class EmptyLocation(
  name : Option[NameDefinition],
  annotations : ISeq[Annotation])
    extends LocationDecl
 {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ComplexLocation(
  name : Option[NameDefinition],
  annotations : ISeq[Annotation],
  transformations : ISeq[Transformation])
    extends LocationDecl {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class Transformation(
  annotations : ISeq[Annotation],
  guard : Option[Guard],
  actions : ISeq[Action],
  jump : Option[Jump])
    extends Annotable[Transformation] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class Guard extends Annotable[Guard]

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ExpGuard(
  annotations : ISeq[Annotation],
  cond : Exp)
    extends Guard {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ElseGuard(annotations : ISeq[Annotation])
  extends Guard {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed trait Command extends Annotable[Command] {
  private val propKey = classOf[Command].getName
  def hasCommandDescriptorInfo = this ? propKey

  def commandDescriptorInfo(locUri : Option[ResourceUri], locIndex : Int,
                            transIndex : Int, commandIndex : Int) {
    this(propKey) =
      Command.CommandInfo(locUri, locIndex, transIndex, commandIndex)
  }

  def commandDescriptorInfo : (Option[ResourceUri], Int, Int, Int) = {
    require(hasCommandDescriptorInfo)
    val ci = commandInfo
    (ci.locUri, ci.locIndex, ci.transIndex, ci.commandIndex)
  }

  def locUri = { require(hasCommandDescriptorInfo); commandInfo.locUri }
  def locIndex = { require(hasCommandDescriptorInfo); commandInfo.locIndex }
  def transIndex = { require(hasCommandDescriptorInfo); commandInfo.transIndex }
  def commandIndex = { require(hasCommandDescriptorInfo); commandInfo.commandIndex }

  private def commandInfo : Command.CommandInfo = this(propKey)
}

object Command {
  private final case class CommandInfo(
    locUri : Option[ResourceUri], locIndex : Int,
    transIndex : Int, commandIndex : Int)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class Action extends Command

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class AssertAction(
  annotations : ISeq[Annotation],
  cond : Exp,
  message : Option[Exp])
    extends Action {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class AssumeAction(
  annotations : ISeq[Annotation],
  cond : Exp)
    extends Action {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ThrowAction(
  annotations : ISeq[Annotation],
  exp : Exp)
    extends Action {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed trait Assignment extends Command

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class AssignAction(
  annotations : ISeq[Annotation],
  lhs : Exp,
  op : String,
  rhs : Exp)
    extends Action with Assignment {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class StartAction(
  annotations : ISeq[Annotation],
  name : NameUser,
  count : Option[Exp],
  arg : Option[Exp])
    extends Action {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ExtCallAction(
  annotations : ISeq[Annotation],
  callExp : CallExp)
    extends Action with Assignment {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class Jump extends Command

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed trait Branch

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class GotoJump(
  annotations : ISeq[Annotation],
  target : NameUser)
    extends Jump with Branch {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ReturnJump(
  annotations : ISeq[Annotation],
  exp : Option[Exp])
    extends Jump with Branch {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class IfJump(
  annotations : ISeq[Annotation],
  ifThens : ISeq[IfThenJump],
  ifElse : Option[GotoJump])
    extends Jump {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class IfThenJump(
  cond : Exp,
  annotations : ISeq[Annotation],
  target : NameUser)
    extends Annotable[IfThenJump] with Branch {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class SwitchJump(
  annotations : ISeq[Annotation],
  cond : Exp,
  cases : ISeq[SwitchCaseJump],
  defaultCase : Option[GotoJump])
    extends Jump {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class SwitchCaseJump(
  cond : Exp,
  annotations : ISeq[Annotation],
  target : NameUser)
    extends Annotable[SwitchCaseJump] with Branch {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class CallJump(
  annotations : ISeq[Annotation],
  lhs : Option[NameExp],
  callExp : CallExp,
  jump : Option[GotoJump])
    extends Jump with Assignment {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class CatchClause(
  annotations : ISeq[Annotation],
  typeSpec : Option[TypeSpec],
  name : Option[NameUser],
  fromTarget : NameUser,
  toTarget : NameUser,
  jump : GotoJump)
    extends Annotable[CatchClause] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class Exp extends AnnotableProperty[Exp]

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class IfExp(
  ifThens : ISeq[IfThenExp],
  elseExp : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class IfThenExp(
  cond : Exp,
  annotations : ISeq[Annotation],
  exp : Exp)
    extends Annotable[IfThenExp] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class SwitchExp(
  exp : Exp,
  cases : ISeq[SwitchCaseExp],
  defaultCase : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class SwitchCaseExp(
  cond : Exp,
  annotations : ISeq[Annotation],
  exp : Exp)
    extends Annotable[SwitchCaseExp] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class BinaryExp(
  op : BinaryOp,
  left : Exp,
  right : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class UnaryExp(
  op : UnaryOp,
  exp : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class CastExp(
  typeSpec : TypeSpec,
  exp : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class IndexingExp(
  exp : Exp,
  indices : ISeq[Exp])
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class AccessExp(
  exp : Exp,
  attributeName : NameUser)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class CallExp(
  exp : Exp,
  arg : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class LiteralExp(
  typ : LiteralType,
  literal : Any,
  text : String)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class TupleExp(
  exps : ISeq[Exp])
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NewExp(
  typeSpec : TypeSpec,
  dims : ISeq[ISeq[Exp]],
  typeFragments : ISeq[TypeFragment])
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class TypeFragment extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ArrayTypeFragment() extends TypeFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ListTypeFragment() extends TypeFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class MultiArrayTypeFragment(rank : Int)
  extends TypeFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class SetTypeFragment() extends TypeFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NameExp(name : NameUser)
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class TypeExp(typeSpec : TypeSpec)
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NewListRangedExp(
  lowRange : Exp,
  highRange : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NewListExp(elements : ISeq[Exp])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NewMultiArrayExp(elements : ISeq[MultiArrayFragment])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class MultiArrayFragment
  extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ExpMultiArrayFragment(exp : Exp)
  extends MultiArrayFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class MultiArrayMultiArrayFragment(fragments : ISeq[MultiArrayFragment])
  extends MultiArrayFragment

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NewRecordExp(
  recordType : NamedTypeSpec,
  attributeInits : ISeq[AttributeInit])
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class AttributeInit(
  name : NameUser,
  exp : Exp)
    extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NewSetExp(elements : ISeq[Exp])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NewFunctionExp(elements : ISeq[(Exp, Exp)])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class FunExp(matchings : ISeq[Matching])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class Matching(
  params : ISeq[ParamDecl],
  exp : Exp)
    extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class LetExp(
  bindings : ISeq[LetBinding],
  exp : Exp)
    extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class LetBinding(
  names : ISeq[NameDefinition],
  exp : Exp)
    extends PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ValueExp(value : Value)
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ValuesExp(values : ISeq[Value])
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ExternalExp(obj : Any)
  extends Exp

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
sealed abstract class TypeSpec extends AnnotableProperty[TypeSpec]

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ProcedureTypeSpec(
  varArity : Boolean,
  typeParams : ISeq[TypeParam],
  returnType : Option[TypeSpec])
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class FunTypeSpec(
  varArity : Boolean,
  typeParams : ISeq[TypeParam],
  returnType : Option[TypeSpec])
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class TypeParam(
  typeSpec : TypeSpec,
  name : Option[String],
  annotations : ISeq[Annotation])
    extends Annotable[TypeParam] {
  def make(anns : ISeq[Annotation]) = copy(annotations = anns)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class TupleTypeSpec(elementTypes : ISeq[TypeParam])
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class FunctionTypeSpec(
  domainTypes : ISeq[TypeParam],
  imageType : TypeSpec)
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class RelationTypeSpec(elementTypes : ISeq[TypeParam])
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class SetTypeSpec(elementType : TypeSpec)
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ArrayTypeSpec(elementType : TypeSpec)
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ListTypeSpec(elementType : TypeSpec)
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class MultiArrayTypeSpec(
  elementType : TypeSpec,
  rank : Int)
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class TypeVarSpec(name : NameUser)
  extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NamedTypeSpec(
  name : NameUser,
  typeArgs : ISeq[TypeSpec])
    extends TypeSpec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class NamedExtTypeSpec(
  extName : String,
  name : NameUser,
  typeArgs : ISeq[TypeSpec])
    extends TypeSpec
