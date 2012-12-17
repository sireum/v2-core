package org.sireum.bakar.compiler.module

import org.sireum.bakar.xml._
import org.sireum.pilar.ast._
import org.sireum.pipeline._
import org.sireum.util._
import org.sireum.pilar.ast.AssignAction.apply
import org.sireum.pilar.ast.BinaryExp.apply
import org.sireum.pilar.ast.CallExp.apply
import org.sireum.pilar.ast.LiteralExp.apply
import org.sireum.pilar.ast.NameExp.apply
import org.sireum.pilar.ast.NameUser.apply
import org.sireum.pilar.ast.TupleExp.apply
import scala.Some.apply
import scala.collection.JavaConversions.asScalaBuffer

class BakarVisitorDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends BakarVisitorModule {

  type Visitor = Any => Boolean

  trait Context {
    var elemStack = mstackEmpty[Any]

    var unhandledSet = msetEmpty[String]

    var result : Object = null;

    def pushResult(o : Object) {
      //assert(result == null);
      result = o
    }

    def popResult = {
      //assert(result != null)
      val t = result
      result = null
      t
    }

    def isEmpty(o : Base) : Boolean = o.isInstanceOf[NotAnElement]

    def isBinaryOp(o : Any) = getBinaryOp(o).isDefined
    def getBinaryOp(o : Any) : scala.Option[BinaryOp] = {
      if (!o.isInstanceOf[ExpressionClass]) {
        return None
      }
      val x = o.asInstanceOf[ExpressionClass].getExpression().asInstanceOf[Any]

      x match {
        case AndOperatorEx                => Some(PilarAstUtil.LOGICAL_AND_BINOP)
        case OrOperatorEx                 => Some(PilarAstUtil.LOGICAL_OR_BINOP)

        case EqualOperatorEx              => Some(PilarAstUtil.EQ_BINOP)
        case GreaterThanOperatorEx        => Some(PilarAstUtil.GT_BINOP)
        case GreaterThanOrEqualOperatorEx => Some(PilarAstUtil.GE_BINOP)
        case LessThanOperatorEx           => Some(PilarAstUtil.LT_BINOP)
        case LessThanOrEqualOperatorEx    => Some(PilarAstUtil.LE_BINOP)
        case NotEqualOperatorEx           => Some(PilarAstUtil.NE_BINOP)

        case DivideOperatorEx             => Some(PilarAstUtil.DIV_BINOP)
        case MinusOperatorEx              => Some(PilarAstUtil.SUB_BINOP)
        case MultiplyOperatorEx           => Some(PilarAstUtil.MUL_BINOP)
        case PlusOperatorEx               => Some(PilarAstUtil.PLUS_UNOP)
        case RemOperatorEx                => Some(PilarAstUtil.REM_BINOP)

        case XorOperatorEx =>
          println("Don't know how to handle bin op " + o)
          None
        case ModOperatorEx =>
          println("Don't know how to handle bin op " + o)
          None

        case _ =>
          None
      }
    }
  }

  def packageH(ctx : Context, v : => Visitor) : VisitorFunction = {
    case o @ CompilationUnitEx(
      sloc,
      contextClauseElements,
      unitDeclaration,
      pragmasAfter,
      unitKind,
      unitClass,
      unitOrigin,
      unitFullName,
      defName,
      sourceFile) =>
      println(o.getClass().getSimpleName())
      true
    case o @ PackageDeclarationEx(
      sloc,
      names,
      aspects,
      visiblePartDecItems,
      privatePartDecItems) =>
      println(o.getClass().getSimpleName())
      true
    case o @ PackageBodyDeclarationEx(sloc, names, aspectSpec, bodyDecItems, bodyStatements, bodyExceptionHandlers) =>
      println(o.getClass().getSimpleName())
      true
  }

