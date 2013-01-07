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
import java.io.File

class BakarTranslatorDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends BakarTranslatorModule {

  type BVisitor = Any => Boolean

  trait Context {
    val genThreeAddress = true
    val TEMP_VAR_PREFIX = "_t"
    val LOCATION_PREFIX = "l"

    var models = mlistEmpty[Model]

    var unhandledSet = msetEmpty[String]

    var countLocation = 0
    var stackLocationList = mstackEmpty[MList[LocationDecl]]
    def pushLocationList = stackLocationList.push(mlistEmpty[LocationDecl])
    def popLocationList = this.stackLocationList.pop
    def pushLocation(l : LocationDecl) { stackLocationList.top += l }

    var result : Any = null;
    def pushResult(o : Any) {
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
        case AndOperatorEx(_)                => Some(PilarAstUtil.LOGICAL_AND_BINOP)
        case OrOperatorEx(_)                 => Some(PilarAstUtil.LOGICAL_OR_BINOP)

        case EqualOperatorEx(_)              => Some(PilarAstUtil.EQ_BINOP)
        case GreaterThanOperatorEx(_)        => Some(PilarAstUtil.GT_BINOP)
        case GreaterThanOrEqualOperatorEx(_) => Some(PilarAstUtil.GE_BINOP)
        case LessThanOperatorEx(_)           => Some(PilarAstUtil.LT_BINOP)
        case LessThanOrEqualOperatorEx(_)    => Some(PilarAstUtil.LE_BINOP)
        case NotEqualOperatorEx(_)           => Some(PilarAstUtil.NE_BINOP)

        case DivideOperatorEx(_)             => Some(PilarAstUtil.DIV_BINOP)
        case MinusOperatorEx(_)              => Some(PilarAstUtil.SUB_BINOP)
        case MultiplyOperatorEx(_)           => Some(PilarAstUtil.MUL_BINOP)
        case PlusOperatorEx(_)               => Some(PilarAstUtil.PLUS_UNOP)
        case RemOperatorEx(_)                => Some(PilarAstUtil.REM_BINOP)

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

    var countTempVar = 0
    var listTempVar = mlistEmpty[NameExp]
    def createPushTempVar : NameExp = {
      countTempVar += 1
      val tempVar = NameExp(NameUser(TEMP_VAR_PREFIX + countTempVar))
      this.listTempVar += tempVar
      tempVar
    }

    def createPushLocation(a : Action, annots : ISeq[Annotation]) : LocationDecl = {
      countLocation += 1
      createPushLocation(NameDefinition(this.LOCATION_PREFIX + countLocation), a, annots)
    }

    def createPushLocation(locName : NameDefinition, a : Action, annots : ISeq[Annotation]) : LocationDecl = {
      val loc = createLocation(locName, a, annots)
      pushLocation(loc)
      loc
    }

    def createLocation(locName : NameDefinition, a : Action, annots : ISeq[Annotation]) = {
      ActionLocation(Some(locName), annots, a)
    }

    def createPushAssignmentLocation(lhs : Exp, rhs : Exp, annots : ISeq[Annotation]) = {
      createPushLocation(createAssignment(lhs, rhs, annots), TranslatorUtil.emptyAnnot)
    }

    def createAssignment(lhs : Exp, rhs : Exp, annots : ISeq[Annotation]) = {
      AssignAction(annots, lhs, ":=", rhs)
    }

    def assignToTemp(e : Exp) : NameExp = {
      val tempVar = createPushTempVar
      createPushLocation(createAssignment(tempVar, e, TranslatorUtil.emptyAnnot), TranslatorUtil.emptyAnnot)
      tempVar
    }

    def handleBE(v : => BVisitor, sloc : SourceLocation,
                 op : BinaryOp, lhs : ExpressionClass, rhs : ExpressionClass, theType : String) : Exp = {
      v(lhs)
      val plhs = popResult.asInstanceOf[Exp]
      v(rhs)
      val prhs = popResult.asInstanceOf[Exp]

      handleExp(BinaryExp(op, plhs, prhs))
    }

    def handleExp(e : Exp) : Exp = {
      if (this.genThreeAddress)
        assignToTemp(e)
      else
        e
    }
  }

