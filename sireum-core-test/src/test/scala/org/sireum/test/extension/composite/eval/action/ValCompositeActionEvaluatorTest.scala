/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.extension.composite.eval.action

import org.junit.runner._
import org.scalatest.junit._

import org.sireum.kiasan.extension._
import org.sireum.kiasan.state._
import org.sireum.konkrit.extension._
import org.sireum.pilar.ast._
import org.sireum.util._

import org.sireum.test.extension.composite._
import org.sireum.test.framework.pilar.eval._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class ValCompositeActionEvaluatorTest
    extends ActionEvaluatorTestFramework[KiasanStateWithHeap, KiasanStateWithHeap, ISeq[KiasanStateWithHeap]]
    with ValCompositeTestFramework {

  import language.implicitConversions

  implicit def se2sr(f : Se => Unit) : SR => Unit = { sr : SR =>
    sr.foreach { f(_) }
  }

  implicit def se2s(se : Se) : S = se

  def forceGenerate = false

  def extensions = ivector(
    MyKiasanValCompositeExtension,
    MyKonkritValCompositeExtension,
    KiasanBooleanExtension,
    KiasanIntegerExtension,
    KiasanVariableAccessExtension,
    KonkritBooleanExtension,
    KonkritIntegerExtension)

  def newActionEvaluator(s : S) = config.evaluator.mainEvaluator

  override def postProcess(
    casePrefix : String, s : S, actionS : String, action : Action, r : SR) {
    checkExpected(casePrefix, s"Action: $actionS", s, r, {
      (s, mcsOpt) =>
        mcsOpt match {
          case Some(mcs @ (m, cs)) => checkConcExe(s, cs, actionS, mcs)
          case _                   =>
        }
        ""
    })
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "rec")
      s2("@@x_rec") = rv
    }

    Case("konkrit.x_rec.f1.2").
      Evaluating action "@@x_rec.f1 := 2ii;" on s gives "@@x_rec.f1 == 2"

    Case("konkrit.x_rec.f2.3").
      Evaluating action "@@x_rec.f2 := 3ii;" on s gives "@@x_rec.f2 == 3"
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "recrec")
      s2("@@x_recrec") = rv
    }

    Case("konkrit.x_recrec.f1_rec.x_recrec.f2_rec").
      Evaluating action "@@x_recrec.f1_rec := @@x_recrec.f2_rec;" on s gives
      "@@x_recrec.f1_rec == @@x_recrec.f2_rec"
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "recarr")
      s2("@@x_recarr") = rv
    }

    Case("konkrit.x_recarr.f1_arr.x_recarr.f2_arr").
      Evaluating action "@@x_recarr.f1_arr := @@x_recarr.f2_arr;" on s gives
      "@@x_recarr.f1_arr == @@x_recarr.f2_arr"
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "arr")
      s2("@@x_arr") = rv
    }

    Case("konkrit.x_arr.1.2").
      Evaluating action "@@x_arr[1ii] := 2ii;" on s gives "@@x_arr[1] == 2"

    Case("konkrit.x_arr.2.3").
      Evaluating action "@@x_arr[2ii] := 3ii;" on s gives "@@x_arr[2] == 3"
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "arrrec")
      s2("@@x_arrrec") = rv
    }

    Case("konkrit.x_arrrec.1.x_arrrec.2").
      Evaluating action "@@x_arrrec[1ii] := @@x_arrrec[2ii];" on s gives "@@x_arrrec[1] == @@x_arrrec[2]"
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "arrarr")
      s2("@@x_arrarr") = rv
    }

    Case("konkrit.x_arrarr.1.x_arrarr.2").
      Evaluating action "@@x_arrarr[1ii] := @@x_arrarr[2ii];" on s gives "@@x_arrarr[1] == @@x_arrarr[2]"
  }

  {
    val s = state

    Case("kiasan.x_rec.f1.2").
      Evaluating action "@@x_rec.f1 := 2ii;" on s gives "@@x_rec.f1 == 2"

    Case("kiasan.x_rec.f1.y").
      Evaluating action "@@x_rec.f1 := @@y;" on s gives "@@x_rec.f1 == @@y"

    Case("kiasan.x_recrec.f1_rec.y_rec").
      Evaluating action "@@x_recrec.f1_rec := @@y_rec;" on s gives "@@x_recrec.f1_rec == @@y_rec"

    Case("kiasan.x_recarr.f1_rec.y_arr").
      Evaluating action "@@x_recarr.f1_rec := @@y_arr;" on s gives "@@x_recarr.f1_arr == @@y_arr"

    Case("kiasan.x_arr.1.2").
      Evaluating action "@@x_arr[1ii] := 2ii;" on s gives "@@x_arr[1] == 2"

    Case("kiasan.x_arr.y.2").
      Evaluating action "@@x_arr[@@y] := 2ii;" on s gives "@@x_arr[@@y] == 2"

    Case("kiasan.x_arr.y.x_arr.1").
      Evaluating action "@@x_arr[@@y] := @@x_arr[1ii];" on s gives "@@x_arr[@@y] == @@x_arr[1]"

    Case("kiasan.x_arr.y.x_arr.z").
      Evaluating action "@@x_arr[@@y] := @@x_arr[@@z];" on s gives "@@x_arr[@@y] == @@x_arr[@@z]"
      
    Case("kiasan.x_arr.y.x_arr.1.plus.x_arr.z").
      Evaluating action "@@x_arr[@@y] := @@x_arr[1ii] + @@x_arr[@@z];" on s gives "@@x_arr[@@y] == @@x_arr[@@z]"
      
    Case("kiasan.x_arrrec.1.y_rec").
      Evaluating action "@@x_arrrec[1ii] := @@y_rec;" on s gives "@@x_arr[1] == @@y_rec"
      
    Case("kiasan.x_arrarr.1.y_arr").
      Evaluating action "@@x_arrarr[1ii] := @@y_arr;" on s gives "@@x_arr[1] == @@y_arr"
  }
}