  def methodH(ctx : Context, v : => Visitor) : VisitorFunction = {
    case o @ ProcedureDeclarationEx(sloc, isOverridingDec, isNotOverridingDec, name, paramProfile, hasAbstract, aspectSpec) =>
      println(o.getClass().getSimpleName())
      true
    case o @ ProcedureBodyDeclarationEx(sloc, isOverridingDec, isNotOverridingDec, names, paramProfile, aspectSpec, bodyDecItems, bodyStatements, bodyExceptionHandlers) =>
      println(o.getClass().getSimpleName())
      true
    case o @ FunctionDeclarationEx(sloc, isOverridingDec, isNotOverridingDec, names, paramProfile, isNotNullReturn, resultProfile, hasAbstract, aspectSpec) =>
      println(o.getClass().getSimpleName())
      true
    case o @ FunctionBodyDeclarationEx(sloc, isOverridingDec, isNotOverridingDec, names, paramProfile, isNotNullReturn, resultProfile, aspectSpec, bodyDecItems, bodyStatements, bodyExceptionHandlers) =>
      println(o.getClass().getSimpleName())
      true
  }

  def nameH(ctx : Context, v : => Visitor) : VisitorFunction = {
    case o @ IdentifierEx(sloc, refName, ref, theType) =>
      ctx.pushResult(NameExp(NameUser(refName)))
      false
    case o @ DefiningIdentifierEx(sloc, defName, theDef, theType) =>
      ctx.pushResult(NameExp(NameUser(defName)))
      true
    case o @ (
      SelectedComponentEx(_) |
      NameClassEx(_) |
      DefiningNameClassEx(_) |
      DefiningExpandedNameEx(_)
      ) =>
      println("nameH: need to handle: + " + o.getClass().getSimpleName())
      true
  }

  def statementH(ctx : Context, v : => Visitor) : VisitorFunction = {
    case o @ StatementListEx(statements) =>
      println(o.getClass().getSimpleName())
      true
    case o @ NullStatementEx(sloc, labelNames) =>
      println(o.getClass().getSimpleName())
      true
    case o @ AssignmentStatementEx(sloc, labelName, assignmentVariableNames, assignmentExpression) =>

      v(assignmentVariableNames)
      val lhs = ctx.popResult.asInstanceOf[Exp]

      v(assignmentExpression)
      val rhs = ctx.popResult.asInstanceOf[Exp]

      val aa = AssignAction(ilistEmpty[Annotation], lhs, ":=", rhs)
      ctx.pushResult(aa)

      false
    case o @ IfStatementEx(sloc, labelNames, statementPaths) =>
      println(o.getClass().getSimpleName())
      true
    case o @ CaseStatementEx(sloc, labelnames, caseExpression, statementPaths) =>
      println(o.getClass().getSimpleName())
      true
    case o @ LoopStatementEx(sloc, labelNames, statementIdentifier, loopStatements) =>
      println(o.getClass().getSimpleName())
      true
    case o @ WhileLoopStatementEx(sloc, labelNames, statementIdentifier, whileCondition, loopStatements) =>
      println(o.getClass().getSimpleName())
      true
    case o @ ForLoopStatementEx(sloc, labelnames, statementIdentifier, forLoopParameterSpecification, loopStatements) =>
      println(o.getClass().getSimpleName())
      true
    case o @ (
      BlockStatementEx(_) |
      BlockStatementEx(_) |
      GotoStatementEx(_) |
      ProcedureCallStatementEx(_) |
      ReturnStatementEx(_) |
      AcceptStatementEx(_) |
      ExtendedReturnStatementEx(_) |
      EntryCallStatementEx(_) |
      RequeueStatementEx(_) |
      RequeueStatementWithAbortEx(_) |
      DelayUntilStatementEx(_) |
      DelayRelativeStatementEx(_) |
      TerminateAlternativeStatementEx(_) |
      SelectiveAcceptStatementEx(_) |
      TimedEntryCallStatementEx(_) |
      ConditionalEntryCallStatementEx(_) |
      AsynchronousSelectStatementEx(_) |
      AbortStatementEx(_) |
      RaiseStatementEx(_) |
      CodeStatementEx(_)
      ) =>
      println("statementH: need to handle " + o.getClass().getSimpleName())
      true
  }

