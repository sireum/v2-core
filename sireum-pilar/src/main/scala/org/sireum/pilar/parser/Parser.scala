/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.parser

import java.io._
import java.net._

import org.antlr.v4.runtime._

import org.sireum.pilar.ast._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Parser {
  import scala.reflect.runtime.{ universe => ru }

  final val MODEL_TYPE = ru.typeOf[Model]
  final val ANNOTATION_TYPE = ru.typeOf[Annotation]
  final val BODY_TYPE = ru.typeOf[ImplementedBody]
  final val LOCATION_TYPE = ru.typeOf[LocationDecl]
  final val TRANSFORMATION_TYPE = ru.typeOf[Transformation]
  final val ACTION_TYPE = ru.typeOf[Action]
  final val JUMP_TYPE = ru.typeOf[Jump]
  final val EXP_TYPE = ru.typeOf[Exp]

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  sealed trait Result[T <: PilarAstNode] {
    def ast : T
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class ModelResult(ast : Model) extends Result[Model]

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class AnnotationResult(ast : Annotation) extends Result[Annotation]

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class BodyResult(ast : ImplementedBody) extends Result[ImplementedBody]

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class LocationResult(ast : LocationDecl) extends Result[LocationDecl]

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class TransformationResult(ast : Transformation) extends Result[Transformation]

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class ActionResult(ast : Action) extends Result[Action]

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class JumpResult(ast : Jump) extends Result[Jump]

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class ExpResult(ast : Exp) extends Result[Exp]

  def tokenize(reader : java.io.Reader) = {
    import scala.collection.JavaConversions._
    val input = new ANTLRInputStream(reader)
    val lexer = new Antlr4PilarLexer(input)
    var result = ivectorEmpty[Token]
    var t = lexer.nextToken
    while (t.getType != Token.EOF) {
      result :+= t
      t = lexer.nextToken
    }
    result
  }

  def tokenizeF(reader : java.io.Reader)(f : Token => Unit) {
    import scala.collection.JavaConversions._
    val input = new ANTLRInputStream(reader)
    val lexer = new Antlr4PilarLexer(input)
    var t = lexer.nextToken
    while (t.getType != Token.EOF) {
      f(t)
      t = lexer.nextToken
    }
  }

  def parse[T <: PilarAstNode : ru.TypeTag](
    source : Either[String, ResourceUri],
    reporter : ErrorReporter,
    storeLoc : Boolean) : Option[Result[T]] = {
    import java.io._
    import java.net._
    val reader = source match {
      case Left(text)     => new StringReader(text)
      case Right(fileUri) => new FileReader(new File(new URI(fileUri)))
    }
    parse(source, reader, reporter, storeLoc)
  }

  def parse[T <: PilarAstNode : ru.TypeTag](
    source : Either[String, ResourceUri],
    reader : java.io.Reader,
    reporter : ErrorReporter,
    storeLoc : Boolean) : Option[Result[T]] = try {
    val text = FileUtil.readFile(reader)
    val sr = new java.io.StringReader(text)
    try {
      val src = source match {
        case Left(text)     => None
        case Right(fileUri) => Some(fileUri)
      }
      val input = new ANTLRInputStream(sr)
      val lexer = new Antlr4PilarLexer(input)
      val tokens = new CommonTokenStream(lexer)
      val parser = new Antlr4PilarParser(tokens)
      parser.removeErrorListeners
      var success = true
      parser.addErrorListener(new BaseErrorListener {
        override def syntaxError(recognizer : Recognizer[_, _],
                                 offendingSymbol : Object, line : Int,
                                 charPositionInLine : Int, msg : String,
                                 e : RecognitionException) {
          success = false
          val token = offendingSymbol.asInstanceOf[Token]
          reporter.report(src, line, charPositionInLine,
            msg + s" (token=${token.getType})")
        }
      })
      (ru.typeOf[T] match {
        case t if t =:= MODEL_TYPE =>
          val tree = parser.modelFile
          if (success)
            Some(ModelResult(Antlr4PilarParserVisitor[Model](
              src, tree, reporter, storeLoc)))
          else None
        case t if t =:= ANNOTATION_TYPE =>
          val tree = parser.annotationFile
          if (success)
            Some(AnnotationResult(Antlr4PilarParserVisitor[Annotation](
              src, tree, reporter, storeLoc)))
          else None
        case t if t =:= BODY_TYPE =>
          val tree = parser.bodyFile
          if (success)
            Some(BodyResult(Antlr4PilarParserVisitor[ImplementedBody](
              src, tree, reporter, storeLoc)))
          else None
        case t if t =:= LOCATION_TYPE =>
          val tree = parser.locationFile
          if (success)
            Some(LocationResult(Antlr4PilarParserVisitor[LocationDecl](
              src, tree, reporter, storeLoc)))
          else None
        case t if t =:= TRANSFORMATION_TYPE =>
          val tree = parser.transformationFile
          if (success)
            Some(TransformationResult(Antlr4PilarParserVisitor[Transformation](
              src, tree, reporter, storeLoc)))
          else None
        case t if t =:= ACTION_TYPE =>
          val tree = parser.actionFile
          if (success)
            Some(ActionResult(Antlr4PilarParserVisitor[Action](
              src, tree, reporter, storeLoc)))
          else None
        case t if t =:= JUMP_TYPE =>
          val tree = parser.jumpFile
          if (success)
            Some(JumpResult(Antlr4PilarParserVisitor[Jump](
              src, tree, reporter, storeLoc)))
          else None
        case t if t =:= EXP_TYPE =>
          val tree = parser.expFile
          if (success)
            Some(ExpResult(Antlr4PilarParserVisitor[Exp](
              src, tree, reporter, storeLoc)))
          else None
      }).asInstanceOf[Option[Result[T]]]
    } finally sr.close
  } finally reader.close

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
}