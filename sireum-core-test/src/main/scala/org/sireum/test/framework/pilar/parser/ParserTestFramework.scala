/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.framework.pilar.parser

import org.sireum.test.framework._

import org.sireum.core.module._
import org.sireum.pilar.ast._
import org.sireum.pilar.parser._
import org.sireum.util._
import org.sireum.pipeline._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ParserTestFramework extends TestFramework {

  def Parsing : this.type = this

  def model(title : String) : this.type = {
    claz = classOf[Model]
    _title = "Case " + casePrefix + ": " + title
    casePrefix = ""
    this
  }

  def annotation(title : String) : this.type = {
    claz = classOf[Annotation]
    _title = "Case " + casePrefix + ": " + title
    casePrefix = ""
    this
  }

  def package_decl(title : String) : this.type = {
    claz = classOf[Model]
    _title = "Case " + casePrefix + ": " + title
    casePrefix = ""
    this
  }

  def package_elem(title : String) : this.type = {
    claz = classOf[PackageElement]
    _title = "Case " + casePrefix + ": " + title
    casePrefix = ""
    this
  }

  def location(title : String) : this.type = {
    claz = classOf[LocationDecl]
    _title = "Case " + casePrefix + ": " + title
    casePrefix = ""
    this
  }

  def transformation(title : String) : this.type = {
    claz = classOf[Transformation]
    _title = "Case " + casePrefix + ": " + title
    casePrefix = ""
    this
  }

  def action(title : String) : this.type = {
    claz = classOf[Action]
    _title = "Case " + casePrefix + ": " + title
    casePrefix = ""
    this
  }

  def jump(title : String) : this.type = {
    claz = classOf[Jump]
    _title = "Case " + casePrefix + ": " + title
    casePrefix = ""
    this
  }

  def expression(title : String) : this.type = {
    claz = classOf[Exp]
    _title = "Case " + casePrefix + ": " + title
    casePrefix = ""
    this
  }

  def type_spec(title : String) : this.type = {
    claz = classOf[TypeSpec]
    _title = "Case " + casePrefix + ": " + title
    casePrefix = ""
    this
  }

  def from_file(fileUri : FileResourceUri) =
    ParsingConfiguration(Right(fileUri))

  def from_String(code : String) =
    ParsingConfiguration(Left(code))

  protected var claz : Class[_ <: PilarAstNode] = classOf[Model]

  protected var _title = ""

  protected val pipeline =
    PipelineConfiguration(
      "Parser Test Pipeline",
      false,
      PipelineStage(
        "Pilar Parsing",
        false,
        PilarParserModule))

  case class ParsingConfiguration[T <: PilarAstNode] //
  (source : Either[String, FileResourceUri]) {

    test(title) {
      val job = PipelineJob()
      PilarParserModule.setSources(job.properties, ilist(source).toSeq)
      pipeline.compute(job)
      val tags = job.tags

      if (!errorF.isEmpty) {
        assert(errorF.forall { f =>
          import PilarParserModule._

          Tag.exists(org.sireum.core.module.PilarParserDef.ERROR_TAG_TYPE, f, tags)
        }, "The given error function(s) do not match anything")
      } else if (!tags.isEmpty) {
        assert(false,
          "Expecting no error while parsing, but found:\n" +
            Tag.collateAsString(tags))
      } else {
        val visitor =
          if (visitorG.isEmpty)
            Visitor.build(Visitor.map(visitorF.toList, false))
          else
            Visitor.buildEnd(Visitor.map(visitorF.toList, false), Visitor.map(visitorG.toList, false))

        val model = PilarParserModule.getModels(job.properties)(0)
        visitor(model)
      }
    }

    def error(f : LocationTag => Boolean) : this.type = {
      errorF += Tag.lift(f, false)
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

    private def title =
      ("Parsing %s from %s ").format(
        claz.getSimpleName + (if (_title == "") "" else " " + _title),
        source match {
          case Left(code)     => "string " + code
          case Right(fileUri) => "file " + fileUri
        })

    private val errorF = marrayEmpty[Tag => Boolean]
    private var visitorF = marrayEmpty[VisitorFunction]
    private var visitorG = marrayEmpty[VisitorFunction]
  }
}