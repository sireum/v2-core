/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.eval

import org.sireum.extension._
import org.sireum.pilar.ast._
import org.sireum.pilar.symbol._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object EvaluatorImpl {
  final case class AVI(locUri : Option[ResourceUri], locIndex : Int,
                       transIndex : Int, commandIndex : Int) extends AssertionViolationInfo

  final case class AVIL(procUri : ResourceUri, locUri : Option[ResourceUri], locIndex : Int,
                        transIndex : Int, commandIndex : Int) extends AssertionViolationInfo with ProcInfo

  final case class ABI(locUri : Option[ResourceUri], locIndex : Int,
                       transIndex : Int, commandIndex : Int) extends AssumptionBreachInfo

  final case class ABIL(procUri : ResourceUri, locUri : Option[ResourceUri], locIndex : Int,
                        transIndex : Int, commandIndex : Int) extends AssumptionBreachInfo with ProcInfo

  final case class LI(locUri : Option[ResourceUri], locIndex : Int,
                      transIndex : Int, commandIndex : Int) extends LocationInfo

  final case class LIL(procUri : ResourceUri, locUri : Option[ResourceUri], locIndex : Int,
                       transIndex : Int, commandIndex : Int) extends LocationInfo with ProcInfo

  case class Trans[S <: State[S]](
    enabled : ISeq[(S, LocationDecl, Transformation)],
    disabled : ISeq[(S, LocationDecl, Transformation)]) extends Transitions[S]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class EvaluatorImpl[S <: State[S], V] extends Evaluator[S, ISeq[(S, V)], ISeq[(S, Boolean)], ISeq[S]]
    with EvaluatorModule {
  type Re = (S, V)
  type R = ISeq[Re]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]

  var ec : EvaluatorConfiguration = null
  lazy val sec : SemanticsExtensionConsumer[S, V, R, C, SR] = ec.semanticsExtension
  lazy val sp : SymbolProvider[S] = ec.symbolProvider
  lazy val elseGuardExpander : Option[ElseGuardExpander] = ec.elseGuardExpander

  def initialize(config : ExtensionConfig) {
    import EvaluatorConfig._
    ec = config.evalConfig
  }

  import EvaluatorImpl._

  @inline
  private def re2s(re : Re) : S = re._1

  @inline
  private def re2v(re : Re) : V = re._2

  @inline
  private def re2value(re : Re) : Value = ec.vToValue(re._2)

  @inline
  private def value2v(v : Value) : V = ec.valueToV(v)

  @inline
  private def v2value(v : V) : Value = ec.vToValue(v)

  @inline
  private def cond(s : S, l : LocationDecl, t : Transformation, e : Exp) =
    mainEvaluator.evalGuard(s, l, t, e)

  val evalGuard : (S, LocationDecl, Transformation, Exp) --> C = {
    case (s, l, t, e) =>
      for {
        re1 <- eval(s, e)
        re2 <- sec.cond(re2s(re1), re2v(re1))
      } yield re2
  }

  @inline
  private def cond(s : S, cond : Exp) =
    for {
      re1 <- eval(s, cond)
      re2 <- sec.cond(re2s(re1), re2v(re1))
    } yield re2

  @inline
  private def eval(s : S, j : Jump) : SR = mainEvaluator.evalJump(s, j)

  @inline
  private def evalGotoJump(s : S, j : GotoJump) : SR = ivector(sp.location(s, j.target))

  @inline
  private def evalIfJump(s : S, j : IfJump) : SR = {
    var r : ISeq[S] = ivectorEmpty

      def it(s : S, i : Int) {
        if (i == j.ifThens.size)
          if (j.ifElse.isEmpty)
            r = sp.nextLocation(s) +: r
          else
            r = sp.location(s, j.ifElse.get.target) +: r
        else {
          val ifThen = j.ifThens(i)
          val eValues = cond(s, ifThen.cond)
          for (p <- eValues)
            if (p._2)
              r = sp.location(p._1, ifThen.target) +: r
            else
              it(p._1, i + 1)
        }
      }
    it(s, 0)
    r
  }

  @inline
  private def evalSwitchJump(s : S, j : SwitchJump) : SR = {
    var r : ISeq[S] = ivectorEmpty

      def it(s : S, v : ValueExp, i : Int) {
        if (i == j.cases.size)
          if (j.defaultCase.isEmpty)
            r = sp.nextLocation(s) +: r
          else
            r = sp.location(s, j.defaultCase.get.target) +: r
        else {
          val switchCase = j.cases(i)
          val eValues = cond(s, BinaryExp("==", v, switchCase.cond))
          for (p <- eValues)
            if (p._2)
              r = sp.location(p._1, switchCase.target) +: r
            else
              it(p._1, v, i + 1)
        }
      }
    val eValues = eval(s, j.cond)
    eValues.foreach { p => it(re2s(p), ValueExp(re2value(p)), 0) }
    r
  }

  @inline
  private def evalReturnJump(s : S, j : ReturnJump) : SR = {
    if (j.exp.isEmpty) {
      val (newS, cf) = s.exitCallFrame
      if (newS.callStack.size == 0)
        ivector(newS)
      else
        ivector(newS.location(cf.returnLocationUri, cf.returnLocationIndex))
    } else {
      val eValues = eval(s, j.exp.get)
      eValues.flatMap { p =>
        val s : S = re2s(p)
        val v : V = re2v(p)
        if (s.callStack.size == 1)
          ivector(s.exitCallFrame._1)
        else {
          val (s2, cf) = s.exitCallFrame
          val vars = cf.returnVariableUris
          val s3 = s2.location(cf.returnLocationUri, cf.returnLocationIndex)
          if (vars.length == 1)
            ivector(s3.variable(vars(0), v2value(v)))
          else
            for { (s4, vs) <- sec.tupleDecon(s3, v) } yield {
              var s5 = s4
              if (vars.length == vs.length)
                for (i <- 0 until vs.length)
                  s5 = s5.variable(vars(i), v2value(vs(i)))
              s5
            }
        }
      }
    }
  }

  @inline
  private def evalCallJump(s : S, j : CallJump) : SR = {
    val ce = j.callExp
    val nextLoc = sp.nextLocation(s)
    val eValues = eval(s, ce.exp)
    val spa =
      for {
        re1 <- eval(s, ce.exp)
        re2 <- eval(re2s(re1), ce.arg)
      } yield (re2s(re2), re2v(re1), re2v(re2))

    val returnVars = j.lhss.map { nu =>
      if (nu.name.hasResourceInfo) nu.name.uri
      else nu.name.name
    }
    spa.flatMap { t =>
      val (s, proc, arg) = t
      sec.resolveCall(j, s, proc, arg).map { p =>
        val (s, procUri) = p
        val initLocUri = sp.initLocation(procUri)
        val store = sp.initStore(s, procUri, v2value(arg))
        s.enterCallFrame(procUri, initLocUri, 0, store, nextLoc.locationUri,
          nextLoc.locationIndex, returnVars)
      }
    }
  }

  @inline
  private def eval(s : S, a : Action) = mainEvaluator.evalAction(s, a)

  @inline
  private def evalAssertAction(s : S, a : AssertAction) : SR = {
    val (locName, locIndex, transIndex, cmdIndex) = a.commandDescriptorInfo
    cond(s, a.cond).map { p =>
      if (p._2)
        p._1
      else
        p._1.assertionViolation(AVI(locName, locIndex, transIndex, cmdIndex))
    }
  }

  @inline
  private def evalAssumeAction(s : S, a : AssumeAction) : SR = {
    val (locName, locIndex, transIndex, cmdIndex) = a.commandDescriptorInfo
    cond(s, a.cond).map { p =>
      if (p._2)
        p._1
      else
        p._1.assumptionBreach(ABI(locName, locIndex, transIndex, cmdIndex))
    }
  }

  @inline
  private def evalThrowAction(s : S, a : ThrowAction) : SR = {
    val eValues = eval(s, a.exp)
    val (locName, locIndex, transIndex, cmdIndex) = a.commandDescriptorInfo
    eValues.map { p =>
      re2s(p).raiseException(re2value(p),
        LI(locName, locIndex, transIndex, cmdIndex))
    }
  }

  @inline
  private def evalExtCallAction(s : S, a : ExtCallAction) : SR = {
    val e = a.callExp
    for {
      re1 <- eval(s, e.exp)
      args <- {
        val s2 = re2s(re1)
        val f = re2v(re1)
        assert(sec.uriExtractor.isDefinedAt(f))
        val extUri = sec.uriExtractor(f)
        buildExtArgs(s2, extUri, e.arg)
      }
      re3 <- {
        val uri = sec.uriExtractor(re2v(re1))
        val sr = sec.actionExtCall(uri, args)
        val n = sr.length
        if (n <= 1) sr
        else {
          val s3 = args.productElement(0)
          s3 match {
            case s3 : ScheduleRecordingState[S] =>
              s3.peekSchedule match {
                case None =>
                  sr.zip(0 until n).map { sj =>
                    val (s, j) = sj
                    s.asInstanceOf[ScheduleRecordingState[_]].
                      recordSchedule(uri, n, j).asInstanceOf[S]
                  }
                case Some((sourceOpt, num, i)) =>
                  assert(sourceOpt == Some(uri) && num == sr.length)
                  val s4 = sr(i).asInstanceOf[ScheduleRecordingState[_]]
                  ivector(s4.popSchedule.asInstanceOf[S])
              }
            case _ => sr
          }
        }
      }
    } yield re3
  }

  @inline
  private def evalAssignAction(s : S, a : AssignAction) : SR =
    if (a.op == ":=")
      (a.lhs : @unchecked) match {
        case e : NameExp if sp.isVar(e) || !e.name.hasResourceInfo =>
          for {
            re1 <- eval(s, a.rhs)
            re2 <- sec.variableUpdate(re2s(re1), e.name, re2v(re1))
          } yield re2
        case AccessExp(e : NameExp, f) if sp.isVar(e) || !e.name.hasResourceInfo =>
          for {
            re1 <- sec.variable(s, e.name)
            re2 <- eval(re2s(re1), a.rhs)
            s1 <- {
              val s2 = re2s(re2)
              val v = re2v(re2)
              val arg = (s2, e.name, f, v)
              if (sec.fieldUpdateVar.isDefinedAt(arg))
                sec.fieldUpdateVar(arg)
              else
                sec.fieldUpdate(s2, re2v(re1), f, v)
            }
          } yield s1
        case AccessExp(e : Exp, f) =>
          for {
            re1 <- eval(s, e)
            re2 <- eval(re2s(re1), a.rhs)
            s1 <- sec.fieldUpdate(re2s(re2), re2v(re1), f, re2v(re2))
          } yield s1
        case IndexingExp(e : NameExp, ies) if (
          sp.isVar(e) || !e.name.hasResourceInfo) && ies.length == 1 =>
          for {
            re1 <- sec.variable(s, e.name)
            re2 <- eval(re2s(re1), ies(0))
            re3 <- eval(re2s(re2), a.rhs)
            s1 <- {
              val s2 = re2s(re3)
              val index = re2v(re2)
              val v = re2v(re3)
              val arg = (s2, e.name, index, v)
              if (sec.indexUpdateVar.isDefinedAt(arg))
                sec.indexUpdateVar(arg)
              else
                sec.indexUpdate(s2, re2v(re1), index, v)
            }
          } yield s1
        case IndexingExp(e : Exp, ies) if ies.length == 1 =>
          for {
            re1 <- eval(s, a.rhs)
            re2 <- eval(re2s(re1), e)
            re3 <- eval(re2s(re2), ies(0))
            s1 <- sec.indexUpdate(re2s(re3), re2v(re2), re2v(re3), re2v(re1))
          } yield s1
      }
    else
      for {
        re1 <- eval(s, a.rhs)
        re2 <- eval(re2s(re1), a.lhs)
        s1 <- sec.assignOp(a.op, re2s(re2), re2v(re1), re2v(re2))
      } yield s1

  @inline
  private def evalStartAction(s : S, a : StartAction) : SR = {
    sys.error("unhandled") // TODO
  }

  private def buildExtArgs(s : S, extUri : ResourceUri, arg : Exp) : ISeq[Product] = {
      @inline
      def mkLazyArg(exp : Exp)(s : S) = eval(s, exp)

    val bitMask = sec.extLazyBitMask(extUri)
    val varargs = sec.extVarArgs(extUri)
    val extParamSize = sec.extNumOfArgs(extUri)
    if (varargs)
      arg match {
        case arg : TupleExp if extParamSize == 1 && arg.exps.size == 0 =>
          ivector((s, ivectorEmpty[V]))
        case arg : TupleExp if extParamSize - 1 <= arg.exps.size =>
          case class ExtArgV(state : S,
                             paramArgs : IVector[Any] = ivectorEmpty,
                             paramVarArgs : IVector[Any] = ivectorEmpty)
          var eavs = ivector(ExtArgV(s))
          for (i <- 0 until extParamSize - 1) {
            val exp = arg.exps(i)
            eavs = eavs.flatMap { eav =>
              if (bitMask.contains(i))
                ivector(ExtArgV(eav.state, eav.paramArgs :+ (mkLazyArg(exp) _)))
              else
                eval(eav.state, exp).map { re =>
                  ExtArgV(re2s(re), eav.paramArgs :+ re2v(re))
                }
            }
          }
          for (i <- extParamSize - 1 until arg.exps.size) {
            val exp = arg.exps(i)
            eavs = eavs.flatMap { vss =>
              if (bitMask.contains(extParamSize - 1))
                ivector(ExtArgV(vss.state, vss.paramArgs,
                  vss.paramVarArgs :+ (mkLazyArg(exp) _)))
              else
                eval(vss.state, exp).map { re =>
                  ExtArgV(re2s(re), vss.paramArgs, vss.paramVarArgs :+ re2v(re))
                }
            }
          }
          eavs.map { vss =>
            val args = (vss.state +: vss.paramArgs) ++
              ivector(vss.paramVarArgs.toSeq)
            TupleHelper.makeTuple(args)
          }
        case _ if !arg.isInstanceOf[TupleExp] && extParamSize == 1 =>
          if (bitMask.contains(0)) {
            val args : ISeq[Any] = s +: ivector(Seq(mkLazyArg(arg) _))
            ivector(TupleHelper.makeTuple(args))
          } else
            eval(s, arg).map { re =>
              val args = (re2s(re)) +: ivector(Seq(re2v(re)))
              TupleHelper.makeTuple(args)
            }
        case _ =>
          sys.error("ill-formed model")
      }
    else
      arg match {
        case arg : TupleExp if extParamSize == arg.exps.size =>
          if (extParamSize == 0)
            ivector(Tuple1(s))
          else {
            case class ExtArg(state : S,
                              paramArgs : IVector[Any] = ivectorEmpty)
            var eas = ivector(ExtArg(s))
            for (i <- 0 until extParamSize) {
              val exp = arg.exps(i)
              eas = eas.flatMap { ea =>
                if (bitMask.contains(i))
                  ivector((ExtArg(ea.state, ea.paramArgs :+ (mkLazyArg(exp) _))))
                else
                  eval(ea.state, exp).map { re =>
                    ExtArg(re2s(re), ea.paramArgs :+ re2v(re))
                  }
              }
            }
            eas.map { ea =>
              val args = ea.state +: ea.paramArgs
              TupleHelper.makeTuple(args)
            }
          }
        case _ if !arg.isInstanceOf[TupleExp] && extParamSize == 1 =>
          if (bitMask.contains(0)) {
            val args : ISeq[Any] = s +: ivector(mkLazyArg(arg) _)
            ivector(TupleHelper.makeTuple(args))
          } else
            eval(s, arg).map { re =>
              val args = re2s(re) +: ivector(re2v(re))
              TupleHelper.makeTuple(args)
            }
        case _ =>
          sys.error("ill-formed model")
      }
  }

  @inline
  private def eval(s : S, e : Exp) : R = mainEvaluator.evalExp(s, e)

  @inline
  private def isNormal(s : S) =
    s.raisedException.isEmpty && s.assertionViolation.isEmpty

  private val EMPTY_TRANS = Transformation(ivectorEmpty, None, ivectorEmpty, None)

  var ev : Evaluator[S, R, C, SR] = this
  def mainEvaluator = ev
  def setMainEvaluator(eval : Evaluator[S, R, C, SR]) {
    ev = eval
  }

  def evalTransformation : (S, LocationDecl, Transformation) --> SR = {
    case (state, l, t) =>
      var ss = ivector(state)
      for (a <- t.actions) {
        ss = ss.flatMap { s => if (isNormal(s)) eval(s, a) else ivector(s) }
      }

      if (t.jump.isDefined)
        ss.flatMap { s => if (isNormal(s)) eval(s, t.jump.get) else ivector(s) }
      else
        ss.map { s => if (isNormal(s)) sp.nextLocation(s) else s }
  }

  def transitions : (S, LocationDecl) --> Transitions[S] = {
    case (s, l : ActionLocation) =>
      Trans(ivector((s, l, Transformation(ivectorEmpty, None, ivector(l.action), None))),
        ivectorEmpty)
    case (s, l : JumpLocation) =>
      Trans(ivector((s, l, Transformation(ivectorEmpty, None, ivectorEmpty, Some(l.jump)))),
        ivectorEmpty)
    case (s, l : EmptyLocation) =>
      Trans(ivector((s, l, EMPTY_TRANS)), ivectorEmpty)
    case (s, l : ComplexLocation) =>
      var elseTrans : Option[Transformation] = None

      var enabledTrans : ISeq[(S, LocationDecl, Transformation)] = ivectorEmpty
      var disabledTrans : ISeq[(S, LocationDecl, Transformation)] = ivectorEmpty
      for (t <- l.transformations) {
        if (t.guard.isEmpty)
          enabledTrans = (s, l, t) +: enabledTrans
        else
          t.guard.get match {
            case g : ExpGuard =>
              for (scond <- cond(s, l, t, g.cond)) {
                if (scond._2)
                  enabledTrans = (scond._1, l, t) +: enabledTrans
                else if (ec.computeDisabledTransitions)
                  disabledTrans = (scond._1, l, t) +: disabledTrans
              }
            case g : ElseGuard =>
              assert(elseTrans.isEmpty)
              elseTrans = Some(t)
          }
      }

      if (elseGuardExpander.isDefined && elseTrans.isDefined) {
        val expander = elseGuardExpander.get
        val elseT = elseTrans.get
        var conds : ISeq[Exp] = Nil
        for (t <- l.transformations)
          if (t ne elseT)
            conds = expander.negate(t.guard.get.asInstanceOf[ExpGuard].cond) +: conds

        val cond = conds.reduce(expander.conjunct)
        val enabledElseTran = ((s, l, Transformation(elseT.annotations,
          Some(ExpGuard(ivectorEmpty, cond)), elseT.actions, elseT.jump)))

        Trans(enabledElseTran +: enabledTrans, disabledTrans)
      } else if (enabledTrans.isEmpty)
        if (elseTrans.isEmpty)
          Trans(ivectorEmpty, ivectorEmpty)
        else
          Trans(ivector((s, l, elseTrans.get)), ivectorEmpty)
      else if (elseTrans.isDefined && ec.computeDisabledTransitions)
        Trans(enabledTrans, (s, l, elseTrans.get) +: disabledTrans)
      else
        Trans(enabledTrans, disabledTrans)
  }

  def evalJump : (S, Jump) --> SR = {
    case (s, j : GotoJump)   => evalGotoJump(s, j)
    case (s, j : IfJump)     => evalIfJump(s, j)
    case (s, j : SwitchJump) => evalSwitchJump(s, j)
    case (s, j : ReturnJump) => evalReturnJump(s, j)
    case (s, j : CallJump)   => evalCallJump(s, j)
  }

  def evalAction : (S, Action) --> SR = {
    case (s, a : AssertAction)  => evalAssertAction(s, a)
    case (s, a : AssumeAction)  => evalAssumeAction(s, a)
    case (s, a : ThrowAction)   => evalThrowAction(s, a)
    case (s, a : ExtCallAction) => evalExtCallAction(s, a)
    case (s, a : AssignAction)  => evalAssignAction(s, a)
    case (s, a : StartAction)   => evalStartAction(s, a)
  }

  def evalExp : (S, Exp) --> R = {
    case (s, ValueExp(v)) =>
      ivector((s, ec.valueToV(v)))
    case (s, LiteralExp(_, b : Boolean, _)) =>
      ivector(if (b) sec.trueLiteral(s) else sec.falseLiteral(s))
    case (s, LiteralExp(_, n : Int, _)) =>
      ivector(sec.intLiteral(s, n))
    case (s, LiteralExp(_, n : Long, _)) =>
      ivector(sec.longLiteral(s, n))
    case (s, LiteralExp(_, n : BigInt, _)) =>
      ivector(sec.integerLiteral(s, n))
    case (s, LiteralExp(_, null, _)) =>
      ivector(sec.nullLiteral(s))
    case (s, TupleExp(Seq(e))) =>
      eval(s, e)
    case (s, TupleExp(es)) =>
      var rs : ISeq[(S, ISeq[V])] = ivector((s, ivectorEmpty))
      for (e <- es) {
        rs = for {
          (s2, vs) <- rs
          (s3, v) <- eval(s2, e)
        } yield (s3, vs :+ v)
      }
      rs.map(sec.tupleCon)
    case (s, UnaryExp(op, e)) =>
      for {
        re1 <- eval(s, e)
        re2 <- sec.unaryOp(op, re2s(re1), re2v(re1))
      } yield re2
    case (s, e : NameExp) if sp.isVar(e) =>
      sec.variable(s, e.name)
    case (s, e : NameExp) if sp.extUri(e, sec.hasExpExt _).isDefined =>
      sec.uriValue(s, sp.extUri(e, sec.hasExpExt _).get)
    case (s, e : NameExp) if sp.extUri(e, sec.hasActionExt _).isDefined =>
      sec.uriValue(s, sp.extUri(e, sec.hasActionExt _).get)
    case (s, e : NameExp) if sp.procedureUri(e).isDefined =>
      sec.uriValue(s, sp.procedureUri(e).get)
    case (s, e : NameExp) if sp.funUri(e).isDefined =>
      sec.uriValue(s, sp.funUri(e).get)
    case (s, e : NameExp) if !e.name.hasResourceInfo =>
      sec.variable(s, e.name)
    case (s, BinaryExp(op, e1, e2)) =>
      sec.binaryOpMode(op) match {
        case BinaryOpMode.LAZY_LEFT =>
          for {
            re1 <- eval(s, e2)
            re2 <- sec.lazyLBinaryOp(op, re2s(re1), { s => eval(s, e1) }, re2v(re1))
          } yield re2
        case BinaryOpMode.LAZY_RIGHT =>
          for {
            re1 <- eval(s, e1)
            re2 <- sec.lazyRBinaryOp(op, re2s(re1), re2v(re1), { s => eval(s, e2) })
          } yield re2
        case BinaryOpMode.REGULAR =>
          for {
            re1 <- eval(s, e1)
            re2 <- eval(re2s(re1), e2)
            re3 <- sec.binaryOp(op, re2s(re2), re2v(re1), re2v(re2))
          } yield re3
      }
    case (s, AccessExp(e, f)) if !f.hasResourceInfo || sp.isFieldAccess(f) =>
      for {
        re1 <- eval(s, e)
        re2 <- sec.field(re2s(re1), re2v(re1), f)
      } yield re2
    case (s, IndexingExp(e, ies)) if ies.length == 1 =>
      for {
        re1 <- eval(s, e)
        re2 <- eval(re2s(re1), ies(0))
        re3 <- sec.index(re2s(re2), re2v(re1), re2v(re2))
      } yield re3
    case (s, e : CallExp) =>
      for {
        re1 <- eval(s, e.exp)
        (i, args) <- {
          val s2 = re2s(re1)
          val f = re2v(re1)
          if (sec.uriExtractor.isDefinedAt(f)) {
            val extUri = sec.uriExtractor(f)
            buildExtArgs(s2, extUri, e.arg).map { p => (0, p) }
          } else {
            sys.error("unhandled") // TODO: handles function and closure
          }
        }
        re3 <- i match { // TODO: handles function and closure
          case 0 =>
            val uri = sec.uriExtractor(re2v(re1))
            val r = sec.expExtCall(uri, args)
            val n = r.length
            if (n <= 1) r
            else {
              val s3 = args.productElement(0)
              s3 match {
                case s3 : ScheduleRecordingState[S] =>
                  s3.peekSchedule match {
                    case None =>
                      r.zip(0 until n).map { svj =>
                        val ((s, v), j) = svj
                        (s.asInstanceOf[ScheduleRecordingState[_]].
                          recordSchedule(uri, n, j).asInstanceOf[S], v)
                      }
                    case Some((sourceOpt, num, i)) =>
                      assert(sourceOpt == Some(uri) && num == r.length)
                      val (s4, v) = r(i)
                      ivector((s4.asInstanceOf[ScheduleRecordingState[_]].
                        popSchedule.asInstanceOf[S], v))
                  }
                case _ => r
              }
            }
        }
      } yield re3
    case (s, e : NewListExp) =>
      def rec(s : S, l : IList[Exp], acc : IVector[V]) : ISeq[(S, ISeq[V])] =
          l match {
            case e :: tail =>
              for {
                re <- eval(s, e)
                svs <- rec(re2s(re), tail, acc :+ re2v(re))
              } yield svs
            case Nil =>
              ivector((s, acc))
          }
      for {
        (s1, vs) <- rec(s, e.elements.toList, ivectorEmpty[V])
        re <- sec.newList(s1, vs)
      } yield re
    case (s, e : TypeExp) =>
      ivector((s, value2v(ec.typeProvider.typeValue(e.typeSpec))))
  }
}
