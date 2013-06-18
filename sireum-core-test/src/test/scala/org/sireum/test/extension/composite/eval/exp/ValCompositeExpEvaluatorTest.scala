/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.extension.composite.eval.exp

import org.junit.runner._
import org.scalatest.junit._

import org.sireum.konkrit.extension._
import org.sireum.kiasan.extension._
import org.sireum.kiasan.state._
import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.util._

import org.sireum.test.extension.composite._
import org.sireum.test.framework.pilar.eval._
import org.sireum.test.kiasan.eval._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class ValCompositeExpEvaluatorTest
    extends ExpEvaluatorTestFramework[KiasanStateWithHeap, (KiasanStateWithHeap, Value), ISeq[(KiasanStateWithHeap, Value)]]
    with ValCompositeTestFramework {

  import KiasanEvaluatorTestUtil._

  import language.implicitConversions

  implicit def p2rc(p : Re) : RK[S] = RK(p._1, p._2)

  implicit def rf2erf(f : Re => Unit) : R => Unit = { rs : R =>
    rs.foreach { f(_) }
  }

  def forceGenerate = false

  def extensions = ivector(
    MyKiasanValCompositeExtension,
    MyKonkritValCompositeExtension,
    KiasanBooleanExtension,
    KiasanIntegerExtension,
    KiasanVariableAccessExtension,
    KonkritBooleanExtension,
    KonkritIntegerExtension)

  def newExpEvaluator(s : S) = config.evaluator.mainEvaluator

  override def expRewriter(e : Exp) = exp(e)

  override def postProcess(casePrefix : String, s : S, expS : String, exp : Exp, r : R) {
    val postValueMap = idmapEmpty[S, V] ++ r
    checkExpected(casePrefix, s"Exp: $expS", s, r.map(first2), {
      (s, mcsOpt) =>
        val vp = vprint(s) _
        val pv = postValueMap(s)
        mcsOpt match {
          case Some((m, cs)) =>
            checkConcExe(s, cs, expS, pv, (m, cs))
            val postValue =
              pv match {
                case KiasanIntegerExtension.KI(n)  => m.getOrElse(s"ii!$n", vp(pv))
                case KonkritIntegerExtension.CI(n) => vp(pv)
              }
            s"\n\n* Result: $postValue"
          case None =>
            s"\n\n* Result: ${vp(pv)}"
        }
    })
  }
  
  import State.UriAccess._

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "rec")
      s2("@@x_rec") = rv
    }

    Case("konkrit.x_rec.f1.is.11").
      Evaluating expression "@@x_rec.f1" on s gives "11" satisfying { r : Re =>
        r.value is 11
      }

    Case("konkrit.x_rec.f2.is.12").
      Evaluating expression "@@x_rec.f2" on s gives "12" satisfying { r : Re =>
        r.value is 12
      }
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "recrec")
      s2("@@x_recrec") = rv
    }

    Case("konkrit.x_recrec.f1_rec.is.rec0").
      Evaluating expression "@@x_recrec.f1_rec" on s gives "rec0" satisfying {
        r : Re => r.value is Heap.RV(chid, 0)
      }

    Case("konkrit.x_recrec.f1_rec.f1.is.11").
      Evaluating expression "@@x_recrec.f1_rec.f1" on s gives "11" satisfying {
        r : Re => r.value is 11
      }

    Case("konkrit.x_recrec.f1_rec.f2.is.12").
      Evaluating expression "@@x_recrec.f1_rec.f2" on s gives "12" satisfying {
        r : Re => r.value is 12
      }

    Case("konkrit.x_recrec.f2_rec.is.rec1").
      Evaluating expression "@@x_recrec.f2_rec" on s gives "rec1" satisfying {
        r : Re => r.value is Heap.RV(chid, 1)
      }

    Case("konkrit.x_recrec.f2_rec.f1.is.11").
      Evaluating expression "@@x_recrec.f2_rec.f1" on s gives "21" satisfying {
        r : Re => r.value is 21
      }

    Case("konkrit.x_recrec.f2_rec.f2.is.12").
      Evaluating expression "@@x_recrec.f2_rec.f2" on s gives "22" satisfying {
        r : Re => r.value is 22
      }
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "recarr")
      s2("@@x_recarr") = rv
    }

    Case("konkrit.x_recarr.f1_arr.is.arr0").
      Evaluating expression "@@x_recarr.f1_arr" on s gives "arr0" satisfying {
        r : Re => r.value is Heap.RV(chid, 0)
      }

    Case("konkrit.x_recarr.f1_arr.1.is.11").
      Evaluating expression "@@x_recarr.f1_arr[1ii]" on s gives "11" satisfying {
        r : Re => r.value is 11
      }

    Case("konkrit.x_recarr.f1_arr.2.is.12").
      Evaluating expression "@@x_recarr.f1_arr[2ii]" on s gives "12" satisfying {
        r : Re => r.value is 12
      }

    Case("konkrit.x_recarr.f2_arr.is.arr1").
      Evaluating expression "@@x_recarr.f2_arr" on s gives "arr2" satisfying {
        r : Re => r.value is Heap.RV(chid, 1)
      }

    Case("konkrit.x_recarr.f2_arr.1.is.22").
      Evaluating expression "@@x_recarr.f2_arr[1ii]" on s gives "22" satisfying {
        r : Re => r.value is 22
      }

    Case("konkrit.x_recarr.f2_arr.2.is.23").
      Evaluating expression "@@x_recarr.f2_arr[2ii]" on s gives "23" satisfying {
        r : Re => r.value is 23
      }
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "arr")
      s2("@@x_arr") = rv
    }

    Case("konkrit.x_arr.1.is.11").
      Evaluating expression "@@x_arr[1ii]" on s gives "11" satisfying {
        r : Re => r.value is 11
      }

    Case("konkrit.x_arr.2.is.12").
      Evaluating expression "@@x_arr[2ii]" on s gives "12" satisfying {
        r : Re => r.value is 12
      }

    Case("konkrit.x_arr.3.is.13").
      Evaluating expression "@@x_arr[3ii]" on s gives "13" satisfying {
        r : Re => r.value is 13
      }
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "arrrec")
      s2("@@x_arrrec") = rv
    }

    Case("konkrit.x_arrrec.1.is.rec0").
      Evaluating expression "@@x_arrrec[1ii]" on s gives "rec0" satisfying {
        r : Re => r.value is Heap.RV(chid, 0)
      }

    Case("konkrit.x_arrrec.1.f1.is.11").
      Evaluating expression "@@x_arrrec[1ii].f1" on s gives "11" satisfying {
        r : Re => r.value is 11
      }

    Case("konkrit.x_arrrec.1.f1.is.12").
      Evaluating expression "@@x_arrrec[1ii].f2" on s gives "12" satisfying {
        r : Re => r.value is 12
      }

    Case("konkrit.x_arrrec.2.is.rec0").
      Evaluating expression "@@x_arrrec[2ii]" on s gives "rec1" satisfying {
        r : Re => r.value is Heap.RV(chid, 1)
      }

    Case("konkrit.x_arrrec.2.f1.is.21").
      Evaluating expression "@@x_arrrec[2ii].f1" on s gives "21" satisfying {
        r : Re => r.value is 21
      }

    Case("konkrit.x_arrrec.2.f2.is.22").
      Evaluating expression "@@x_arrrec[2ii].f2" on s gives "22" satisfying {
        r : Re => r.value is 22
      }
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "arrarr")
      s2("@@x_arrarr") = rv
    }

    Case("konkrit.x_arrarr.1.is.arr0").
      Evaluating expression "@@x_arrarr[1ii]" on s gives "arr0" satisfying {
        r : Re => r.value is Heap.RV(chid, 0)
      }

    Case("konkrit.x_arrarr.1.1.is.11").
      Evaluating expression "@@x_arrarr[1ii][1ii]" on s gives "11" satisfying {
        r : Re => r.value is 11
      }

    Case("konkrit.x_arrarr.1.2.is.12").
      Evaluating expression "@@x_arrarr[1ii][2ii]" on s gives "12" satisfying {
        r : Re => r.value is 12
      }

    Case("konkrit.x_arrarr.2.is.arr1").
      Evaluating expression "@@x_arrarr[2ii]" on s gives "arr1" satisfying {
        r : Re => r.value is Heap.RV(chid, 1)
      }

    Case("konkrit.x_arrarr.2.1.is.22").
      Evaluating expression "@@x_arrarr[2ii][1ii]" on s gives "22" satisfying {
        r : Re => r.value is 22
      }

    Case("konkrit.x_arrarr.2.2.is.23").
      Evaluating expression "@@x_arrarr[2ii][2ii]" on s gives "23" satisfying {
        r : Re => r.value is 23
      }
  }

  {
    val s = {
      val s = state
      val (s2, rv) = cDefVal(s, "arr")
      val s3 = (s2("@@x_arr") = rv)
      val (s4, i) = KiasanIntegerExtension.fresh(s3)
      s4("@@y") = i
    }

    Case("konkritkiasan.x_arr.y.is.11.or.12.or.13").
      Evaluating expression "@@x_arr[@@y]" on s gives "11 or 12 or 13" satisfying {
        r : Re => r.value is_in (11, 12, 13)
      }
  }

  val (ki1, ki2, ki3, ki4, ki5, ki6, ki7, ki8) = {
    import KiasanIntegerExtension.KI
    (KI(1), KI(2), KI(3), KI(4), KI(5), KI(6), KI(7), KI(8))
  }

  {
    val s = state

    Case("kiasan.x_rec.f1.is.ki1.lazyempty").
      Evaluating expression "@@x_rec.f1" on s gives "ki1" satisfying {
        r : Re => r.value is ki1
      }
  }

  {
    val s = {
      val s = state
      val s2s = first3(eval(Either3.First("@@x_rec.f1"), s)).map(first2)
      assert(s2s.size == 1)
      s2s(0)
    }

    Case("kiasan.x_rec.f1.is.ki1.exist").
      Evaluating expression "@@x_rec.f1" on s gives "ki1" satisfying {
        r : Re =>
          r.value is ki1
          r.state is s
      }

    Case("kiasan.x_rec.f2.is.ki2.lazy").
      Evaluating expression "@@x_rec.f2" on s gives "ki2" satisfying {
        r : Re => r.value is ki2
      }
  }

  {
    val s = state

    Case("kiasan.x_arr.1.is.ki4.lazyconempty").
      Evaluating expression "@@x_arr[1ii]" on s gives "ki4" satisfying {
        r : Re => r.value is ki4
      }

    Case("kiasan.x_arr.y.is.ki5.lazysymempty").
      Evaluating expression "@@x_arr[@@y]" on s gives "ki5" satisfying {
        r : Re => r.value is ki5
      }
  }

  {
    val s = {
      val s = state
      val s2s = first3(eval(Either3.First("@@x_arr[1ii]"), s)).map(first2)
      assert(s2s.size == 1)
      s2s(0)
    }

    Case("kiasan.x_arr.1.is.ki4.existconcon").
      Evaluating expression "@@x_arr[1ii]" on s gives "ki4" satisfying {
        r : Re =>
          r.value is ki4
          r.state is s
      }

    Case("kiasan.x_arr.2.is.ki5.lazyconcon").
      Evaluating expression "@@x_arr[2ii]" on s gives "ki5" satisfying {
        r : Re =>
          r.value is ki5
      }

    Case("kiasan.x_arr.y.is.ki4.or.ki6.lazysymcon").
      Evaluating expression "@@x_arr[@@y]" on s gives "ki4 or ki6" satisfying {
        r : Re =>
          r.value is_in (ki6, ki4)
      }
  }

  {
    val s = {
      val s = state
      val s2s = first3(eval(Either3.First("@@x_arr[@@y]"), s)).map(first2)
      assert(s2s.size == 1)
      s2s(0)
    }

    Case("kiasan.x_arr.y.is.ki5.existsym").
      Evaluating expression "@@x_arr[@@y]" on s gives "ki5" satisfying {
        r : Re =>
          r.value is ki5
          r.state is s
      }

    Case("kiasan.x_arr.1.is.ki5.or.ki6.lazyconsym").
      Evaluating expression "@@x_arr[1ii]" on s gives "ki5 or ki6" satisfying {
        r : Re =>
          r.value is_in (ki5, ki6)
      }

    Case("kiasan.x_arr.y.is.ki5.or.ki7.lazysymsym").
      Evaluating expression "@@x_arr[@@z]" on s gives "ki5 or ki7" satisfying {
        r : Re =>
          r.value is_in (ki5, ki7)
      }

  }

  {
    val ss = {
      val s = state
      val s2s = first3(eval(Either3.First("@@x_arr[@@y]"), s)).map(first2)
      assert(s2s.size == 1)
      val s2 = s2s(0)
      first3(eval(Either3.First("@@x_arr[1ii]"), s2)).map(first2)
    }

    for (i <- 0 until ss.length) {
      val s = ss(i)

      Case("kiasan.x_arr.y.is.ki5.or.ki6.existsymsymcon." + i).
        Evaluating expression "@@x_arr[@@y]" on s gives "ki5 or ki6" satisfying {
          r : Re =>
            r.value is_in (ki5, ki6)
          // r.state is s // TODO: implemet better rep
        }

      Case("kiasan.x_arr.1.is.ki5.or.ki6.existconsymcon." + i).
        Evaluating expression "@@x_arr[1ii]" on s gives "ki5 or ki6" satisfying {
          r : Re =>
            r.value is_in (ki5, ki6)
            r.state is s
        }

      Case("kiasan.x_arr.2.is.ki5.or.ki6.or.ki7.lazyconsymcon." + i).
        Evaluating expression "@@x_arr[2ii]" on s gives "ki5 or ki6 or ki7" satisfying {
          r : Re =>
            r.value is_in (ki5, ki6, ki7)
        }

      Case("kiasan.x_arr.z.is.ki5.or.ki6.or.ki7.or.ki8.lazysymsymcon." + i).
        Evaluating expression "@@x_arr[@@z]" on s gives "ki5 or ki6 or ki7 or ki8" satisfying {
          r : Re =>
            r.value is_in (ki5, ki6, ki7, ki8)
        }
    }
  }
}
