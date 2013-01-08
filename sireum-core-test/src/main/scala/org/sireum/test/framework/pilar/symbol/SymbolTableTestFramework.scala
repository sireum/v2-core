/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.framework.pilar.symbol

import org.sireum.test.framework._
import org.sireum.core.module._
import org.sireum.pilar.ast._
import org.sireum.pilar.parser._
import org.sireum.pilar.symbol._
import org.sireum.util._
import org.sireum.pipeline._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolResolverTestFramework extends TestFramework {

  def Resolving : this.type = this

  def title(s : String) : this.type = {
    _title = "Case " + casePrefix + ": " + s
    casePrefix = ""
    this
  }

  def model(code : String) =
    SymbolResolveConfiguration(title, ilist(Left(code)))

  def file(fileUri : FileResourceUri) =
    SymbolResolveConfiguration(title, ilist(Right(fileUri)))

  case class SymbolResolveConfiguration //
  (title : String, srcs : ISeq[Either[String, FileResourceUri]]) {

    test(title) {
      val job = PipelineJob()
      val options = job.properties
      
      //val job = pipeline.compute(PipelineJob().sources(srcs))
      PilarParserModule.setSources(options, srcs)
      PilarSymbolResolverModule.setParallel(options, false)
      PilarSymbolResolverModule.setHasExistingModels(options, None)
      PilarSymbolResolverModule.setHasExistingSymbolTable(options, None)
      pipeline.compute(job)
      
      val tags = job.tags

      import PilarSymbolResolverDef.WARNING_TAG_TYPE
      import PilarSymbolResolverDef.ERROR_TAG_TYPE

      if (warningF.isEmpty) {
        val f = { _ : Tag => true }
        assert(!Tag.exists(WARNING_TAG_TYPE, f, tags),
          "Unexpecting a symbol resolution warning but found:\n" +
            Tag.collateAsString(Tag.filter(WARNING_TAG_TYPE, f, tags)))
      } else
        assert(warningF.forall { f => Tag.exists(WARNING_TAG_TYPE, f, tags) },
          "The given warning function(s) do not match anything")

      if (!errorF.isEmpty) {
        assert(errorF.forall { f => Tag.exists(ERROR_TAG_TYPE, f, tags) },
          "The given error function(s) do not match anything")
      } else {
        val f = { _ : Tag => true }
        assert(!Tag.exists(WARNING_TAG_TYPE, f, tags),
          "Unexpecting a symbol resolution error but found:\n" +
            Tag.collateAsString(Tag.filter(ERROR_TAG_TYPE, f, tags)))

        //val st = job.symbolTable
        //val ms = job.models
        val st = PilarSymbolResolverModule.getSymbolTable(job.properties)
        val ms = PilarSymbolResolverModule.getModels(job.properties)
        
        assert(symtabF.forall { _(st) },
          "The symbol table does not satisfy the given constraint function(s)")
        val visitor =
          if (visitorG.isEmpty)
            Visitor.build(Visitor.map(visitorF.toList, false))
          else
            Visitor.buildEnd(Visitor.map(visitorF.toList, false), Visitor.map(visitorG.toList, false))
        ms.foreach { visitor(_) }
      }
    }

    def error(f : LocationTag => Boolean) : this.type = {
      errorF += Tag.lift(f, false)
      this
    }

    def warning(f : LocationTag => Boolean) : this.type = {
      warningF += Tag.lift(f, false)
      this
    }

    def ast_satisfies(f : VisitorFunction) : this.type = {
      visitorF += f
      this
    }

    def ast_satisfiesEnd(g : VisitorFunction) : this.type = {
      visitorG += g
      this
    }

    def satisfies(f : SymbolTable => Boolean) : this.type = {
      symtabF += f
      this
    }

    private val errorF = marrayEmpty[Tag => Boolean]
    private val warningF = marrayEmpty[Tag => Boolean]
    private var visitorF = marrayEmpty[VisitorFunction]
    private var visitorG = marrayEmpty[VisitorFunction]
    private var symtabF = marrayEmpty[SymbolTable => Boolean]
  }

  protected var _title : String = null
  protected var num = 0
  protected def title() = if (_title == null) {
    num += 1
    "Symbol resolution #" + num
  } else _title

  protected val pipeline =
    PipelineConfiguration(
      "Parser Test Pipeline",
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