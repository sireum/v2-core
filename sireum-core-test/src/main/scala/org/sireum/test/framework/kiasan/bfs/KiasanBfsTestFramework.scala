/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
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

  def file(fUri : FileResourceUri) = {
    val prefix = casePrefix
    casePrefix = ""
    new KiasanBfsConfiguration(prefix, fUri)
  }

  def parallelThreshold : Int = 1

  def parallelismLevel : Int = Runtime.getRuntime.availableProcessors

  def extensions : ISeq[ExtensionCompanion]

  def evaluator(st : SymbolTable) : Evaluator[S, R, C, ISeq[S]]

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  case class ResultingStates(endStates : ISeq[S], errorStates : ISeq[S])

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  class KiasanBfsConfiguration(val casePrefix : String,
                               val source : FileResourceUri,
                               var stStates : SymbolTable => ISeq[S] = { st => ilistEmpty },
                               var _depthBound : Int = 10) {

    def on(f : SymbolTable => ISeq[S]) : this.type = {
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
      "Case " + casePrefix + ": " +
        "Evaluating %s %s".format(source, mode)

    protected def exe : ResultingStates = {
      val job = PipelineJob()
      val options = job.properties

      PilarParserModule.setSources(options, ilist(Right(source)))
      PilarSymbolResolverModule.setParallel(options, true)
      PilarSymbolResolverModule.setHasExistingModels(options, None)
      PilarSymbolResolverModule.setHasExistingSymbolTable(options, None)
      pipeline.compute(job)

      val tags = job.tags

      tags should be('empty)

      val st = PilarSymbolResolverModule.getSymbolTable(options)

      var endStates = ilistEmpty[S]
      var avStates = ilistEmpty[S]
      val ecs = extensions

      val kiasan = new KiasanBfs[S, R, C] {
        def depthBound = _depthBound

        def parallelThreshold = self.parallelThreshold

        def parallelismLevel = self.parallelismLevel

        def locationProvider = new KiasanLocationProvider[S] {
          def location(s : S) : LocationDecl = {
            st.procedureSymbolTable(s.procedure).location(s.locationIndex)
          }
        }

        def evaluator = self.evaluator(st)

        def initialStatesProvider = new KiasanInitialStateProvider[S] {
          def initialStates = stStates(st)
        }

        def reporter = new KiasanReporter[S] {
          def foundAssertionViolation(s : S) {
            avStates = s :: avStates
          }
          def foundEndState(s : S) {
            endStates = s :: endStates
          }
        }

        def topi = Topi.create(TopiSolver.Z3, TopiMode.Process, 1000,
          extensions : _*)
      }

      kiasan.search

      ResultingStates(endStates, avStates)
    }

    protected var funs : ISeq[ResultingStates => Unit] = ilistEmpty
    protected var fails : ISeq[Throwable => Unit] = ilistEmpty
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