  def expressionH(ctx : Context, v : => Visitor) : VisitorFunction = {
    case ExpressionClassEx(expr) =>
      true
    case o @ FunctionCallEx(sloc, prefix, functionCallParameters, isPrefixCall, isPrefixNotation, theType) =>
      import collection.JavaConversions._
      println(o.getClass().getSimpleName())

      if (!ctx.isEmpty(isPrefixCall.getIsPrefixCall())) {
        val p = isPrefixCall.getIsPrefixCall().asInstanceOf[IsPrefixCall]
        val ploc = p.getSloc()
      }

      if (!ctx.isEmpty(isPrefixNotation.getIsPrefixNotation())) {
        val pn = isPrefixNotation.getIsPrefixNotation().asInstanceOf[IsPrefixNotation]
        val pnloc = pn.getSloc()
      }

      val plist = mlistEmpty[Exp]
      functionCallParameters.getAssociations().toList.foreach { a =>
        v(a)
        plist += ctx.popResult.asInstanceOf[Exp]
      }

      if (ctx.isBinaryOp(prefix)) {
        assert(plist.length == 2)
        val bo = ctx.getBinaryOp(prefix)
        val be = BinaryExp(bo.get, plist(0), plist(1))
        ctx.pushResult(be)
      } else {
        v(prefix)
        val mname = ctx.popResult.asInstanceOf[Exp]
        val ce = CallExp(mname, TupleExp(plist.toList))
        ctx.pushResult(ce)
      }

      false
    case IntegerLiteralEx(sloc, litVal, theType) =>
      val v = litVal.replaceAll("_", "")
      ctx.pushResult(LiteralExp(LiteralType.INTEGER, Integer.parseInt(v), v))
      false
    case o @ (
      ExpressionClassEx(_) |
      ExpressionListEx(_) |
      DiscreteSimpleExpressionRangeEx(_) |
      RealLiteralEx(_) |
      StringLiteralEx(_) |
      CharacterLiteralEx(_) |
      EnumerationLiteralEx(_) |
      FunctionCallEx(_) |
      IndexedComponentEx(_) |
      AndThenShortCircuitEx(_) |
      OrElseShortCircuitEx(_) |
      NullLiteralEx(_) |
      QualifiedExpressionEx(_) |
      IfExpressionEx(_) |
      ForAllQuantifiedExpressionEx(_) |
      ForSomeQuantifiedExpressionEx(_)
      ) =>
      println("expressionH: need to handle: " + o.getClass.getSimpleName)
      true
  }

  def everythingElseH(ctx : Context, v : => Visitor) : VisitorFunction = {
    case o if (o != null) =>
      ctx.unhandledSet += o.getClass.getSimpleName()
      true
    case null =>
      println("everythingElseH: it is null")
      false
  }

  def visitor : Visitor = b

  def preStep(ctx : Context, v : => Visitor) : VisitorFunction = {
    case o =>
      println("prestep")
      ctx.elemStack.push(o)
      false
  }

  def postStep(ctx : Context, v : => Visitor) : VisitorFunction = {
    case o =>
      ctx.elemStack.pop
      false
  }

  val ctx = new Context {}

  def step[A] //
  (f1 : A --> Boolean, f2 : A --> Boolean) : A --> Boolean = {
    new PartialFunction[A, Boolean] {
      def isDefinedAt(a : A) = (f1 isDefinedAt a) || (f2 isDefinedAt a)
      def apply(a : A) : Boolean = {
        f1(a)
        if (f2 isDefinedAt a) f2(a) else false
      }
    }
  }

  val b = Visitor.build(Visitor.map(
    ilist(
      packageH(ctx, visitor),
      methodH(ctx, visitor),
      statementH(ctx, visitor),
      expressionH(ctx, visitor),
      nameH(ctx, visitor),
      everythingElseH(ctx, visitor))
  ))

  this.parseGnat2XMLresults.foreach {
    case (key, value) => {
      b(value)
    }
  }

  println(ctx.unhandledSet.toList.sorted)
}
