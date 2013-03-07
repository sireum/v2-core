/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.extension.composite

import org.sireum.extension._
import org.sireum.konkrit.extension.composite.value._
import org.sireum.kiasan.state._
import org.sireum.kiasan.extension._
import org.sireum.kiasan.extension.composite.value._
import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.pilar.eval._
import org.sireum.util._
import org.sireum.util.math._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Extensions {

}

object MyKonkritValCompositeExtension extends ExtensionCompanion {
  def create[S <: State[S] with Heap[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new MyKonkritValCompositeExtension(config)

  import org.sireum.konkrit.extension.KonkritIntegerExtension.CI

  import language.implicitConversions
  implicit def int2integer(i : Int) = SireumNumber(i)

  import KonkritValCompositeExtension._

  def defaultValue[S <: Heap[S]](implicit hid : Int) : (S, ResourceUri) --> (S, Value) = {
    case (s, "rec") =>
      Record.create(hid, s, ("f1" -> CI(11)), ("f2" -> CI(12)))
    case (s, "recrec") =>
      val (s2, rv1) = Record.create(hid, s, ("f1" -> CI(11)), ("f2" -> CI(12)))
      val (s3, rv2) = Record.create(hid, s2, ("f1" -> CI(21)), ("f2" -> CI(22)))
      Record.create(hid, s3, ("f1_rec" -> rv1), ("f2_rec" -> rv2))
    case (s, "recarr") =>
      val (s2, rv1) = Array.create(hid, s, 1, CI(11), CI(12))
      val (s3, rv2) = Array.create(hid, s2, 1, CI(22), CI(23))
      Record.create(hid, s3, ("f1_arr" -> rv1), ("f2_arr" -> rv2))
    case (s, "arr") =>
      Array.create(hid, s, 1, CI(11), CI(12), CI(13))
    case (s, "arrrec") =>
      val (s2, rv1) = Record.create(hid, s, ("f1" -> CI(11)), ("f2" -> CI(12)))
      val (s3, rv2) = Record.create(hid, s2, ("f1" -> CI(21)), ("f2" -> CI(22)))
      KonkritValCompositeExtension.Array.create(hid, s3, 1, rv1, rv2)
    case (s, "arrarr") =>
      val (s2, rv1) = Array.create(hid, s, 1, CI(11), CI(12))
      val (s3, rv2) = Array.create(hid, s2, 1, CI(22), CI(23))
      Array.create(hid, s3, 1, rv1, rv2)
  }
}

final class MyKonkritValCompositeExtension[S <: State[S] with Heap[S]](
  val config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KonkritValCompositeExtension[S] {

  import language.implicitConversions
  implicit def t2s[T](t : T) = ivector(t)

  val defValueHelper = MyKonkritValCompositeExtension.defaultValue[S]

  @DefaultValueProvider
  val defValue : (S, ResourceUri) --> ISeq[(S, V)] = {
    case (s, typeUri) if defValueHelper.isDefinedAt(s, typeUri) =>
      defValueHelper(s, typeUri)
  }
}

object MyKiasanValCompositeExtension extends ExtensionCompanion {
  def create[S <: State[S] with KiasanStatePart[S] with Heap[S]](
    config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]) =
    new MyKiasanValCompositeExtension(config)

  import org.sireum.konkrit.extension.KonkritIntegerExtension.CI
  import org.sireum.kiasan.extension.KiasanIntegerExtension.KI

  import language.implicitConversions
  implicit def int2integer(i : Int) = SireumNumber(i)
  implicit def v2e(v : Value) = ValueExp(v)

  import KiasanValCompositeExtension._

  def freshValueArrayHelper[S <: KiasanStatePart[S] with Heap[S]](
    s : S, bhid : Int, elementType : ResourceUri)(implicit hid : Int) = {
    val (s2, minIndex) = KiasanIntegerExtension.fresh(s)
    val (s3, length) = KiasanIntegerExtension.fresh(s2)
    val (s4, maxIndex) = KiasanIntegerExtension.fresh(s3)
    import KiasanState.PathCondition._
    val s5 =
      s4 ++ (
        BinaryExp("<=", minIndex, maxIndex),
        BinaryExp("<=", CI(0), length),
        BinaryExp("==",
          length,
          BinaryExp("+", BinaryExp("-", maxIndex, minIndex), CI(1))))
    Array.create(hid, bhid, s5, IntegerExtension.Type, elementType,
      minIndex, maxIndex, length)
  }

  def freshValue[S <: KiasanStatePart[S] with Heap[S]](
    bhid : Int)(implicit hid : Int) : (S, ResourceUri) --> (S, Value) = {
    case (s, turi) if turi.startsWith("rec") =>
      Record.create(hid, bhid, s)
    case (s, "arr") =>
      freshValueArrayHelper(s, bhid, IntegerExtension.Type)
    case (s, "arrrec") =>
      freshValueArrayHelper(s, bhid, "rec")
    case (s, "arrarr") =>
      freshValueArrayHelper(s, bhid, "arr")
  }
}

final class MyKiasanValCompositeExtension[S <: State[S] with KiasanStatePart[S] with Heap[S]](
  val config : EvaluatorConfiguration[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]])
    extends KiasanValCompositeExtension[S] {

  import language.implicitConversions
  implicit def t2s[T](t : T) = ivector(t)

  val freshValueHelper = MyKiasanValCompositeExtension.freshValue[S](bhid)

  @FreshKiasanValueProvider
  val freshValue : (S, ResourceUri) --> (S, V) = {
    case (s, typeUri) if freshValueHelper.isDefinedAt(s, typeUri) =>
      freshValueHelper(s, typeUri)
  }
} 
