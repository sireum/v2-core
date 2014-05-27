/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.ast

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object PilarPrecedenceRewriter {

  def precedence(e : Exp) = e match {
    case e : ValueExp         => 0
    case e : ValuesExp        => 0
    case e : NameExp          => 0
    case e : LiteralExp       => 0
    case e : TupleExp         => 0
    case e : LetExp           => 0
    case e : NewListRangedExp => 0
    case e : NewListExp       => 0
    case e : NewSetExp        => 0
    case e : NewFunctionExp   => 0
    case e : NewMultiSeqExp   => 0
    case e : NewRecordExp     => 0
    case e : NewExp           => 0
    case e : TypeExp          => 0
    case e : FunExp           => 0
    case e : AccessExp        => 0
    case e : IndexingExp      => 0
    case e : CallExp          => 0
    case e : CastExp          => 1
    case e : UnaryExp         => 2
    case e : BinaryExp =>
      import PilarAstUtil._
      e.op match {
        case MUL_BINOP | DIV_BINOP | REM_BINOP         => 3
        case ADD_BINOP | SUB_BINOP                     => 4
        case SHL_BINOP | SHR_BINOP | USHR_BINOP        => 5
        case GT_BINOP | GE_BINOP | LT_BINOP | LE_BINOP => 6
        case EQ_BINOP | NE_BINOP                       => 7
        case BIT_AND                                   => 8
        case BIT_XOR                                   => 9
        case BIT_OR                                    => 10
        case COND_AND_BINOP | LOGICAL_AND_BINOP        => 11
        case COND_OR_BINOP | LOGICAL_OR_BINOP          => 12
        case COND_IMPLIED_BINOP | COND_IMPLY_BINOP |
          LOGICAL_IMPLIED_BINOP | LOGICAL_IMPLY_BINOP => 13
      }
    case e : IfExp       => 14
    case e : SwitchExp   => 15
    case e : ExternalExp => 16
  }

  private def existsNe[T <: AnyRef](es : ISeq[(T, T)]) =
    es.exists(p => p._1 ne p._2)

  private def mapR(es : ISeq[Exp]) = es.map(x => (x, rewrite(x)))

  private def tuple(e : Exp) = TupleExp(ivector(e))

  def rewrite(e : Exp) : Exp = {
    Rewriter.build({
      case e : AccessExp =>
        var exp = rewrite(e.exp)
        exp = if (precedence(exp) > precedence(e)) tuple(exp) else exp
        if (e.exp ne exp) AccessExp(exp, e.attributeName) else e
      case e : IndexingExp =>
        var exp = rewrite(e.exp)
        val indices = mapR(e.indices)
        exp = if (precedence(exp) > precedence(e)) tuple(exp) else exp
        if ((e.exp ne exp) || existsNe(indices))
          IndexingExp(exp, indices.map(second2)) else e
      case e : CastExp =>
        var exp = rewrite(e.exp)
        exp = if (precedence(exp) > precedence(e)) tuple(exp) else exp
        if (exp ne e.exp) CastExp(e.typeSpec, exp) else e
      case e : UnaryExp =>
        var exp = rewrite(e.exp)
        exp = if (precedence(exp) > precedence(e)) tuple(exp) else exp
        if (exp ne e.exp) UnaryExp(e.op, exp) else e
      case e : BinaryExp =>
        var left = rewrite(e.left)
        left = if (precedence(left) > precedence(e)) tuple(left) else left
        var right = rewrite(e.right)
        right = if (precedence(right) > precedence(e)) tuple(right) else right
        if ((left ne e.left) || (right ne e.right))
          BinaryExp(e.op, left, right)
        else e
    }, Rewriter.TraversalMode.BOTTOM_UP)(e).asInstanceOf[Exp]
  }
}