/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.extension

import org.sireum.extension.annotation._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.symbol._
import org.sireum.util._
import org.sireum.util.math.Integer
import java.lang.reflect.Method

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object BinaryOpMode extends Enum {
  sealed abstract class Type extends EnumElem
  object REGULAR extends Type
  object LAZY_LEFT extends Type
  object LAZY_RIGHT extends Type

  def elements = List(REGULAR, LAZY_LEFT, LAZY_RIGHT)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SemanticsExtensionConsumer[S, V, R, C, SR] {
  def adapter[T <: SemanticsExtensionConsumer[S, V, R, C, SR]] : T = {
    require(isInstanceOf[T])
    asInstanceOf[T]
  }

  def isNativeIndexValue(v : V) : Boolean = toNativeIndex.isDefinedAt(v)
  def toNativeIndex : V --> Integer

  def defaultValue : (S, ResourceUri) --> R
  def trueLiteral : S --> R
  def falseLiteral : S --> R
  def intLiteral : (S, Int) --> R
  def longLiteral : (S, Long) --> R
  def integerLiteral : (S, BigInt) --> R
  def nullLiteral : S --> R
  def cond : (S, V) --> C
  def tupleDecon : (S, V) --> (S, ISeq[V])
  def variable : (S, NameUser) --> R
  def field : (S, V, NameUser) --> R
  def index : (S, V, V) --> R
  def cast : (S, V, ResourceUri) --> R
  def canCast(s : S, v : V, typeUri : ResourceUri) : Boolean =
    cast.isDefinedAt((s, v, typeUri))
  def unaryOp(op : String, s : S, v : V) : R
  def binaryOpMode(op : String) : BinaryOpMode.Type
  def binaryOp(op : String, s : S, v1 : V, v2 : V) : R
  def lazyRBinaryOp(op : String, s : S, v1 : V, fv2 : S => R) : R
  def lazyLBinaryOp(op : String, s : S, fv1 : S => R, v2 : V) : R
  def newList : (S, ISeq[V]) --> R

  def expExtCall(extUri : ResourceUri, args : Product) : R
  def hasExpExt(extUri : ResourceUri) : Boolean

  def variableUpdate : (S, NameUser, V) --> SR
  def fieldUpdate : (S, V, NameUser, V) --> SR
  def fieldUpdateVar : (S, NameUser, NameUser, V) --> SR
  def indexUpdate : (S, V, V, V) --> SR
  def indexUpdateVar : (S, NameUser, V, V) --> SR
  def resolveCall : (CallJump, S, V, V) --> ISeq[(S, ResourceUri)]
  def actionExtCall(extUri : ResourceUri, args : Product) : SR
  def assignOp(op : String, s : S, v1 : V, v2 : V) : SR
  def hasActionExt(extUri : ResourceUri) : Boolean

  def extNumOfArgs(extUri : ResourceUri) : Int
  def extLazyBitMask(extUri : ResourceUri) : IBitSet
  def extVarArgs(extUri : ResourceUri) : Boolean

  def uriValue : (S, ResourceUri) --> R
  def uriExtractor : V --> ResourceUri
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SemanticsExtensionInit[S, V, R, C, SR]
    extends SemanticsExtensionConsumer[S, V, R, C, SR] {
  def addNativeIndexConverter(convF : V --> Integer)
  def addDefaultValue(valueF : (S, ResourceUri) --> R)
  def addTrueLiteral(trueF : S --> R)
  def addFalseLiteral(falseF : S --> R)
  def addCond(condF : (S, V) --> C)
  def addIntLiteral(intF : (S, Int) --> R)
  def addLongLiteral(intF : (S, Long) --> R)
  def addIntegerLiteral(integerF : (S, BigInt) --> R)
  def addNullLiteral(nullF : S --> R)
  def addTupleDecon(tupleDF : (S, V) --> (S, ISeq[V]))
  def addVariableLookup(variableF : (S, NameUser) --> R)
  def addFieldLookup(fieldF : (S, V, NameUser) --> R)
  def addIndexLookup(fieldF : (S, V, V) --> R)
  def addCast(castF : (S, V, ResourceUri) --> R)
  def addUnaryOp(op : String, opF : (S, V) --> R)
  def addUnaryOps(ops : ISeq[String], opF : (S, String, V) --> R)
  def addBinaryOp(op : String, opF : (S, V, V) --> R)
  def addBinaryOps(ops : ISeq[String], opF : (S, V, String, V) --> R)
  def addLeftLazyBinaryOp(op : String, opF : (S, S => R, V) --> R)
  def addLeftLazyBinaryOps(ops : ISeq[String], opF : (S, S => R, String, V) --> R)
  def addRightLazyBinaryOp(op : String, opF : (S, V, S => R) --> R)
  def addRightLazyBinaryOps(ops : ISeq[String], opF : (S, V, String, S => R) --> R)
  def addNewList(newListF : (S, ISeq[V]) --> R)
  def addExpExt(extUri : ResourceUri, extF : Any --> R)

  def addVariableUpdate(variableF : (S, NameUser, V) --> SR)
  def addFieldUpdate(fieldF : (S, V, NameUser, V) --> SR)
  def addFieldUpdateVar(fieldF : (S, NameUser, NameUser, V) --> SR)
  def addIndexUpdate(indexF : (S, V, V, V) --> SR)
  def addIndexUpdateVar(indexF : (S, NameUser, V, V) --> SR)
  def addResolveCall(resolveF : (CallJump, S, V, V) --> ISeq[(S, ResourceUri)])
  def addActionExt(extUri : ResourceUri, extF : Any --> SR)
  def addAssignOp(op : String, assignF : (S, V, V) --> SR)

  def addExtInfo(extUri : ResourceUri, numOfArgs : Int, argLazyMask : IBitSet, varargs : Boolean)

  def addUriValue(uri : (S, ResourceUri) --> R, uriExtractor : V --> ResourceUri)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SemanticsExtensionInitImpl[S, V, R, C, SR]
    extends SemanticsExtensionInit[S, V, R, C, SR] {

  protected val _nativeIndexConverterA : MArray[V --> Integer] = marrayEmpty
  val toNativeIndex : V --> Integer = PartialFunctionUtil.orElses(_nativeIndexConverterA)

  def addNativeIndexConverter(convF : V --> Integer) {
    require(!PartialFunctionUtil.empty.equals(convF))
    _nativeIndexConverterA += convF
  }

  protected val _defaultA : MArray[(S, ResourceUri) --> R] = marrayEmpty
  val defaultValue : (S, ResourceUri) --> R = PartialFunctionUtil.orElses(_defaultA)

  def addDefaultValue(valueF : (S, ResourceUri) --> R) {
    require(!PartialFunctionUtil.empty.equals(valueF))
    _defaultA += valueF
  }

  protected val _trueLiteralA : MArray[S --> R] = marrayEmpty
  val trueLiteral : S --> R = PartialFunctionUtil.orElses(_trueLiteralA)

  def addTrueLiteral(trueF : S --> R) {
    require(!PartialFunctionUtil.empty.equals(trueF))
    _trueLiteralA += trueF
  }

  protected val _falseLiteralA : MArray[S --> R] = marrayEmpty
  val falseLiteral : S --> R = PartialFunctionUtil.orElses(_falseLiteralA)

  def addFalseLiteral(falseF : S --> R) {
    require(!PartialFunctionUtil.empty.equals(falseF))
    _falseLiteralA += falseF
  }

  protected val _intLiteralA : MArray[(S, Int) --> R] = marrayEmpty
  val intLiteral : (S, Int) --> R = PartialFunctionUtil.orElses(_intLiteralA)

  def addIntLiteral(intF : (S, Int) --> R) {
    require(!PartialFunctionUtil.empty.equals(intF))
    _intLiteralA += intF
  }

  protected val _longLiteralA : MArray[(S, Long) --> R] = marrayEmpty
  val longLiteral : (S, Long) --> R = PartialFunctionUtil.orElses(_longLiteralA)

  def addLongLiteral(longF : (S, Long) --> R) {
    require(!PartialFunctionUtil.empty.equals(longF))
    _longLiteralA += longF
  }

  protected val _integerLiteralA : MArray[(S, BigInt) --> R] = marrayEmpty
  val integerLiteral : (S, BigInt) --> R = PartialFunctionUtil.orElses(_integerLiteralA)

  def addIntegerLiteral(integerF : (S, BigInt) --> R) {
    require(!PartialFunctionUtil.empty.equals(integerF))
    _integerLiteralA += integerF
  }

  protected val _nullLiteralA : MArray[S --> R] = marrayEmpty
  val nullLiteral : S --> R = PartialFunctionUtil.orElses(_nullLiteralA)

  def addNullLiteral(nullF : S --> R) {
    require(!PartialFunctionUtil.empty.equals(nullF))
    _nullLiteralA += nullF
  }

  protected val _condA : MArray[(S, V) --> C] = marrayEmpty
  val cond : (S, V) --> C = PartialFunctionUtil.orElses(_condA)

  def addCond(condF : (S, V) --> C) {
    require(!PartialFunctionUtil.empty.equals(condF))
    _condA += condF
  }

  protected val _variableLookupA : MArray[(S, NameUser) --> R] = marrayEmpty
  val variable : (S, NameUser) --> R = PartialFunctionUtil.orElses(_variableLookupA)

  def addVariableLookup(variableF : (S, NameUser) --> R) {
    require(!PartialFunctionUtil.empty.equals(variableF))
    _variableLookupA += variableF
  }

  protected val _fieldLookupA : MArray[(S, V, NameUser) --> R] = marrayEmpty
  val field : (S, V, NameUser) --> R = PartialFunctionUtil.orElses(_fieldLookupA)

  def addFieldLookup(fieldF : (S, V, NameUser) --> R) {
    require(!PartialFunctionUtil.empty.equals(fieldF))
    _fieldLookupA += fieldF
  }

  protected val _arrayLookupA : MArray[(S, V, V) --> R] = marrayEmpty
  val index : (S, V, V) --> R = PartialFunctionUtil.orElses(_arrayLookupA)

  def addIndexLookup(indexF : (S, V, V) --> R) {
    require(!PartialFunctionUtil.empty.equals(indexF))
    _arrayLookupA += indexF
  }

  protected val castTA : MArray[(S, V, ResourceUri) --> R] = marrayEmpty
  val cast : (S, V, ResourceUri) --> R = PartialFunctionUtil.orElses(castTA)

  def addCast(castF : (S, V, ResourceUri) --> R) {
    require(!PartialFunctionUtil.empty.equals(castF))
    castTA += castF
  }

  protected val unaryOpsA : MMap[String, MArray[(S, String, V) --> R]] = mmapEmpty.empty
  protected val unaryOps : MMap[String, (S, String, V) --> R] = mmapEmpty.empty

  protected val binaryOpsA : MMap[String, MArray[(S, V, String, V) --> R]] = mmapEmpty.empty
  protected val binaryOps : MMap[String, (S, V, String, V) --> R] = mmapEmpty.empty

  protected val lLazyBinaryOpsA : MMap[String, MArray[(S, S => R, String, V) --> R]] = mmapEmpty.empty
  protected val lLazyBinaryOps : MMap[String, (S, S => R, String, V) --> R] = mmapEmpty.empty

  protected val rLazyBinaryOpsA : MMap[String, MArray[(S, V, String, S => R) --> R]] = mmapEmpty.empty
  protected val rLazyBinaryOps : MMap[String, (S, V, String, S => R) --> R] = mmapEmpty.empty

  def addUnaryOp(op : String, opF : (S, V) --> R) {
    require(!PartialFunctionUtil.empty.equals(opF))
    if (!unaryOpsA.contains(op)) {
      val l = marrayEmpty[(S, String, V) --> R]
      unaryOpsA(op) = l
      unaryOps(op) = PartialFunctionUtil.orElses(l)
    }
    unaryOpsA(op) += {
      case (s, op2, v) if (op == op2 && opF.isDefinedAt(s, v)) => opF(s, v)
    }
  }

  def addUnaryOps(ops : ISeq[String], opF : (S, String, V) --> R) {
    require(!PartialFunctionUtil.empty.equals(opF))
    for (op <- ops) {
      if (!unaryOpsA.contains(op)) {
        val l = marrayEmpty[(S, String, V) --> R]
        unaryOpsA(op) = l
        unaryOps(op) = PartialFunctionUtil.orElses(l)
      }
      unaryOpsA(op) += opF
    }
  }

  def addBinaryOp(op : String, opF : (S, V, V) --> R) {
    require(!lLazyBinaryOps.contains(op) && !rLazyBinaryOps.contains(op))
    require(!PartialFunctionUtil.empty.equals(opF))
    if (!binaryOpsA.contains(op)) {
      val l = marrayEmpty[(S, V, String, V) --> R]
      binaryOpsA(op) = l
      binaryOps(op) = PartialFunctionUtil.orElses(l)
    }
    binaryOpsA(op) += {
      case (s, v1, op2, v2) if (op == op2 && opF.isDefinedAt((s, v1, v2))) =>
        opF(s, v1, v2)
    }
  }

  def addBinaryOps(ops : ISeq[String], opF : (S, V, String, V) --> R) {
    require(!PartialFunctionUtil.empty.equals(opF))
    for (op <- ops) {
      require(!lLazyBinaryOps.contains(op) && !rLazyBinaryOps.contains(op))
      if (!binaryOpsA.contains(op)) {
        val l = marrayEmpty[(S, V, String, V) --> R]
        binaryOpsA(op) = l
        binaryOps(op) = PartialFunctionUtil.orElses(l)
      }
      binaryOpsA(op) += opF
    }
  }

  def addLeftLazyBinaryOp(op : String, opF : (S, S => R, V) --> R) {
    require(!binaryOps.contains(op) && !rLazyBinaryOps.contains(op))
    require(!PartialFunctionUtil.empty.equals(opF))
    if (!lLazyBinaryOpsA.contains(op)) {
      val l = marrayEmpty[(S, S => R, String, V) --> R]
      lLazyBinaryOpsA(op) = l
      lLazyBinaryOps(op) = PartialFunctionUtil.orElses(l)
    }
    lLazyBinaryOpsA(op) += {
      case (s, f, op2, v) if (op == op2 && opF.isDefinedAt((s, f, v))) =>
        opF(s, f, v)
    }
  }

  def addLeftLazyBinaryOps(ops : ISeq[String], opF : (S, S => R, String, V) --> R) {
    require(!PartialFunctionUtil.empty.equals(opF))
    for (op <- ops) {
      require(!binaryOps.contains(op) && !rLazyBinaryOps.contains(op))
      if (!lLazyBinaryOpsA.contains(op)) {
        val l = marrayEmpty[(S, S => R, String, V) --> R]
        lLazyBinaryOpsA(op) = l
        lLazyBinaryOps(op) = PartialFunctionUtil.orElses(l)
      }
      lLazyBinaryOpsA(op) += opF
    }
  }

  def addRightLazyBinaryOp(op : String, opF : (S, V, S => R) --> R) {
    require(!binaryOps.contains(op) && !lLazyBinaryOps.contains(op))
    require(!PartialFunctionUtil.empty.equals(opF))
    if (!rLazyBinaryOpsA.contains(op)) {
      val l = marrayEmpty[(S, V, String, S => R) --> R]
      rLazyBinaryOpsA(op) = l
      rLazyBinaryOps(op) = PartialFunctionUtil.orElses(l)
    }
    rLazyBinaryOpsA(op) += {
      case (s, v, op2, f) if (op == op2 && opF.isDefinedAt((s, v, f))) =>
        opF(s, v, f)
    }
  }

  def addRightLazyBinaryOps(ops : ISeq[String], opF : (S, V, String, S => R) --> R) {
    require(!PartialFunctionUtil.empty.equals(opF))
    for (op <- ops) {
      require(!binaryOps.contains(op) && !lLazyBinaryOps.contains(op))
      if (!rLazyBinaryOpsA.contains(op)) {
        val l = marrayEmpty[(S, V, String, S => R) --> R]
        rLazyBinaryOpsA(op) = l
        rLazyBinaryOps(op) = PartialFunctionUtil.orElses(l)
      }
      rLazyBinaryOpsA(op) += opF
    }
  }

  def unaryOp(op : String, s : S, v : V) : R = unaryOps(op)(s, op, v)

  def binaryOpMode(op : String) : BinaryOpMode.Type =
    if (lLazyBinaryOps.contains(op)) BinaryOpMode.LAZY_LEFT
    else if (rLazyBinaryOps.contains(op)) BinaryOpMode.LAZY_RIGHT
    else BinaryOpMode.REGULAR

  def binaryOp(op : String, s : S, v1 : V, v2 : V) : R =
    binaryOps(op)(s, v1, op, v2)

  def lazyLBinaryOp(op : String, s : S, e : S => R, v : V) : R =
    lLazyBinaryOps(op)(s, e, op, v)

  def lazyRBinaryOp(op : String, s : S, v : V, e : S => R) : R =
    rLazyBinaryOps(op)(s, v, op, e)

  protected val _newListA : MArray[(S, ISeq[V]) --> R] = marrayEmpty
  val newList : (S, ISeq[V]) --> R = PartialFunctionUtil.orElses(_newListA)

  def addNewList(newListF : (S, ISeq[V]) --> R) {
    require(!PartialFunctionUtil.empty.equals(newListF))
    _newListA += newListF
  }

  protected val _expExt : MMap[ResourceUri, Any --> R] = mmapEmpty

  def addExpExt(extUri : ResourceUri, extF : Any --> R) {
    require(!_expExt.contains(extUri))
    _expExt(extUri) = extF
  }

  def hasExpExt(extUri : ResourceUri) : Boolean = _expExt.contains(extUri)

  def expExtCall(extUri : ResourceUri, args : Product) : R =
    args match {
      case Tuple1(s) => _expExt(extUri)(s)
      case _         => _expExt(extUri)(args)
    }

  protected val _variableUpdateA : MArray[(S, NameUser, V) --> SR] = marrayEmpty
  val variableUpdate : (S, NameUser, V) --> SR = PartialFunctionUtil.orElses(_variableUpdateA)

  def addVariableUpdate(variableF : (S, NameUser, V) --> SR) {
    require(!PartialFunctionUtil.empty.equals(variableF))
    _variableUpdateA += variableF
  }

  protected val _fieldUpdateA : MArray[(S, V, NameUser, V) --> SR] = marrayEmpty
  val fieldUpdate : (S, V, NameUser, V) --> SR = PartialFunctionUtil.orElses(_fieldUpdateA)

  def addFieldUpdate(fieldF : (S, V, NameUser, V) --> SR) {
    if (!PartialFunctionUtil.empty.equals(fieldF))
      _fieldUpdateA += fieldF
  }

  protected val _fieldUpdateVarA : MArray[(S, NameUser, NameUser, V) --> SR] = marrayEmpty
  val fieldUpdateVar : (S, NameUser, NameUser, V) --> SR = PartialFunctionUtil.orElses(_fieldUpdateVarA)

  def addFieldUpdateVar(fieldF : (S, NameUser, NameUser, V) --> SR) {
    if (!PartialFunctionUtil.empty.equals(fieldF))
      _fieldUpdateVarA += fieldF
  }

  protected val _indexUpdateA : MArray[(S, V, V, V) --> SR] = marrayEmpty
  val indexUpdate : (S, V, V, V) --> SR = PartialFunctionUtil.orElses(_indexUpdateA)

  def addIndexUpdate(indexF : (S, V, V, V) --> SR) {
    if (!PartialFunctionUtil.empty.equals(indexF))
      _indexUpdateA += indexF
  }

  protected val _indexUpdateVarA : MArray[(S, NameUser, V, V) --> SR] = marrayEmpty
  val indexUpdateVar : (S, NameUser, V, V) --> SR = PartialFunctionUtil.orElses(_indexUpdateVarA)

  def addIndexUpdateVar(indexF : (S, NameUser, V, V) --> SR) {
    if (!PartialFunctionUtil.empty.equals(indexF))
      _indexUpdateVarA += indexF
  }

  protected val _resolveCallA : MArray[(CallJump, S, V, V) --> ISeq[(S, ResourceUri)]] = marrayEmpty
  val resolveCall : (CallJump, S, V, V) --> ISeq[(S, ResourceUri)] = PartialFunctionUtil.orElses(_resolveCallA)

  def addResolveCall(resolveF : (CallJump, S, V, V) --> ISeq[(S, ResourceUri)]) {

    require(!PartialFunctionUtil.empty.equals(resolveF))
    _resolveCallA += resolveF
  }

  protected val _tupleDeconA : MArray[(S, V) --> (S, ISeq[V])] = marrayEmpty
  val tupleDecon : (S, V) --> (S, ISeq[V]) = PartialFunctionUtil.orElses(_tupleDeconA)

  def addTupleDecon(tupleDF : (S, V) --> (S, ISeq[V])) {
    require(!PartialFunctionUtil.empty.equals(tupleDF))
    _tupleDeconA += tupleDF
  }

  protected val _actionExt : MMap[ResourceUri, Any --> SR] = mmapEmpty
  def addActionExt(extUri : ResourceUri, extF : Any --> SR) {
    require(!_actionExt.contains(extUri))
    _actionExt(extUri) = extF
  }

  def actionExtCall(extUri : ResourceUri, args : Product) : SR =
    args match {
      case Tuple1(s) => _actionExt(extUri)(s)
      case _         => _actionExt(extUri)(args)
    }

  def hasActionExt(extUri : ResourceUri) : Boolean = _actionExt.contains(extUri)

  protected val assignOpsA : MMap[String, MArray[(S, V, V) --> SR]] = mmapEmpty.empty
  protected val assignOps : MMap[String, (S, V, V) --> SR] = mmapEmpty.empty

  def addAssignOp(op : String, opF : (S, V, V) --> SR) {
    require(!PartialFunctionUtil.empty.equals(opF))
    if (!assignOpsA.contains(op)) {
      val l = marrayEmpty[(S, V, V) --> SR]
      assignOpsA(op) = l
      assignOps(op) = PartialFunctionUtil.orElses(l)
    }
    assignOpsA(op) += opF
  }

  def assignOp(op : String, s : S, lhs : V, rhs : V) : SR = assignOps(op)(s, lhs, rhs)

  protected val _extInfo : MMap[ResourceUri, (Int, IBitSet, Boolean)] = mmapEmpty

  def addExtInfo(extUri : ResourceUri, numOfArgs : Int, argLazyMask : IBitSet, varargs : Boolean) {
    _extInfo(extUri) = (numOfArgs, argLazyMask, varargs)
  }

  def extNumOfArgs(extUri : ResourceUri) : Int = _extInfo(extUri)._1

  def extLazyBitMask(extUri : ResourceUri) : IBitSet = _extInfo(extUri)._2

  def extVarArgs(extUri : ResourceUri) : Boolean = _extInfo(extUri)._3

  protected val _uriValueA : MArray[(S, ResourceUri) --> R] = marrayEmpty
  val uriValue : (S, ResourceUri) --> R = PartialFunctionUtil.orElses(_uriValueA)

  protected val _uriExtractorA : MArray[V --> ResourceUri] = marrayEmpty
  val uriExtractor : V --> ResourceUri = PartialFunctionUtil.orElses(_uriExtractorA)

  def addUriValue(uriF : (S, ResourceUri) --> R, uriExtractor : V --> ResourceUri) {
    _uriValueA += uriF
    _uriExtractorA += uriExtractor
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SemanticsExtension[S, V, R, C, SR] {
  def apply(tp : TypeProvider, evalF : Evaluator[S, R, C, SR]) : SemanticsExtensionConsumer[S, V, R, C, SR]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object ExtensionMiner {
  def hasTopLevelAnn(m : Method) : Boolean = {
    for (ann <- m.getDeclaredAnnotations)
      ann match {
        case ann : TopLevel => return true
        case _              =>
      }
    false
  }

  def addExtInfo[S, V, R, C, SR](extUri : ResourceUri, m : Method,
                                 sei : SemanticsExtensionInit[S, V, R, C, SR]) {
    import java.lang.reflect._
    m.getGenericReturnType match {
      case pt : ParameterizedType =>
        assert(pt.getRawType == classOf[PartialFunction[_, _]])
        pt.getActualTypeArguments()(0) match {
          case pt : ParameterizedType =>
            assert(pt.getRawType.toString.indexOf("Tuple") >= 0)
            val typeArgs = pt.getActualTypeArguments
            val lazyBitMask = mbitsetEmpty(typeArgs.length)
            var varargs = false
            for (i <- 1 until typeArgs.length)
              typeArgs(i) match {
                case t : ParameterizedType if t.getRawType == classOf[Function1[_, _]] =>
                  lazyBitMask += i - 1
                case t : ParameterizedType if t.getRawType == classOf[ISeq[V]] || t.getRawType == classOf[Seq[V]] =>
                  assert(i == typeArgs.length - 1)
                  varargs = true
                  t.getActualTypeArguments()(0) match {
                    case t : ParameterizedType if t.getRawType == classOf[Function1[_, _]] =>
                      lazyBitMask += i - 1
                    case _ =>
                  }
                case t =>
                  assert(t.isInstanceOf[V])
              }
            sei.addExtInfo(extUri, typeArgs.length - 1, lazyBitMask.toImmutable, varargs)
          case t =>
            sei.addExtInfo(extUri, 0, ibitsetEmpty, false)
        }
    }
  }

  def mine[S, V, R, C, SR](m : Method, ann : java.lang.annotation.Annotation,
                           sei : SemanticsExtensionInit[S, V, R, C, SR],
                           ext : Extension[S, V, R, C, SR]) : Boolean = {
    import org.sireum.pilar.symbol.H._
    val mName = m.getName
    val extUri = Resource.getResourceUri(SCHEME, EXTENSION_ELEM_TYPE, ilist(ext.uriPath, mName))
    ann match {
      case ann : VarLookup =>
        val extF = m.invoke(ext).asInstanceOf[(S, NameUser) --> R]
        sei.addVariableLookup(extF)
      case ann : VarUpdate =>
        val extF = m.invoke(ext).asInstanceOf[(S, NameUser, V) --> SR]
        sei.addVariableUpdate(extF)
      case ann : FieldLookup =>
        val extF = m.invoke(ext).asInstanceOf[(S, V, NameUser) --> R]
        sei.addFieldLookup(extF)
      case ann : FieldUpdate =>
        val extF = m.invoke(ext).asInstanceOf[(S, V, NameUser, V) --> SR]
        sei.addFieldUpdate(extF)
      case ann : FieldUpdateVar =>
        val extF = m.invoke(ext).asInstanceOf[(S, NameUser, NameUser, V) --> SR]
        sei.addFieldUpdateVar(extF)
      case ann : ArrayLookup =>
        val extF = m.invoke(ext).asInstanceOf[(S, V, V) --> R]
        sei.addIndexLookup(extF)
      case ann : ArrayUpdate =>
        val extF = m.invoke(ext).asInstanceOf[(S, V, V, V) --> SR]
        sei.addIndexUpdate(extF)
      case ann : ArrayUpdateVar =>
        val extF = m.invoke(ext).asInstanceOf[(S, NameUser, V, V) --> SR]
        sei.addIndexUpdateVar(extF)
      case ann : Binary =>
        val extF = m.invoke(ext).asInstanceOf[(S, V, V) --> R]
        sei.addBinaryOp(ann.value, extF)
      case ann : Binaries =>
        val extF = m.invoke(ext).asInstanceOf[(S, V, String, V) --> R]
        sei.addBinaryOps(ann.value.toList, extF)
      case ann : RBinary =>
        val extF = m.invoke(ext).asInstanceOf[(S, V, S => R) --> R]
        sei.addRightLazyBinaryOp(ann.value, extF)
      case ann : RBinaries =>
        val extF = m.invoke(ext).asInstanceOf[(S, V, String, S => R) --> R]
        sei.addRightLazyBinaryOps(ann.value.toList, extF)
      case ann : LBinary =>
        val extF = m.invoke(ext).asInstanceOf[(S, S => R, V) --> R]
        sei.addLeftLazyBinaryOp(ann.value, extF)
      case ann : LBinaries =>
        val extF = m.invoke(ext).asInstanceOf[(S, S => R, String, V) --> R]
        sei.addLeftLazyBinaryOps(ann.value.toList, extF)
      case ann : Unaries =>
        val extF = m.invoke(ext).asInstanceOf[(S, String, V) --> R]
        sei.addUnaryOps(ann.value.toList, extF)
      case ann : Unary =>
        val extF = m.invoke(ext).asInstanceOf[(S, V) --> R]
        sei.addUnaryOp(ann.value, extF)
      case ann : Literal =>
        val c = ann.value
        if (c == classOf[Boolean]) {
          val pf = m.invoke(ext).asInstanceOf[S --> R]
          if (ann.isTrue) sei.addTrueLiteral(pf)
          else sei.addFalseLiteral(pf)
        } else if (c == classOf[BigInt])
          sei.addIntegerLiteral(m.invoke(ext).asInstanceOf[(S, BigInt) --> R])
        else if (c == classOf[Int])
          sei.addIntLiteral(m.invoke(ext).asInstanceOf[(S, Int) --> R])
        else if (c == classOf[Long])
          sei.addLongLiteral(m.invoke(ext).asInstanceOf[(S, Long) --> R])
        else if (c == classOf[Object])
          sei.addNullLiteral(m.invoke(ext).asInstanceOf[S --> R])
      case ann : NativeIndex =>
        val extF = m.invoke(ext).asInstanceOf[V --> Integer]
        sei.addNativeIndexConverter(extF)
      case ann : Cond =>
        val extF = m.invoke(ext).asInstanceOf[(S, V) --> C]
        sei.addCond(extF)
      case ann : Cast =>
        val extF = m.invoke(ext).asInstanceOf[(S, V, ResourceUri) --> R]
        sei.addCast(extF)
      case ann : NewList =>
        val extF = m.invoke(ext).asInstanceOf[(S, ISeq[V]) --> R]
        sei.addNewList(extF)
      case ann : DefaultValue =>
        val extF = m.invoke(ext).asInstanceOf[(S, ResourceUri) --> R]
        sei.addDefaultValue(extF)
      case ann : UriValue =>
        val uriFE = m.invoke(ext).asInstanceOf[((S, ResourceUri) --> R, V --> ResourceUri)]
        sei.addUriValue(uriFE._1, uriFE._2)
      case ann : ExpExt =>
        addExtInfo(extUri, m, sei)
        val extF = m.invoke(ext).asInstanceOf[Any --> R]
        sei.addExpExt(extUri, extF)
        if (hasTopLevelAnn(m)) {
          sei.addExpExt(mName, extF)
          addExtInfo(mName, m, sei)
        }
      case ann : ActionExt =>
        addExtInfo(extUri, m, sei)
        val extF = m.invoke(ext).asInstanceOf[Any --> SR]
        sei.addActionExt(extUri, extF)
        if (hasTopLevelAnn(m)) {
          sei.addActionExt(mName, extF)
          addExtInfo(mName, m, sei)
        }
      case ann : Assign =>
        val extF = m.invoke(ext).asInstanceOf[(S, V, V) --> SR]
        sei.addAssignOp(ann.value, extF)
      case ann : ProcExt =>
        val extF = m.invoke(ext).asInstanceOf[(CallJump, S, V, V) --> ISeq[(S, ResourceUri)]]
        sei.addResolveCall(extF)
      case _ =>
        return false
    }
    return true
  }

  type Miner[S, V, R, C, SR] = (Method, java.lang.annotation.Annotation, SemanticsExtensionInit[S, V, R, C, SR], Extension[S, V, R, C, SR]) => Boolean

  def mineExtensions[S, V, R, C, SR](
    sei : SemanticsExtensionInit[S, V, R, C, SR],
    ext : Extension[S, V, R, C, SR],
    miners : Miner[S, V, R, C, SR]*) {
    for (m <- ext.getClass.getDeclaredMethods)
      for (ann <- m.getDeclaredAnnotations) {
        var mined = false
        for (miner <- miners if !mined)
          if (miner(m, ann, sei, ext))
            mined = true
      }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SemanticsExtensionModule[S, V, R, C, SR] extends EvaluatorModule[S, V, R, C, SR] {
  type EC = {
    def create(ec : EvaluatorConfiguration[S, V, R, C, SR]) : Extension[S, V, R, C, SR]
  }

  def sei : SemanticsExtensionInit[S, V, R, C, SR]

  def miners : ISeq[ExtensionMiner.Miner[S, V, R, C, SR]]

  def initialize(ec : EvaluatorConfiguration[S, V, R, C, SR]) {
    ec.semanticsExtension = sei
    for (extC <- ec.extensions) {
      val ext = extC.asInstanceOf[EC].create(ec)
      ExtensionMiner.mineExtensions(sei, ext, miners : _*)
    }
  }
}