  def packageH(ctx : Context, v : => BVisitor) : VisitorFunction = {
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

      // v(contextClauseElements)

      v(unitDeclaration)

      ctx.popResult match {
        case p : PackageDecl =>
          val fru = new File(sourceFile).toURI().toASCIIString()
          ctx.models += Model(Some(fru), ilistEmpty[Annotation], ilist(p))
        case x =>
          println("Expecting a PackageDecl, received " + x)
      }

      false
    case o @ PackageDeclarationEx(
      sloc,
      names,
      aspects,
      visiblePartDecItems,
      privatePartDecItems) =>
      println(o.getClass().getSimpleName())
      false
    case o @ PackageBodyDeclarationEx(sloc, names, aspectSpec, bodyDecItems,
      bodyStatements, bodyExceptionHandlers) =>
      println(o.getClass().getSimpleName())
      assert(bodyStatements.getStatements().isEmpty())
      assert(bodyExceptionHandlers.getExceptionHandlers().isEmpty())
      assert(aspectSpec.getElements().isEmpty())

      assert(names.getDefiningNames().length == 1)
      val di = names.getDefiningNames.get(0).asInstanceOf[DefiningIdentifier]
      val pname = NameDefinition(di.getDefName())

      val packElems = mlistEmpty[PackageElement]
      TranslatorUtil.getTypeDeclarations(bodyDecItems)

      TranslatorUtil.getConstantDeclarations(bodyDecItems)

      TranslatorUtil.getGlobalDeclarations(bodyDecItems)

      TranslatorUtil.getMethodDeclarations(bodyDecItems).foreach { m =>
        v(m)
        val pm = ctx.popResult
        packElems += pm.asInstanceOf[ProcedureDecl]
      }

      val pack = PackageDecl(Some(pname), TranslatorUtil.emptyAnnot, packElems.toList)
      ctx.pushResult(pack)

      false
  }

  def methodH(ctx : Context, v : => BVisitor) : VisitorFunction = {
      def handleMethodBody(sloc : SourceLocation,
                           names : DefiningNameList,
                           paramProfile : ParameterSpecificationList,
                           bodyDeclItems : ElementList,
                           bodyStatements : StatementList,
                           resultProfile : Option[ElementClass],
                           aspectSpecs : ElementList,
                           exceptionHandlerList : ExceptionHandlerList,
                           isOverridingDec : Base,
                           isNotOverridingDec : Base) = {

        assert(aspectSpecs.getElements().isEmpty)
        assert(exceptionHandlerList.getExceptionHandlers().isEmpty())
        assert(ctx.isEmpty(isOverridingDec))
        assert(ctx.isEmpty(isNotOverridingDec))

        assert(names.getDefiningNames().length == 1)
        val mname = NameDefinition(names.getDefiningNames().get(0).asInstanceOf[DefiningIdentifier].getDefName())

        val params = mlistEmpty[ParamDecl]
        paramProfile.getParameterSpecifications().foreach {
          case ps @ ParameterSpecificationEx(sloc, pnames, _hasAliased, _hasNullEx,
            objDecView, _initExpr, mode) =>
              // e.g (I : Integer) or (I, J : 
            assert(ctx.isEmpty(_hasAliased.getHasAliased()))
            assert(ctx.isEmpty(_hasNullEx.getHasNullExclusion()))
            assert(ctx.isEmpty(_initExpr.getExpression()))

            pnames.getDefiningNames().foreach {
              case pname : DefiningIdentifier =>
                val name = NameDefinition(pname.getDefName())
                val typeSpec : Option[TypeSpec] = None
                params += ParamDecl(typeSpec, name, TranslatorUtil.emptyAnnot)
              case x => 
                println("Not expecting: " + x)
                assert(false)
            }
          case x => 
            println("Not expecting: " + x)
            assert(false)
        }

        ctx.pushLocationList
        v(bodyStatements)
        val locals = ilistEmpty[LocalVarDecl]
        val body = ImplementedBody(locals, ctx.popLocationList.toList, ilistEmpty[CatchClause])

        val returnType : Option[TypeSpec] = resultProfile match {
          case Some(p) =>
            None
          case None => None
        }

        val annots = TranslatorUtil.emptyAnnot
        val typeVars = ilistEmpty[(NameDefinition, ISeq[Annotation])]
        val varArity = false

        ProcedureDecl(mname, TranslatorUtil.emptyAnnot, typeVars, params.toList,
          returnType, varArity, body)
      }

    {
      case o @ ProcedureDeclarationEx(sloc, isOverridingDec, isNotOverridingDec,
        name, paramProfile, hasAbstract, aspectSpec) =>
        println(o.getClass().getSimpleName())
        true
      case o @ FunctionDeclarationEx(sloc, isOverridingDec, isNotOverridingDec,
        names, paramProfile, isNotNullReturn, resultProfile, hasAbstract, aspectSpec) =>
        println(o.getClass().getSimpleName())
        true

      case o @ ProcedureBodyDeclarationEx(sloc, isOverridingDec, isNotOverridingDec,
        names, paramProfile, aspectSpec, bodyDecItems, bodyStatements, bodyExceptionHandlers) =>
        println(o.getClass().getSimpleName())

        val m = handleMethodBody(sloc, names, paramProfile, bodyDecItems,
          bodyStatements, None, aspectSpec, bodyExceptionHandlers,
          isOverridingDec.getIsOverriding(), isNotOverridingDec.getIsNotOverriding())
        ctx.pushResult(m)

        false
      case o @ FunctionBodyDeclarationEx(sloc, isOverridingDec, isNotOverridingDec,
        names, paramProfile, isNotNullReturn, resultProfile, aspectSpec,
        bodyDecItems, bodyStatements, bodyExceptionHandlers) =>
        println(o.getClass().getSimpleName())

        assert(ctx.isEmpty(isNotNullReturn.getIsNotNullReturn()))

        val m = handleMethodBody(sloc, names, paramProfile, bodyDecItems,
          bodyStatements, Some(resultProfile), aspectSpec, bodyExceptionHandlers,
          isOverridingDec.getIsOverriding(), isNotOverridingDec.getIsNotOverriding())
        ctx.pushResult(m)

        false
    }
  }

