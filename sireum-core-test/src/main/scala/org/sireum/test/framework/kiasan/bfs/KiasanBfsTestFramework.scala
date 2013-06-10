/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.framework.kiasan.bfs

import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.test.framework._
import org.sireum.util._
import org.sireum.kiasan._
import org.sireum.pilar.eval._
import org.sireum.pipeline._
import org.sireum.core.module._
import org.sireum.extension._
import org.sireum.pilar.symbol._
import org.sireum.topi._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanBfsTestFramework[S <: Kiasan.KiasanState[S], R, C]
    extends TestFramework {
  self =>

  def Executing : this.type = this

  def file(fUri : FileResourceUri) = new KiasanBfsConfiguration(fUri)

  def parallelMode = true

  def parallelThreshold : Int = 1

  def parallelismLevel : Int = 0

  def extensions : ISeq[ExtensionCompanion]

  def config(st : SymbolTable) : EvaluatorConfiguration[S, Value, R, C, ISeq[S]]

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  case class ResultingStates(endStates : ISeq[S], errorStates : ISeq[S],
                             depthBoundExStates : ISeq[S])

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  class KiasanBfsConfiguration(
      val source : FileResourceUri,
      var stStates : (SymbolTable, EvaluatorConfiguration[S, Value, R, C, ISeq[S]]) => ISeq[S] = { (st, ec) => ivectorEmpty },
      var _depthBound : Int = 10) {

    def on(f : (SymbolTable, EvaluatorConfiguration[S, Value, R, C, ISeq[S]]) => ISeq[S]) : this.type = {
      stStates = f
      this
    }

    def withDepthBound(bound : Int) : this.type = {
      _depthBound = bound
      this
    }

    def gives(s : String) : this.type = {
      resultText = s;
      test(toString(" gives " + resultText)) {
        try {
          val sr = exe
          funs.foreach { f => f(sr) }
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

    def satisfying(f : ResultingStates => Unit) : this.type = {
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
          exe
        }
        if (f != null) {
          f(t)
        }
      }
    }

    protected def toString(mode : String) =
      caseString +
        "Evaluating %s %s".format(source, mode)

    protected def exe : ResultingStates = {
      val job = PipelineJob()

      {
        import PilarParserModule.ProducerView._
        import PilarSymbolResolverModule.ProducerView._

        job.sources = ivector(Right(source))
        job.parallel = true
        job.hasExistingModels = None
        job.hasExistingSymbolTable = None
      }

      pipeline.compute(job)

      val tags = job.tags

      tags should be('empty)

      val st = {
        import PilarSymbolResolverModule.ConsumerView._
        job.symbolTable
      }

      var endStates = ivectorEmpty[S]
      var avStates = ivectorEmpty[S]
      var boundDepthExStates = ivectorEmpty[S]
      val ecs = extensions
      val conf = config(st)

      val kiasan = new KiasanBfs[S, R, C] {

        val depthBound = _depthBound

        val parallelMode = self.parallelMode

        val parallelThreshold = self.parallelThreshold

        val parallelismLevel = self.parallelismLevel

        val locationProvider = new KiasanLocationProvider[S] {
          def location(s : S) : LocationDecl = {
            st.procedureSymbolTable(s.procedure).location(s.locationIndex)
          }
        }

        val evaluator = conf.evaluator.mainEvaluator

        def initialStatesProvider = new KiasanInitialStateProvider[S] {
          val initialStates = stStates(st, conf)
        }

        val reporter = new KiasanReporter[S] {
          def foundAssertionViolation(s : S) : S = {
            avStates = s +: avStates
            s
          }
          def foundEndState(s : S) : S = {
            endStates = s +: endStates
            s
          }
          def foundDepthBoundExhaustion(s : S) : S = {
            boundDepthExStates = s +: boundDepthExStates
            s
          }
        }

        val topi = Topi.create(TopiSolver.Z3, TopiMode.Process, 7000,
          extensions : _*)
      }

      kiasan.search

      ResultingStates(endStates, avStates, boundDepthExStates)
    }

    protected var funs : ISeq[ResultingStates => Unit] = ivectorEmpty
    protected var fails : ISeq[Throwable => Unit] = ivectorEmpty
    protected var resultText = "unknown"

    protected val pipeline =
      PipelineConfiguration(
        "Kiasan Bfs Test Pipeline",
        false,
        PipelineStage(
          "Pilar Parsing",
          false,
          PilarParserModule),
        PipelineStage(
          "Pilar Symbol Resolution",
          false,
          PilarSymbolResolverModule))
  }
}