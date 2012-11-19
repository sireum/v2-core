/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.parser

import org.antlr.runtime._
import org.antlr.runtime.tree._

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.WrappedArray
import scala.collection._

import org.sireum.pilar.ast._
import org.sireum.util._
import org.sireum.util.math.Rational

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object PilarParser {

  def apply[T](source : Either[String, FileResourceUri],
               reporter : ErrorReporter,
               claz : Class[T] = classOf[Model],
               lineOffset : Int = 0) =
    parse[T](source, reporter, claz, lineOffset)

  def parseProduction[T] //
  (source : Option[FileResourceUri],
   antlrss : ANTLRStringStream,
   reporter : ErrorReporter,
   claz : Class[T] = classOf[Model],
   lineOffset : Int = 0) : Option[T] = {
    var hasParseError = false
    val r = new ErrorReporter {
      override def report(source : Option[FileResourceUri], line : Int,
                          column : Int, message : String) : Unit = {
        hasParseError = true
        reporter.report(source, line, column, message)
      }
    }
    val src = if (source.isEmpty) None else Some(source.get.intern)
    val plexer = new Lexer(src, antlrss, r, lineOffset)
    val cts = new CommonTokenStream(plexer)
    val pparser = new Parser(src, cts, r, lineOffset)
    pparser.setTreeAdaptor(new PilarTreeAdaptor)
    val t = (claz.getName match {
      case "org.sireum.pilar.ast.Model"          => pparser.modelFile
      case "org.sireum.pilar.ast.Annotation"     => pparser.annotationFile
      case "org.sireum.pilar.ast.PackageDecl"    => pparser.packageDeclarationFile
      case "org.sireum.pilar.ast.PackageElement" => pparser.packageElementFile
      case "org.sireum.pilar.ast.LocationDecl"   => pparser.locationFile
      case "org.sireum.pilar.ast.Transformation" => pparser.transformationFile
      case "org.sireum.pilar.ast.Action"         => pparser.actionFile
      case "org.sireum.pilar.ast.Jump"           => pparser.jumpFile
      case "org.sireum.pilar.ast.Exp"            => pparser.expFile
      case "org.sireum.pilar.ast.TypeSpec"       => pparser.typeFile
    }).getTree match {
      case t : Tree => t
      case _        => null
    }
    if (hasParseError) None
    else Some(transform(t, source, reporter, lineOffset).asInstanceOf[T])
  }

  def parse[T](source : Either[String, FileResourceUri],
               reporter : ErrorReporter,
               claz : Class[T] = classOf[Model],
               lineOffset : Int = 0) =
    source match {
      case Left(code) =>
        parseString(code, reporter, None, claz, lineOffset)
      case Right(fileResourceUri) =>
        parseLoadFile(fileResourceUri, reporter, claz, lineOffset)
    }

  def parseWithErrorAsString[T](source : Either[String, FileResourceUri],
                                claz : Class[T] = classOf[Model],
                                lineOffset : Int = 0) =
    source match {
      case Left(code) =>
        parseStringWithErrorAsString(code, None, claz, lineOffset)
      case Right(fileResourceUri) =>
        parseLoadFileWithErrorAsString(fileResourceUri, claz, lineOffset)
    }

  def parseString[T](source : String,
                     reporter : ErrorReporter,
                     sourceFile : Option[FileResourceUri] = None,
                     claz : Class[T] = classOf[Model],
                     lineOffset : Int = 0) =
    parseProduction(None, new ANTLRStringStream(source), reporter, claz,
      lineOffset)

  def parseStringWithErrorAsString[T](source : String,
                                      sourceFile : Option[FileResourceUri] = None,
                                      claz : Class[T] = classOf[Model],
                                      lineOffset : Int = 0) = {
    val reporter = new StringErrorReporter
    val n = parseProduction(None, new ANTLRStringStream(source), reporter, claz,
      lineOffset)
    (n, reporter.errorAsString)
  }

  def parseLoadFile[T](sourceUri : FileResourceUri,
                       reporter : ErrorReporter,
                       claz : Class[T] = classOf[Model],
                       lineOffset : Int = 0) = {
    val (code, absSourceUri) = FileUtil.readFile(sourceUri)
    parseProduction(Some(absSourceUri), new ANTLRStringStream(code), reporter,
      claz, lineOffset)
  }

  def parseLoadFileWithErrorAsString[T](sourceUri : FileResourceUri,
                                        claz : Class[T] = classOf[Model],
                                        lineOffset : Int = 0) = {
    val reporter = new StringErrorReporter
    val (code, absSourceUri) = FileUtil.readFile(sourceUri)
    val n = parseProduction(Some(absSourceUri), new ANTLRStringStream(code),
      reporter, claz, lineOffset)
    (n, reporter.errorAsString)
  }

  def parseFile[T](sourceFile : FileResourceUri,
                   reporter : ErrorReporter,
                   claz : Class[T] = classOf[Model],
                   lineOffset : Int = 0) =
    parseProduction(Some(sourceFile),
      new ANTLRFileStream(FileUtil.toFilePath(sourceFile)),
      reporter,
      claz,
      lineOffset)

  def parseFileWithErrorAsString[T](sourceFile : FileResourceUri,
                                    claz : Class[T] = classOf[Model],
                                    lineOffset : Int = 0) = {
    val reporter = new StringErrorReporter
    val n = parseProduction(Some(sourceFile),
      new ANTLRFileStream(FileUtil.toFilePath(sourceFile)),
      reporter,
      claz,
      lineOffset)
    (n, reporter.errorAsString)
  }

  trait ErrorReporter {
    def report(source : Option[FileResourceUri], line : Int,
               column : Int, message : String)
  }

  class CollectingErrorReporter extends ErrorReporter {
    val errors = mmapEmpty[Option[FileResourceUri], MArray[(Int, Int, String)]]

    def report(source : Option[FileResourceUri], line : Int,
               column : Int, message : String) =
      errors.getOrElse(source, marrayEmpty) += ((line, column, message))
  }

  class StringErrorReporter(collectErrors : Boolean = false) extends ErrorReporter {
    val errors =
      if (collectErrors) marrayEmpty[(Option[FileResourceUri], Int, Int, String)]
      else null

    def report(source : Option[FileResourceUri], line : Int,
               column : Int, message : String) = {
      val fileUri = source.getOrElse(UNKNOWN_FILE)
      errorMap(fileUri) = "  - [%d, %d] %s\n".format(line, column, message)

      if (collectErrors)
        errors += ((source, line, column, message))
    }

    def errorAsString = {
      val sb = new StringBuilder
      errorMap.foreach { e =>
        if (e._1 != UNKNOWN_FILE)
          sb.append("* On file %s:".format(e._1))
        else
          sb.append("* Errors:")
        sb.append(e._2)
      }
      sb.toString
    }

    private val errorMap = mmapEmpty[FileResourceUri, String]
    private val UNKNOWN_FILE : FileResourceUri = "?"
  }

  private def transform(t : Tree,
                        source : Option[FileResourceUri],
                        reporter : ErrorReporter,
                        lineOffset : Int) : PilarAstNode = {
    val vc = new ParserVisitorContext
    vc.source = source
    vc.reporter = reporter
    vc.lineOffset = lineOffset
    new ParserVisitor(vc).visit(t)
    return vc.popResult[PilarAstNode]
  }

  private class Parser //
  (source : Option[FileResourceUri], input : TokenStream,
   reporter : ErrorReporter, lineOffset : Int)
      extends AntlrPilarParser(input) {

    override def displayRecognitionError(tokenNames : Array[String],
                                         re : RecognitionException) =
      reporter.report(source, lineOffset + re.line, re.charPositionInLine + 1,
        getErrorMessage(re, tokenNames))
  }

  private class Lexer //
  (source : Option[FileResourceUri], input : CharStream,
   reporter : ErrorReporter, lineOffset : Int)
      extends AntlrPilarLexer(input) {

    override def displayRecognitionError(tokenNames : Array[String],
                                         re : RecognitionException) =
      reporter.report(source, lineOffset + re.line, re.charPositionInLine + 1,
        getErrorMessage(re, tokenNames))
  }

  private class PilarTree(t : Token) extends CommonTree(t) {
    var line = 0
    var charOffset = 0
    var first = true

    def computePosition = {
      first = false;
      if ((token == null) || (token.getLine == 0)
        || (token.getCharPositionInLine == -1)) {
        val count = getChildCount
        if (count > 0)
          for (i <- 0 until count if charOffset == 0) {
            val c = getChild(i)
            line = c.getLine
            charOffset = c.getCharPositionInLine
          }
      } else {
        line = token.getLine
        charOffset = token.getCharPositionInLine
      }
    }

    override def getCharPositionInLine = {
      if (first) computePosition
      charOffset
    }

    override def getLine = {
      if (first) computePosition
      line
    }
  }

  private class PilarTreeAdaptor extends CommonTreeAdaptor {
    override def create(t : Token) = new PilarTree(t)
  }
}