  def nameH(ctx : Context, v : => BVisitor) : VisitorFunction = {
    case o @ IdentifierEx(sloc, refName, ref, theType) =>
      println(o.getClass().getSimpleName() + " " + refName)
      ctx.pushResult(NameExp(NameUser(refName)))
      false
    case o @ DefiningIdentifierEx(sloc, defName, theDef, theType) =>
      println(o.getClass().getSimpleName() + " " + defName)
      ctx.pushResult(NameExp(NameUser(defName)))
      true
    case o @ SelectedComponentEx(sloc, prefix, selector, theType) =>
      println(o.getClass().getSimpleName())

      v(prefix.getExpression())
      val p = ctx.popResult.asInstanceOf[Exp]

      val sel = selector.getExpression().asInstanceOf[Identifier]

      // TODO: need to type prefix to determine if this is a 
      // AccessExp, field lookup, etc...
      var ret : Option[Exp] = None
      p match {
        case NameExp(ns) =>
          ret = Some(NameExp(NameUser(ns.name + "::" + sel.getRefName())))
        case x : IndexingExp =>
          ret = Some(AccessExp(x, NameUser(sel.getRefName())))
        case q =>
          println("what to do with " + q)
          assert(false)
      }
      assert(ret.isDefined)

      ctx.pushResult(ret.get)
      false
    case o @ (
      NameClassEx(_) |
      DefiningNameClassEx(_) |
      DefiningExpandedNameEx(_)
      ) =>
      println("nameH: need to handle: " + o.getClass().getSimpleName())
      true
  }

