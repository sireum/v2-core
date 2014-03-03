/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.parser

import org.antlr.v4.runtime.tree.ParseTree

import org.sireum.pilar.ast._
import org.sireum.util._
import Antlr4PilarParser._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Antlr4PilarParserVisitor {
  def apply[T <: PilarAstNode](
    src : Option[FileResourceUri], t : ParseTree,
    reporter : Parser.ErrorReporter, storeLoc : Boolean) =
    new Antlr4PilarParserVisitor(reporter, src, storeLoc).
      visit(t).asInstanceOf[T]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class Antlr4PilarParserVisitor(
  reporter : Parser.ErrorReporter,
  src : Option[FileResourceUri], storeLoc : Boolean)
    extends Antlr4PilarBaseVisitor[PilarAstNode]
    with Antlr4.Visitor[PilarAstNode] {

  implicit val source = src
  implicit val storeLocation = storeLoc

  import Antlr4._
  import scala.collection.JavaConversions._

  override def visitModelFile(ctx : ModelFileContext) =
    getChild[Model](ctx.model) at (ctx)

  override def visitAnnotationFile(ctx : AnnotationFileContext) =
    getChild[Annotation](ctx.annotation) at (ctx)

  override def visitLocationFile(ctx : LocationFileContext) =
    getChild[LocationDecl](ctx.location) at (ctx)

  override def visitTransformationFile(ctx : TransformationFileContext) =
    getChild[Transformation](ctx.transformation) at (ctx)

  override def visitActionFile(ctx : ActionFileContext) =
    getChild[Action](ctx.action) at (ctx)

  override def visitJumpFile(ctx : JumpFileContext) =
    getChild[Jump](ctx.jump) at (ctx)

  override def visitExpFile(ctx : ExpFileContext) =
    getChild[Exp](ctx.exp) at (ctx)

  override def visitTypeFile(ctx : TypeFileContext) =
    getChild[TypeSpec](ctx.`type`) at (ctx)

  override def visitModel(ctx : ModelContext) =
    Model(source, getChildren(ctx.annotation),
      ctx.modelElement.toVector.flatMap {
        _.getChild(0) match {
          case me : GlobalVarsDeclarationContext =>
            me.globalVarDeclaration.toVector.flatMap(globalVarDeclaration)
          case me =>
            ivector(getChild[ModelElement](me))
        }
      }
    ) at ctx

  override def visitAnnotation(ctx : AnnotationContext) =
    Annotation(nameUser(ctx.ID),
      ctx.annotationParams match {
        case null => ivectorEmpty
        case ap : AnnotationParamsAContext =>
          getChildren(ap.annotationParam)
        case ap : AnnotationParamsEContext =>
          getChildren(ap.exp).map { e : Exp =>
            ExpAnnotationParam(None, e) at e
          }
      }) at ctx

  override def visitAnnotationParamA(ctx : AnnotationParamAContext) =
    AnnotationAnnotationParam(None, getChild(ctx.annotation)) at ctx

  override def visitAnnotationParamE(ctx : AnnotationParamEContext) =
    ExpAnnotationParam(None, getChild(ctx.exp)) at ctx

  override def visitAnnotationParamIA(ctx : AnnotationParamIAContext) =
    AnnotationAnnotationParam(Some(nameUser(ctx.ID)),
      getChild(ctx.annotation)) at ctx

  override def visitAnnotationParamIE(ctx : AnnotationParamIEContext) =
    ExpAnnotationParam(Some(nameUser(ctx.ID)), getChild(ctx.exp)) at ctx

  override def visitConstDeclaration(ctx : ConstDeclarationContext) =
    ConstDecl(nameDef(ctx.ID), getChildren(ctx.annotation),
      getChildren(ctx.constElement)) at ctx

  override def visitConstElement(ctx : ConstElementContext) =
    ConstElement(nameDef(ctx.ID), getChild(ctx.constant),
      getChildren(ctx.annotation)) at ctx

  override def visitEnumDeclaration(ctx : EnumDeclarationContext) =
    EnumDecl(nameDef(ctx.ID), getChildren(ctx.annotation),
      getChildren(ctx.enumElement)) at ctx

  override def visitEnumElement(ctx : EnumElementContext) =
    EnumElement(nameDef(ctx.ID), getChildren(ctx.annotation)) at ctx

  override def visitTypealiasDeclaration(ctx : TypealiasDeclarationContext) =
    TypeAliasDecl(nameDef(ctx.ID), getChildren(ctx.annotation),
      getChild(ctx.`type`)) at ctx

  override def visitRecordDeclaration(ctx : RecordDeclarationContext) =
    RecordDecl(nameDef(ctx.ID), getChildren(ctx.annotation),
      typeVarTuple(ctx.typeVarTuple), extendClauses(ctx.extendClauses),
      ctx.field.toVector.flatMap(field)) at ctx

  override def visitProcedureDeclaration(ctx : ProcedureDeclarationContext) =
    ProcedureDecl(nameDef(ctx.ID), getChildren(ctx.annotation),
      typeVarTuple(ctx.typeVarTuple), getChildren(ctx.paramVar),
      getOptChild(ctx.`type`), false, getChild(ctx.body)) at ctx

  override def visitParamVar(ctx : ParamVarContext) =
    ParamDecl(getOptChild(ctx.`type`), nameDef(ctx.ID),
      getChildren(ctx.annotation)) at ctx

  override def visitFunDeclaration(ctx : FunDeclarationContext) =
    FunDecl(nameDef(ctx.ID), getChildren(ctx.annotation),
      typeVarTuple(ctx.typeVarTuple),
      getChildren(ctx.paramVar), getOptChild(ctx.`type`),
      getChild(ctx.exp), ivectorEmpty) at ctx

  override def visitExtDeclaration(ctx : ExtDeclarationContext) =
    ExtensionDecl(nameDef(ctx.ID), getChildren(ctx.annotation),
      typeVarTuple(ctx.typeVarTuple), getChildren(ctx.extElement)) at ctx

  override def visitTypeExtension(ctx : TypeExtensionContext) =
    TypeExtensionDecl(typeVarTuple(ctx.typeVarTuple), nameDef(ctx.ID),
      getChildren(ctx.annotation), extendClauses(ctx.extendClauses),
      ivectorEmpty) at ctx

  override def visitActionExtension(ctx : ActionExtensionContext) = {
    val (params, varArity) = extParams(ctx.extParams)
    ActionExtensionDecl(typeVarTuple(ctx.typeVarTuple), nameDef(ctx.ID),
      getChildren(ctx.annotation), params, varArity) at ctx
  }

  override def visitExpExtension(ctx : ExpExtensionContext) = {
    val (params, varArity) = extParams(ctx.extParams)
    ExpExtensionDecl(typeVarTuple(ctx.typeVarTuple), nameDef(ctx.ID),
      getChildren(ctx.annotation), params, varArity,
      getOptChild(ctx.`type`)) at ctx
  }

  override def visitProcedureExtension(ctx : ProcedureExtensionContext) = {
    val (params, varArity) = extParams(ctx.extParams)
    ProcedureExtensionDecl(typeVarTuple(ctx.typeVarTuple), nameDef(ctx.ID),
      getChildren(ctx.annotation), params, varArity,
      getOptChild(ctx.`type`)) at ctx
  }

  override def visitExtParam(ctx : ExtParamContext) =
    ExtParam(getOptChild(ctx.`type`), nameDefOpt(ctx.ID),
      getChildren(ctx.annotation)) at ctx

  override def visitExtParamVariable(ctx : ExtParamVariableContext) =
    ExtParam(getOptChild(ctx.`type`), nameDefOpt(ctx.ID),
      getChildren(ctx.annotation)) at ctx

  override def visitImplementedBody(ctx : ImplementedBodyContext) =
    ImplementedBody(localVarsDeclaration(ctx.localVarsDeclaration),
      getChildren(ctx.location), getChildren(ctx.catchClause)) at ctx

  override def visitEmptyBody(ctx : EmptyBodyContext) = EmptyBody() at ctx

  override def visitLocation(ctx : LocationContext) = {
    val ts : ISeq[Transformation] = getChildren(ctx.transformation)
    ts match {
      case Seq() =>
        EmptyLocation(locNameDef(ctx.LID),
          getChildren(ctx.annotation)) at ctx
      case Seq(t) if t.guard.isEmpty && t.annotations.isEmpty =>
        if (t.actions.isEmpty)
          if (t.jump.isDefined)
            JumpLocation(locNameDef(ctx.LID),
              getChildren(ctx.annotation), t.jump.get) at ctx
          else
            EmptyLocation(locNameDef(ctx.LID),
              getChildren(ctx.annotation)) at ctx
        else if (t.actions.size == 1 && t.jump.isEmpty)
          ActionLocation(locNameDef(ctx.LID),
            getChildren(ctx.annotation), t.actions(0)) at ctx
        else
          ComplexLocation(locNameDef(ctx.LID),
            getChildren(ctx.annotation), ts)
      case _ =>
        ComplexLocation(locNameDef(ctx.LID),
          getChildren(ctx.annotation), ts)
    }
  }

  override def visitCallTransformation(ctx : CallTransformationContext) = {
    val op = ctx.AssignOP
    if (op != null && op.getText != ":=") {
      val line = op.getSymbol.getLine
      val column = op.getSymbol.getCharPositionInLine
      reporter.report(source,
        line, column, "Only := is allowed instead of: " + op.getText)
    }
    Transformation(getChildren(ctx.tanns), getOptChild(ctx.guard),
      ivectorEmpty, Some(
        CallJump(
          getChildren(ctx.cans),
          ctx.clhs.toVector.map { id =>
            NameExp(nameUser(id)) at id
          }, CallExp(NameExp(nameUser(ctx.p)) at ctx.p,
            getChild(ctx.tupleExp)), getOptChild(ctx.gotoj))
      )) at ctx
  }

  override def visitBlockTransformation(ctx : BlockTransformationContext) =
    Transformation(getChildren(ctx.tanns), getOptChild(ctx.guard),
      getChildren(ctx.action), getOptChild(ctx.jump)) at ctx

  override def visitExpGuard(ctx : ExpGuardContext) =
    ExpGuard(getChildren(ctx.annotation), getChild(ctx.exp)) at ctx

  override def visitElseGuard(ctx : ElseGuardContext) =
    ElseGuard(getChildren(ctx.annotation)) at ctx

  override def visitAssert(ctx : AssertContext) =
    AssertAction(getChildren(ctx.annotation), getChild(ctx.exp(0)),
      if (ctx.exp.size > 1) getOptChild(ctx.exp(1)) else None) at ctx

  override def visitAssume(ctx : AssumeContext) =
    AssumeAction(getChildren(ctx.annotation), getChild(ctx.exp(0)),
      if (ctx.exp.size > 1) getOptChild(ctx.exp(1)) else None) at ctx

  override def visitThrow(ctx : ThrowContext) =
    ThrowAction(getChildren(ctx.annotation), getChild(ctx.exp)) at ctx

  override def visitStart(ctx : StartContext) =
    StartAction(getChildren(ctx.annotation), nameUser(ctx.ID),
      getOptChild(ctx.n), getChild(ctx.tupleExp)) at ctx

  override def visitActionExtCall(ctx : ActionExtCallContext) =
    ExtCallAction(getChildren(ctx.annotation),
      CallExp(NameExp(nameUser(ctx.ID)) at ctx.ID,
        getChild(ctx.tupleExp))) at ctx

  override def visitAssign(ctx : AssignContext) = {
    val rhs =
      getChildren[Exp, RhsContext](ctx.rhs) match {
        case Seq(e : Exp)   => e
        case es : ISeq[Exp] => TupleExp(es)
      }
    AssignAction(getChildren(ctx.annotation),
      TupleExp(ctx.lhss.lhs.toVector.map(visitLhs)) at ctx.lhss,
      ctx.AssignOP.getText, rhs) at ctx
  }

  override def visitLhs(ctx : LhsContext) = {
    val e = getChild[Exp](ctx.exp)
    e.annotations = getChildren(ctx.annotation)
    e at ctx
  }

  override def visitRhs(ctx : RhsContext) = {
    val e = getChild[Exp](ctx.exp)
    e.annotations = getChildren(ctx.annotation)
    e at ctx
  }

  override def visitGotoj(ctx : GotojContext) =
    GotoJump(getChildren(ctx.annotation), nameUser(ctx.ID)) at ctx

  override def visitReturnJump(ctx : ReturnJumpContext) =
    ReturnJump(getChildren(ctx.annotation),
      if (ctx.exp.size == 0) None
      else Some(TupleExp(getChildren(ctx.exp)))) at ctx

  override def visitIfJump(ctx : IfJumpContext) =
    IfJump(getChildren(ctx.annotation), getChildren(ctx.ifThenJump),
      getOptChild(ctx.ifElseJump)) at ctx

  override def visitIfThenJump(ctx : IfThenJumpContext) =
    IfThenJump(getChild(ctx.exp), getChildren(ctx.annotation),
      nameUser(ctx.ID)) at ctx

  override def visitIfElseJump(ctx : IfElseJumpContext) =
    GotoJump(getChildren(ctx.annotation), nameUser(ctx.ID)) at ctx

  override def visitSwitchJump(ctx : SwitchJumpContext) =
    SwitchJump(getChildren(ctx.annotation), getChild(ctx.exp),
      getChildren(ctx.switchCaseJump),
      getOptChild(ctx.switchDefaultJump)) at ctx

  override def visitSwitchCaseJump(ctx : SwitchCaseJumpContext) =
    SwitchCaseJump(getChild(ctx.constant), getChildren(ctx.annotation),
      nameUser(ctx.ID)) at ctx

  override def visitSwitchDefaultJump(ctx : SwitchDefaultJumpContext) =
    GotoJump(getChildren(ctx.annotation), nameUser(ctx.ID)) at ctx

  override def visitCatchClause(ctx : CatchClauseContext) =
    CatchClause(getChildren(ctx.annotation), getOptChild(ctx.`type`),
      nameUserOpt(ctx.`var`), nameUser(ctx.from), nameUser(ctx.to),
      getChild(ctx.gotoj)) at ctx

  override def visitPrimaryExp(ctx : PrimaryExpContext) = {
    var e = getChild[Exp](ctx.primary)
    for (ps <- ctx.primarySuffix) {
      val se =
        ps match {
          case ps : IndexingSuffixContext =>
            IndexingExp(e, getChildren(ps.exp)) at ps
          case ps : AccessSuffixContext =>
            AccessExp(e, nameUser(ps.ID)) at ps
          case ps : CallSuffixContext =>
            CallExp(e, getChild(ps.tupleExp)) at ps
        }
      e = se at (e, se)
    }
    e.at(ctx)
  }

  override def visitCastExp(ctx : CastExpContext) =
    CastExp(getChild(ctx.`type`), getChild(ctx.exp)) at ctx

  override def visitUnaryExp(ctx : UnaryExpContext) =
    UnaryExp(op(ctx.op), getChild(ctx.exp)) at ctx

  override def visitBinaryExp(ctx : BinaryExpContext) =
    BinaryExp(op(ctx.op), getChild(ctx.exp(0)),
      getChild(ctx.exp(1))) at ctx

  override def visitIfExp(ctx : IfExpContext) =
    IfExp(getChildren(ctx.ifThenExp), getChild(ctx.ifElseExp)) at ctx

  override def visitIfThenExp(ctx : IfThenExpContext) =
    IfThenExp(getChild(ctx.exp(0)), getChildren(ctx.annotation),
      getChild(ctx.exp(1))) at ctx

  override def visitIfElseExp(ctx : IfElseExpContext) = {
    val e = getChild[Exp](ctx.exp)
    e.annotations = getChildren(ctx.annotation)
    e at ctx
  }

  override def visitNameExp(ctx : NameExpContext) =
    NameExp(nameUser(ctx.ID)) at ctx

  override def visitGlobalNameExp(ctx : GlobalNameExpContext) =
    NameExp(nameUser(ctx.GID)) at ctx

  override def visitLetExp(ctx : LetExpContext) =
    LetExp(getChildren(ctx.binding), getChild(ctx.exp)) at ctx

  override def visitRangedListExp(ctx : RangedListExpContext) =
    NewListRangedExp(getChild(ctx.exp(0)), getChild(ctx.exp(1))) at ctx

  override def visitListExp(ctx : ListExpContext) =
    NewListExp(getChildren(ctx.exp)) at ctx

  override def visitSetExp(ctx : SetExpContext) =
    NewSetExp(getChildren(ctx.exp)) at ctx

  override def visitMapExp(ctx : MapExpContext) =
    NewFunctionExp(ctx.mapping.toVector.map { m =>
      (getChild(m.exp(0)), getChild(m.exp(1)))
    }) at ctx

  override def visitMultiSeqExp(ctx : MultiSeqExpContext) =
    NewMultiSeqExp(getChildren(ctx.newMultiSeqFragment)) at ctx

  override def visitClosureExp(ctx : ClosureExpContext) =
    FunExp(getChildren(ctx.matching)) at ctx

  override def visitRecordExp(ctx : RecordExpContext) =
    NewRecordExp(
      NamedTypeSpec(nameUser(ctx.ID), typeTuple(ctx.typeTuple)) at ctx.ID,
      getChildren(ctx.fieldInit)) at ctx

  override def visitArrayExp(ctx : ArrayExpContext) =
    NewExp(getChild(ctx.baseType),
      ctx.newMultiSeqTypeFragment.toVector.map { m => getChildren(m.exp) },
      getChildren(ctx.typeFragment)) at ctx

  override def visitTypeExp(ctx : TypeExpContext) =
    TypeExp(getChild(ctx.`type`)) at ctx

  override def visitTupleExp(ctx : TupleExpContext) =
    TupleExp(getChildren(ctx.annExp)) at ctx

  override def visitAnnExp(ctx : AnnExpContext) = {
    val e = getChild[Exp](ctx.exp)
    e.annotations = getChildren(ctx.annotation)
    e at ctx
  }

  override def visitBinding(ctx : BindingContext) =
    LetBinding(ctx.ID.toVector.map(nameDef), getChild(ctx.exp)) at ctx

  override def visitNewMultiSeqFragment(ctx : NewMultiSeqFragmentContext) =
    MultiSeqMultiSeqFragment(getChildren(ctx.newMultiSeqFragmentE)) at ctx

  override def visitNewMultiSeqFragmentEExp(ctx : NewMultiSeqFragmentEExpContext) =
    ExpMultiSeqFragment(getChild(ctx.exp)) at ctx

  override def visitMatching(ctx : MatchingContext) =
    Matching(getChildren(ctx.paramVar), getChild(ctx.exp)) at ctx

  override def visitFieldInit(ctx : FieldInitContext) =
    AttributeInit(nameUser(ctx.ID), getChild(ctx.exp)) at ctx

  override def visitType(ctx : TypeContext) = {
    var t = getChild[TypeSpec](ctx.baseType)
    for (tf <- ctx.typeFragment) {
      val f =
        tf match {
          case tf : SeqFragmentContext =>
            SeqTypeSpec(t) at tf
          case tf : StaticSeqFragmentContext =>
            StaticSeqTypeSpec(t, getChild(tf.constant)) at tf
          case tf : MultiSeqFragmentContext =>
            MultiSeqTypeSpec(t, tf.rank.size) at tf
          case tf : StaticMultiSeqFragmentContext =>
            StaticMultiSeqTypeSpec(t, getChildren(tf.constant)) at tf
          case tf : SetFragmentContext =>
            SetTypeSpec(t) at tf
        }
      t = f at (t, f)
    }
    t
  }

  override def visitSeqFragment(ctx : SeqFragmentContext) =
    SeqTypeFragment() at ctx

  override def visitStaticSeqFragment(ctx : StaticSeqFragmentContext) = {
    val line = ctx.getStart.getLine
    val column = ctx.getStart.getCharPositionInLine
    reporter.report(source,
      line, column, "Seq fragment with constant is not allowed here")
    SeqTypeFragment() at ctx
  }

  override def visitMultiSeqFragment(ctx : MultiSeqFragmentContext) =
    MultiSeqTypeFragment(ctx.rank.size) at ctx

  override def visitStaticMultiSeqFragment(ctx : StaticMultiSeqFragmentContext) = {
    val line = ctx.getStart.getLine
    val column = ctx.getStart.getCharPositionInLine
    reporter.report(source,
      line, column, "Multi Seq fragment with constant is not allowed here")
    MultiSeqTypeFragment(ctx.constant.size) at ctx
  }

  override def visitSetFragment(ctx : SetFragmentContext) =
    SetTypeFragment() at ctx

  override def visitTrueConstant(ctx : TrueConstantContext) =
    LiteralExp(LiteralType.BOOLEAN, true, "true") at ctx

  override def visitFalseConstant(ctx : FalseConstantContext) =
    LiteralExp(LiteralType.BOOLEAN, false, "false") at ctx

  override def visitNullConstant(ctx : NullConstantContext) =
    LiteralExp(LiteralType.NULL, null, "null")

  override def visitIdConstant(ctx : IdConstantContext) =
    NameExp(nameUser(ctx.ID)) at ctx

  override def visitCharConstant(ctx : CharConstantContext) = {
    import org.apache.commons.lang3.StringEscapeUtils
    val s = ctx.getText
    val text = s.substring(1, s.size - 1)
    val c = StringEscapeUtils.unescapeJava(text).charAt(0)
    LiteralExp(LiteralType.CHAR, c, text) at ctx
  }

  override def visitHexConstant(ctx : HexConstantContext) = int(ctx)

  override def visitOctConstant(ctx : OctConstantContext) = int(ctx)

  override def visitDecConstant(ctx : DecConstantContext) = int(ctx)

  override def visitBinConstant(ctx : BinConstantContext) = int(ctx)

  override def visitFloatConstant(ctx : FloatConstantContext) = {
    val s = ctx.getText

    val line = ctx.getStart.getLine
    val column = ctx.getStart.getCharPositionInLine

    (if (s.endsWith("f") || s.endsWith("F")) {
      try {
        LiteralExp(LiteralType.FLOAT, s.toFloat, s)
      } catch {
        case ex : Exception =>
          reporter.report(source, line, column, "Cannot parse float: " + s)
          LiteralExp(LiteralType.FLOAT, 0.0, s)
      }
    } else {
      try {
        LiteralExp(LiteralType.DOUBLE, s.toDouble, s)
      } catch {
        case ex : Exception =>
          reporter.report(source, line, column, "Cannot parse double: " + s)
          LiteralExp(LiteralType.DOUBLE, 0.0d, s)
      }
    }) at ctx
  }

  override def visitStringConstant(ctx : StringConstantContext) = {
    import org.apache.commons.lang3.StringEscapeUtils
    var s = ctx.getText
    s = s.substring(1, s.length() - 1)
    s = StringEscapeUtils.unescapeJava(s)

    LiteralExp(LiteralType.STRING, s, s) at ctx
  }

  override def visitMultilineStringConstant(ctx : MultilineStringConstantContext) = {
    import org.apache.commons.lang3.StringEscapeUtils
    var s = ctx.getText
    s = s.substring(3, s.length() - 3)
    s = StringEscapeUtils.unescapeJava(s)

    LiteralExp(LiteralType.RAW, s, s) at ctx // TODO: Fix literal type
  }

  override def visitNamedType(ctx : NamedTypeContext) =
    NamedTypeSpec(nameUser(ctx.ID), typeTuple(ctx.typeTuple)) at ctx

  override def visitClosureType(ctx : ClosureTypeContext) =
    FunTypeSpec(false, getChildren(ctx.typeParam),
      getOptChild(ctx.annotatedType)) at ctx

  override def visitProcedureType(ctx : ProcedureTypeContext) =
    ProcedureTypeSpec(false, getChildren(ctx.typeParam),
      getOptChild(ctx.annotatedType)) at ctx

  override def visitTupleType(ctx : TupleTypeContext) =
    TupleTypeSpec(getChildren(ctx.typeParam)) at ctx

  override def visitMapType(ctx : MapTypeContext) =
    FunctionTypeSpec(getChildren(ctx.typeParam),
      getChild(ctx.annotatedType)) at ctx

  override def visitRelationType(ctx : RelationTypeContext) =
    RelationTypeSpec(getChildren(ctx.typeParam)) at ctx

  override def visitTypeParam(ctx : TypeParamContext) =
    TypeParam(getChild(ctx.`type`),
      if (ctx.ID != null) Some(ctx.ID.getText) else None,
      getChildren(ctx.annotation)) at ctx

  override def visitAnnotatedType(ctx : AnnotatedTypeContext) = {
    val t = getChild[TypeSpec](ctx.`type`)
    t.annotations = getChildren(ctx.annotation)
    t
  }

  private def int(ctx : ConstantContext) = {
    val s = ctx.getText
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
    } else if (temp.endsWith("I")) {
      temp = temp.substring(0, temp.length() - 2)
      choice = 1
    } else {
      choice = 2
    }

    val line = ctx.getStart.getLine
    val column = ctx.getStart.getCharPositionInLine

    var bi : BigInt = null
    try {
      bi = BigInt(temp, radix)
    } catch {
      case ex : Exception =>
        reporter.report(source,
          line, column, "Cannot parse integer: " + s)
    }

    (choice match {
      case 0 =>
        if (bi < Long.MinValue || bi > Long.MaxValue)
          reporter.report(source, line, column, "Exceeds long: " + s)
        LiteralExp(LiteralType.LONG, bi.longValue, s)
      case 1 =>
        if (bi < Int.MinValue || bi > Int.MaxValue)
          reporter.report(source, line, column, "Exceeds int: " + s)
        LiteralExp(LiteralType.INT, bi.intValue, s)
      case _ =>
        LiteralExp(LiteralType.INTEGER, bi, s)
    }) at ctx
  }

  private def op(t : org.antlr.v4.runtime.Token) = {
    var r = t.getText
    if (r.startsWith("`")) r.substring(1, r.length - 1) else r
  }

  private def localVarsDeclaration(ctx : LocalVarsDeclarationContext) =
    if (ctx == null) ivectorEmpty
    else ctx.localVarDeclaration.toVector.flatMap(localVarDeclaration)

  private def localVarDeclaration(ctx : LocalVarDeclarationContext) =
    ctx.localVarFragment.toVector.map { lvf =>
      LocalVarDecl(getOptChild(ctx.`type`), nameDef(lvf.ID),
        getChildren(lvf.annotation)) at ctx
    }

  private def extParams(ctx : ExtParamsContext) =
    if (ctx == null) (ivectorEmpty, false)
    else {
      var r : ISeq[ExtParam] = getChildren(ctx.extParam)
      var varArity = false
      getOptChild[ExtParam](ctx.extParamVariable) match {
        case Some(ep) =>
          r :+= ep; varArity = true
        case None =>
      }
      (r, varArity)
    }

  private def globalVarDeclaration(ctx : GlobalVarDeclarationContext) : ISeq[GlobalVarDecl] =
    ctx.globalVarFragment.toVector.map { gvf =>
      GlobalVarDecl(nameDef(gvf.GID), getChildren(gvf.annotation),
        getOptChild(ctx.`type`)) at ctx
    }

  private def field(ctx : FieldContext) : ISeq[AttributeDecl] =
    ctx.fieldFragment.toVector.map { ff =>
      AttributeDecl(nameDef(ff.ID), getChildren(ff.annotation),
        getOptChild(ctx.`type`), None) at ctx
    }

  private def extendClauses(ecs : ExtendClausesContext) : ISeq[ExtendClause] =
    if (ecs == null) ivectorEmpty
    else
      ecs.extendClause.toVector.map { ec =>
        ExtendClause(nameUser(ec.ID), getChildren(ec.annotation),
          typeTuple(ec.typeTuple)) at ec
      }

  private def typeTuple(tt : TypeTupleContext) : ISeq[TypeSpec] =
    if (tt == null) ivectorEmpty else getChildren(tt.`type`)

  private def typeVarTuple(
    tvt : TypeVarTupleContext) : ISeq[(NameDefinition, ISeq[Annotation])] =
    if (tvt != null)
      tvt.typeVar.toVector.map { tv =>
        (nameDef(tv.ID), getChildren(tv.annotation))
      }
    else ivectorEmpty

  private def locNameDef(tn : org.antlr.v4.runtime.tree.TerminalNode) = {
    var lid = tn.getText
    if (lid.endsWith("."))
      lid = lid.substring(1, lid.length - 1)
    else
      lid = lid.substring(1)
    if (lid == "") None
    else Some(NameDefinition(lid) at tn)
  }

  private def nameDef(tn : org.antlr.v4.runtime.tree.TerminalNode) : NameDefinition = {
    val r = tn.getText
    NameDefinition(if (r.startsWith("`")) r.substring(1, r.length - 1) else r) at tn
  }

  private def nameDefOpt(tn : org.antlr.v4.runtime.tree.TerminalNode) =
    if (tn == null) None else Some(nameDef(tn))

  private def nameUser(t : org.antlr.v4.runtime.Token) : NameUser = {
    val r = t.getText
    NameUser(if (r.startsWith("`")) r.substring(1, r.length - 1) else r) at t
  }

  private def nameUser(tn : org.antlr.v4.runtime.tree.TerminalNode) : NameUser =
    nameUser(tn.getSymbol)

  private def nameUserOpt(t : org.antlr.v4.runtime.Token) =
    if (t == null) None else Some(nameUser(t))
}