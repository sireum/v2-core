/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.ast

import org.sireum.util._
import org.sireum.util.math._
import scala.annotation.tailrec

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object PilarExpSimplifier {
  val toInteger : Exp --> BigInt = {
    case ValueExp(v : IsInteger)      => v.asInteger.toBigInt
    case LiteralExp(_, n : BigInt, _) => n
  }

  @inline
  def isAddSubMulDiv(op : BinaryOp) = {
    import PilarAstUtil._
    op == ADD_BINOP || op == SUB_BINOP || op == MUL_BINOP || op == DIV_BINOP
  }

  val simplify = simplify_x_rop_c_x_nrop_d _

  def simplify_x_rop_c_x_nrop_d(exps : ISeq[Exp]) : ISeq[Exp] = {
    import PilarAstUtil._
    var result = simplify_x_rop_c(exps)
    for (op <- Seq(GT_BINOP, GE_BINOP, LT_BINOP, LE_BINOP)) {
      var (beIs, rest) = result.partition(_ match {
        case BinaryExp(`op`, e1, e2) =>
          isInequality(op) && !toInteger.isDefinedAt(e1) &&
            toInteger.isDefinedAt(e2)
        case _ => false
      })
      var r = ivectorEmpty[Exp]
      for (beI <- beIs) {
        val BinaryExp(_, e1, e2) = beI
        val nop = compRelationalOp(op)
        val (beJs, rest2) = rest.partition(_ match {
          case BinaryExp(`nop`, `e1`, e3) => toInteger.isDefinedAt(e3)
          case _                          => false
        })
        rest = rest2
        val vOpt =
          if (!beJs.isEmpty) {
            val Seq(BinaryExp(_, _, e3)) = beJs
            val v2 = toInteger(e2)
            val v3 = toInteger(e3)

            op match {
              // x > v2, x <= v3
              case GT_BINOP if v2 + 1 == v3 => Some(v3)
              // x >= v2, x < v3
              case GE_BINOP if v2 == v3 - 1 => Some(v2)
              // x < v2, x >= v3
              case LT_BINOP if v2 - 1 == v3 => Some(v3)
              // x <= v2, x > v3
              case LE_BINOP if v2 == v3 + 1 => Some(v2)
              case _                        => None
            }
          } else None
        vOpt match {
          case Some(v) =>
            r :+= BinaryExp(EQ_BINOP, e1, toLiteral(v))
          case _ =>
            r :+= beI
            r ++= beJs
        }
      }
      result = rest ++ r
    }
    result
  }

  def simplify_x_rop_c(exps : ISeq[Exp]) : ISeq[Exp] = {
    import PilarAstUtil._
    var (temp, r) = exps.map(simplify).partition(_ match {
      case BinaryExp(op, e1, e2) =>
        isInequality(op) && !toInteger.isDefinedAt(e1) &&
          toInteger.isDefinedAt(e2)
      case _ => false
    })

    while (!temp.isEmpty) {
      val BinaryExp(op, e1, e2) = temp.head
      val (orbit, rest) = temp.partition(_ match {
        case BinaryExp(`op`, `e1`, _) => true
        case _                        => false
      })
      if (orbit.size > 1) {
        val values =
          orbit.map(x => toInteger(x.asInstanceOf[BinaryExp].right))
        val v =
          op match {
            case GT_BINOP | GE_BINOP => values.max
            case LT_BINOP | LE_BINOP => values.min
          }
        r :+= BinaryExp(op, e1, toLiteral(v))
      } else {
        r :+= orbit.head
      }
      temp = rest
    }
    r
  }

  def simplify(exp : Exp) : Exp = {
    import PilarAstUtil._

    lazy val rec : Exp => Exp = Rewriter.build[Exp]({

      case e @ BinaryExp(op1, BinaryExp(op2, e1, e2), e3) if isRelational(op1) &&
        toInteger.isDefinedAt(e2) && toInteger.isDefinedAt(e3) &&
        (op2 == ADD_BINOP || op2 == SUB_BINOP) =>
        val v2 = toInteger(e2)
        val v3 = toInteger(e3)
        val v =
          op2 match {
            case ADD_BINOP => v3 - v2
            case SUB_BINOP => v3 + v2
          }
        rec(BinaryExp(op1, e1, toLiteral(v)))

      case e @ BinaryExp(op1, BinaryExp(op2, e1, e2), e3) if op1 == op2 &&
        isAddSubMulDiv(op1) && toInteger.isDefinedAt(e2) &&
        toInteger.isDefinedAt(e3) =>
        val v2 = toInteger(e2)
        val v3 = toInteger(e3)
        rec(op1 match {
          case ADD_BINOP | SUB_BINOP => BinaryExp(op1, e1, toLiteral(v2 + v3))
          case MUL_BINOP | DIV_BINOP => BinaryExp(op1, e1, toLiteral(v2 * v3))
        })

      case e @ BinaryExp(op1, e1, BinaryExp(op2, e2, e3)) if op1 == op2 &&
        isAddSubMulDiv(op1) && toInteger.isDefinedAt(e1) &&
        toInteger.isDefinedAt(e2) =>
        val v1 = toInteger(e1)
        val v2 = toInteger(e2)
        rec(op1 match {
          case ADD_BINOP | SUB_BINOP => BinaryExp(op1, toLiteral(v1 + v2), e3)
          case MUL_BINOP | DIV_BINOP => BinaryExp(op1, toLiteral(v1 * v2), e3)
        })

      case e @ BinaryExp(op, e1, e2) if isInequality(op) &&
        toInteger.isDefinedAt(e1) && !toInteger.isDefinedAt(e2) =>
        rec(BinaryExp(mirrorRelationalOp(op), e2, e1))
    })

    rec(exp)
  }
}