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
        !toInteger.isDefinedAt(e1) && toInteger.isDefinedAt(e2) =>
        rec(BinaryExp(mirrorRelationalOp(op), e2, e1))
    })

    rec(exp)
  }
}