  def statementH(ctx : Context, v : => BVisitor) : VisitorFunction = {
    case o @ StatementListEx(statements) =>
      println(o.getClass().getSimpleName())
      true
    case o @ AssignmentStatementEx(sloc, labelName, assignmentVariableName, assignmentExpression) =>
      println(o.getClass().getSimpleName())
      v(assignmentVariableName)
      val lhs = ctx.popResult.asInstanceOf[Exp]

      v(assignmentExpression)
      val rhs = ctx.popResult.asInstanceOf[Exp]

      ctx.createPushAssignmentLocation(lhs, rhs, TranslatorUtil.emptyAnnot)
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
    case o @ WhileLoopStatementEx(sloc, labelNames, statementIdentifier,
      whileCondition, loopStatements) =>
      println(o.getClass().getSimpleName())
      true
    case o @ ForLoopStatementEx(sloc, labelnames, statementIdentifier,
      forLoopParameterSpecification, loopStatements) =>
      println(o.getClass().getSimpleName())
      true
    case o @ (NullStatementEx(_) |
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

  def expressionH(ctx : Context, v : => BVisitor) : VisitorFunction = {
    case o @ FunctionCallEx(sloc, prefix, functionCallParameters, isPrefixCall, isPrefixNotation, theType) =>
      import collection.JavaConversions._
      println(o.getClass().getSimpleName())

      if (!ctx.isEmpty(isPrefixCall.getIsPrefixCall())) {
        // TODO
      }

      if (!ctx.isEmpty(isPrefixNotation.getIsPrefixNotation())) {
        // TODO
      }

      val plist = mlistEmpty[Exp]
      functionCallParameters.getAssociations().toList.foreach { a =>
        // TODO: these maybe associations in which case the arg order may not 
        //       match the declared formal param order
        v(a)
        plist += ctx.popResult.asInstanceOf[Exp]
      }

      if (ctx.isBinaryOp(prefix)) {
        assert(plist.length == 2)
        val be = BinaryExp(ctx.getBinaryOp(prefix).get, plist(0), plist(1))
        ctx.pushResult(ctx.handleExp(be))
      } else {
        v(prefix)
        val mname = ctx.popResult.asInstanceOf[Exp]
        val ce = CallExp(mname, TupleExp(plist.toList))
        ctx.pushResult(ctx.handleExp(ce))
      }

      false
    case IntegerLiteralEx(sloc, litVal, theType) =>
      val v = litVal.replaceAll("_", "")
      ctx.pushResult(LiteralExp(LiteralType.INTEGER, Integer.parseInt(v), v))
      false
    case o @ AndThenShortCircuitEx(sloc, lhs, rhs, theType) =>
      ctx.pushResult(
        ctx.handleBE(v, sloc, PilarAstUtil.LOGICAL_AND_BINOP, lhs, rhs, theType))
      false
    case o @ OrElseShortCircuitEx(sloc, lhs, rhs, theType) =>
      ctx.pushResult(
        ctx.handleBE(v, sloc, PilarAstUtil.LOGICAL_OR_BINOP, lhs, rhs, theType))
      false
    case o @ IfExpressionEx(_) =>
      println("expressionH: need to handle: " + o.getClass.getSimpleName)
      true
    case o @ IndexedComponentEx(sloc, prefix, indexExp, theType) =>
      v(prefix)
      val pprefix = ctx.popResult.asInstanceOf[Exp]

      val indices = mlistEmpty[Exp]
      indexExp.getExpressions().foreach { e =>
        v(e)
        indices += ctx.popResult.asInstanceOf[Exp]
      }

      val ie = IndexingExp(pprefix, indices.toList)
      ctx.pushResult(ie)
      false
    case o @ (
      RealLiteralEx(_) |
      //ExpressionClassEx(_) |
      ExpressionListEx(_) |
      DiscreteSimpleExpressionRangeEx(_) |
      StringLiteralEx(_) |
      CharacterLiteralEx(_) |
      EnumerationLiteralEx(_) |
      FunctionCallEx(_) |

      NullLiteralEx(_) |
      QualifiedExpressionEx(_) |
      ForAllQuantifiedExpressionEx(_) |
      ForSomeQuantifiedExpressionEx(_)
      ) =>
      println("expressionH: need to handle: " + o.getClass.getSimpleName)
      true
  }

  def everythingElseH(ctx : Context, v : => BVisitor) : VisitorFunction = {
    case o if (o != null) =>
      ctx.unhandledSet += o.getClass.getSimpleName()
      true
    case null =>
      println("everythingElseH: it is null")
      assert(false)
      false
  }

  def theVisitor : BVisitor = b
  val ctx = new Context {
    this.stackLocationList.push(mlistEmpty[LocationDecl])
  }

  val b = Visitor.build(
    Visitor.first(
      ilist(
        packageH(ctx, theVisitor),
        methodH(ctx, theVisitor),
        statementH(ctx, theVisitor),
        expressionH(ctx, theVisitor),
        nameH(ctx, theVisitor),
        everythingElseH(ctx, theVisitor)
      )))

  this.parseGnat2XMLresults.foreach {
    case (key, value) => {
      b(value)
    }
  }

  println("Not handling: " + ctx.unhandledSet.toList.sorted)

  this.results_=(ctx.models)
}
