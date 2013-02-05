/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.ast

import org.sireum.pilar.ast._
import org.sireum.util._
import scala.collection.mutable.WrappedArray
import org.apfloat.Apint

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object PilarAstUtil {
  final val NOT_UNOP = "!"
  final val COMPLEMENT_UNOP = "~"
  final val PLUS_UNOP = "+"
  final val MINUS_UNOP = "-"
  final val ADD_BINOP = "+"
  final val SUB_BINOP = "-"
  final val MUL_BINOP = "*"
  final val DIV_BINOP = "/"
  final val REM_BINOP = "%"
  final val EQ_BINOP = "=="
  final val NE_BINOP = "!="
  final val LT_BINOP = "<"
  final val GT_BINOP = ">"
  final val LE_BINOP = "<="
  final val GE_BINOP = ">="
  final val SHL_BINOP = "^<"
  final val SHR_BINOP = "^>"
  final val USHR_BINOP = "^>>"
  final val COND_AND_BINOP = "&&"
  final val COND_OR_BINOP = "||"
  final val COND_IMPLY_BINOP = "==>"
  final val COND_IMPLIED_BINOP = "<=="
  final val LOGICAL_AND_BINOP = "&&&"
  final val LOGICAL_OR_BINOP = "|||"
  final val LOGICAL_IMPLY_BINOP = "===>"
  final val LOGICAL_IMPLIED_BINOP = "<==="
  final val DISTINCT_BINOP = "<!"

  @inline
  def varUri(x : NameUser) =
    if (x.hasResourceInfo) x.uri
    else x.name

  def toBigInt(l : LiteralExp) : BigInt = {
    (l.typ : @unchecked) match {
      case LiteralType.INT     => BigInt(l.literal.asInstanceOf[Int])
      case LiteralType.LONG    => BigInt(l.literal.asInstanceOf[Long])
      case LiteralType.INTEGER => l.literal.asInstanceOf[BigInt]
    }
  }

  def getJumps(l : LocationDecl) : ISeq[Option[Jump]] = {
    l match {
      case l : EmptyLocation   => ivectorEmpty
      case l : ActionLocation  => ivectorEmpty
      case l : JumpLocation    => ivector(Some(l.jump))
      case l : ComplexLocation => l.transformations.map { t => t.jump }
    }
  }

  def getLHSs(a : PilarAstNode) : MIdSet[Exp] = {
    var result = idsetEmpty[Exp]

      def getLHSRec(e : Exp) : Unit =
        e match {
          case te : TupleExp => te.exps.foreach(getLHSRec)
          case _             => result(e) = e
        }

    a match {
      case aa : AssignAction => getLHSRec(aa.lhs)
      case cj : CallJump =>
        if (cj.lhs.isDefined)
          getLHSRec(cj.lhs.get)
      case _ =>
    }
    result
  }

  @inline
  def toLiteral(n : Int) =
    LiteralExp(LiteralType.INT, n, java.lang.Integer.toString(n))

  @inline
  def toLiteral(n : Long) =
    LiteralExp(LiteralType.LONG, n, java.lang.Long.toString(n))

  @inline
  def toLiteral(n : java.math.BigInteger) =
    LiteralExp(LiteralType.INTEGER, new BigInt(n), n.toString())

  @inline
  def toLiteral(n : Apint) =
    LiteralExp(LiteralType.INTEGER, new BigInt(n.toBigInteger()), n.toString())

  @inline
  def toLiteral(n : BigInt) =
    LiteralExp(LiteralType.INTEGER, n, n.toString())
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
protected case class Id(id : String, line : Int, column : Int)
