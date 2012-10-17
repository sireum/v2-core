/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
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
  val NOT_UNOP : BinaryOp = "!"
  val COMPLEMENT_UNOP : BinaryOp = "~"
  val PLUS_UNOP : BinaryOp = "+"
  val MINUS_UNOP : BinaryOp = "-"
  val ADD_BINOP : BinaryOp = "+"
  val SUB_BINOP : BinaryOp = "-"
  val MUL_BINOP : BinaryOp = "*"
  val DIV_BINOP : BinaryOp = "/"
  val REM_BINOP : BinaryOp = "%"
  val EQ_BINOP : BinaryOp = "=="
  val NE_BINOP : BinaryOp = "!="
  val LT_BINOP : BinaryOp = "<"
  val GT_BINOP : BinaryOp = ">"
  val LE_BINOP : BinaryOp = "<="
  val GE_BINOP : BinaryOp = ">="
  val SHL_BINOP : BinaryOp = "^<"
  val SHR_BINOP : BinaryOp = "^>"
  val USHR_BINOP : BinaryOp = "^>>"
  val COND_AND_BINOP : BinaryOp = "&&"
  val COND_OR_BINOP : BinaryOp = "||"
  val COND_IMPLY_BINOP : BinaryOp = "===>"
  val COND_IMPLIED_BINOP : BinaryOp = "<==="
  val LOGICAL_AND_BINOP : BinaryOp = "&"
  val LOGICAL_OR_BINOP : BinaryOp = "|"
  val LOGICAL_IMPLY_BINOP : BinaryOp = "==>"
  val LOGICAL_IMPLIED_BINOP : BinaryOp = "<=="
  val DISTINCT_BINOP : BinaryOp = "<!"

  def toBigInt(l : LiteralExp) : BigInt = {
    (l.typ : @unchecked) match {
      case LiteralType.INT     => BigInt(l.literal.asInstanceOf[Int])
      case LiteralType.LONG    => BigInt(l.literal.asInstanceOf[Long])
      case LiteralType.INTEGER => l.literal.asInstanceOf[BigInt]
    }
  }

  def getJumps(l : LocationDecl) : ISeq[Option[Jump]] = {
    l match {
      case l : EmptyLocation   => ilistEmpty
      case l : ActionLocation  => ilistEmpty
      case l : JumpLocation    => ilist(Some(l.jump))
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
