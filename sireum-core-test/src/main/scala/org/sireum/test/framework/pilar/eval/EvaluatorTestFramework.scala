/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.framework.pilar.eval

import org.sireum.pilar.ast._
import org.sireum.pilar.parser._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.util._

import org.sireum.test.framework._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait EvaluatorTestFramework[S <: State[S]] extends TestFramework {

  def Evaluating : this.type = this

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  abstract class EvalConfiguration[R] //
  (var state : S = null.asInstanceOf[S]) {

    def casePrefix : String
    def source : Either[String, FileResourceUri]

    def on(s : S) : this.type = { this.state = s; this }

    def gives(s : String) : this.type = {
      resultText = s;
      test(toString(" gives " + resultText)) {
        try {
          val r = eval
          funs.foreach { f => f(r) }
          post(r)
        } catch {
          case t : Throwable =>
            val sw = new java.io.StringWriter
            t.printStackTrace(new java.io.PrintWriter(sw))
            assert(!fails.isEmpty, sw.toString)
            fails.foreach { f => f(t) }
        }
      }
      this
    }

    def satisfying(f : R => Unit) : this.type = {
      funs = f +: funs
      this
    }

    def failing(f : Throwable => Unit) : this.type = {
      fails = f +: fails
      this
    }

    def throws[T <: Throwable](claz : Class[T]) //
    (implicit manifest : Manifest[T]) : Unit = {
      throws[T](null : (T => Unit))
    }

    def throws[T <: Throwable](f : => (T => Unit)) //
    (implicit manifest : Manifest[T]) : Unit = {
      test(toString("throws " + manifest.runtimeClass.getName)) {
        val t = intercept[T] {
          eval
        }
        if (f != null) {
          f(t)
        }
      }
    }

    protected def toString(mode : String) =
      (if (casePrefix != "") ("Case " + casePrefix + ": ") else "") +
        "Evaluating %s %s".format(
          source match {
            case Left(code) =>
              code.replace("\n", " ")
            case Right(fileResourceUri) =>
              fileResourceUri
          }, mode) + (if (state != null) " on state " + state else "")

    protected def eval : R
    protected def post(r : R)

    protected var funs : ISeq[R => Unit] = ilistEmpty
    protected var fails : ISeq[Throwable => Unit] = ilistEmpty
    protected var resultText = "unknown"
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ExpEvaluatorTestFramework[S <: State[S], Re, R] extends EvaluatorTestFramework[S] {
  type Result = {
    def value : Value
  }

  import language.implicitConversions

  implicit def rf2erf(rf : Re => Unit) : R => Unit

  def newExpEvaluator(s : S) : ExpEvaluator[S, R]

  def expression(code : String) = {
    val prefix = casePrefix
    casePrefix = ""
    ExpEvalConfiguration(prefix, Either3.First(code))
  }

  def expressionFile(fileUri : FileResourceUri) = {
    val prefix = casePrefix
    casePrefix = ""
    ExpEvalConfiguration(prefix, Either3.Second(fileUri))
  }

  def expression(e : Exp) = {
    val prefix = casePrefix
    casePrefix = ""
    ExpEvalConfiguration(prefix, Either3.Third(e))
  }

  def expRewriter(exp : Exp) : Exp = identity(exp)

  def postProcess(casePrefix : String, s : S, expS : String, exp : Exp, r : R) {}

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  case class ExpEvalConfiguration //
  (casePrefix : String,
   src : Either3[String, FileResourceUri, Exp])
      extends EvalConfiguration[R] {

    var expS : String = ""
    var exp : Exp = null

    def source =
      src match {
        case Either3.First(s)  => Left(s)
        case Either3.Second(s) => Right(s)
        case Either3.Third(e)  => Left(e.toString.replace(')', ']').replace('(', '[')) // TODO: pretty print exp
      }

    protected def post(r : R) {
      postProcess(casePrefix, state, expS, exp, r)
    }

    protected def eval : R = {
      import TestUtil._

      val (eOpt, errors) =
        src match {
          case Either3.First(s) =>
            expS = s
            parse(source, classOf[Exp])
          case Either3.Second(s) =>
            expS = FileUtil.readFile(s)._1
            parse(source, classOf[Exp])
          case Either3.Third(e) =>
            (Some(e), "")
        }

      assert(errors == "", "Expecting no parse error, but found:\n" + errors)

      val ee = newExpEvaluator(state)
      exp = expRewriter(eOpt.get)
      if (state == null)
        ee.evalExp(null.asInstanceOf[S], exp)
      else
        ee.evalExp(state, exp)
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ActionEvaluatorTestFramework[S <: State[S], Se, SR] extends EvaluatorTestFramework[S] {

  import language.implicitConversions

  implicit def se2sr(se : Se => Unit) : SR => Unit

  def newActionEvaluator(s : S) : ActionEvaluator[S, SR]

  def action(code : String) : EvalConfiguration[SR] = {
    val prefix = casePrefix
    casePrefix = ""
    ActionEvalConfiguration(prefix, Left(code))
  }

  def actionRewriter(action : Action) : Action = identity(action)

  def postProcess(casePrefix : String, s : S, actionS : String, action : Action, r : SR) {}

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  case class ActionEvalConfiguration //
  (casePrefix : String,
   source : Either[String, FileResourceUri])
      extends EvalConfiguration[SR] {

    var actionS : String = ""
    var action : Action = null

    protected def post(r : SR) {
      postProcess(casePrefix, state, actionS, action, r)
    }

    protected def eval : SR = {
      import TestUtil._

      source match {
        case Left(s)  => actionS = s
        case Right(s) => actionS = FileUtil.readFile(s)._1
      }

      val (aOpt, errors) = parse(source, classOf[Action])

      assert(errors == "", "Expecting no parse error, but found:\n" + errors)

      val ae = newActionEvaluator(state)
      val a = actionRewriter(aOpt.get)
      a.commandDescriptorInfo(None, 0, 0, 0)
      ae.evalAction(state, a)
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait JumpEvaluatorTestFramework[S <: State[S], Se, SR] extends EvaluatorTestFramework[S] {

  import language.implicitConversions

  implicit def se2sr(se : Se => Unit) : SR => Unit

  def newJumpEvaluator(s : S) : JumpEvaluator[S, SR]

  def jump(code : String) : EvalConfiguration[SR] = {
    val prefix = casePrefix
    casePrefix = ""
    JumpEvalConfiguration(prefix, Left(code))
  }

  def jumpRewriter(jump : Jump) : Jump = identity(jump)

  def postProcess(casePrefix : String, s : S, jumpS : String, jump : Jump, r : SR) {}

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  case class JumpEvalConfiguration //
  (casePrefix : String,
   source : Either[String, FileResourceUri])
      extends EvalConfiguration[SR] {

    var jumpS : String = ""
    var jump : Jump = null

    protected def post(r : SR) {
      postProcess(casePrefix, state, jumpS, jump, r)
    }

    protected def eval : SR = {
      import TestUtil._

      source match {
        case Left(s)  => jumpS = s
        case Right(s) => jumpS = FileUtil.readFile(s)._1
      }

      val (jOpt, errors) = parse(source, classOf[Jump])

      assert(errors == "", "Expecting no parse error, but found:\n" + errors)

      val je = newJumpEvaluator(state)
      val j = jumpRewriter(jOpt.get)
      j.commandDescriptorInfo(None, 0, 0, 0)
      je.evalJump(state, j)
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait TransformationEvaluatorTestFramework[S <: State[S], Se, SR]
    extends EvaluatorTestFramework[S] {

  import language.implicitConversions

  implicit def se2sr(se : Se => Unit) : SR => Unit

  def newTransformationEvaluator(s : S) : TransformationEvaluator[S, SR]

  def transformation(code : String) : EvalConfiguration[SR] = {
    val prefix = casePrefix
    casePrefix = ""
    TransformationEvalConfiguration(prefix, Left(code))
  }

  def transformationRewriter(transformation : Transformation) : Transformation =
    identity(transformation)

  def postProcess(casePrefix : String, s : S, transformationS : String, transformation : Transformation, r : SR) {}

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  case class TransformationEvalConfiguration //
  (casePrefix : String,
   source : Either[String, FileResourceUri])
      extends EvalConfiguration[SR] {

    var transformationS : String = ""
    var transformation : Transformation = null

    protected def post(r : SR) {
      postProcess(casePrefix, state, transformationS, transformation, r)
    }

    protected def eval : SR = {
      import TestUtil._

      source match {
        case Left(s)  => transformationS = s
        case Right(s) => transformationS = FileUtil.readFile(s)._1
      }

      val (tOpt, errors) = parse(source, classOf[Transformation])

      assert(errors == "", "Expecting no parse error, but found:\n" + errors)

      val te = newTransformationEvaluator(state)
      val t = transformationRewriter(tOpt.get)

      var i = 0
      for (a <- t.actions) {
        a.commandDescriptorInfo(None, 0, 0, i)
        i += 1
      }
      if (t.jump.isDefined)
        t.jump.get.commandDescriptorInfo(None, 0, 0, i)

      te.evalTransformation(state, ComplexLocation(None, ilistEmpty, ilist(t)), t)
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait LocationEvaluatorTestFramework[S <: State[S], C] extends EvaluatorTestFramework[S] {

  def newLocationEvaluator(s : S) : LocationEvaluator[S, C]

  def location(code : String) : EvalConfiguration[Transitions[S]] = {
    val prefix = casePrefix
    casePrefix = ""
    LocationEvalConfiguration(prefix, Left(code))
  }

  def locationRewriter(location : LocationDecl) : LocationDecl = identity(location)

  def postProcess(casePrefix : String, s : S, locationS : String,
                  location : LocationDecl,
                  trans : Transitions[S]) {}

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  case class LocationEvalConfiguration //
  (casePrefix : String,
   source : Either[String, FileResourceUri])
      extends EvalConfiguration[Transitions[S]] {

    var locationS : String = ""
    var location : LocationDecl = null

    protected def post(r : Transitions[S]) {
      postProcess(casePrefix, state, locationS, location, r)
    }

    protected def eval : Transitions[S] = {
      import TestUtil._

      source match {
        case Left(s)  => locationS = s
        case Right(s) => locationS = FileUtil.readFile(s)._1
      }

      val (lOpt, errors) = parse(source, classOf[LocationDecl])

      assert(errors == "", "Expecting no parse error, but found:\n" + errors)

      val le = newLocationEvaluator(state)
      location = locationRewriter(lOpt.get)
      le.transitions(state, location)
    }
  }
}
