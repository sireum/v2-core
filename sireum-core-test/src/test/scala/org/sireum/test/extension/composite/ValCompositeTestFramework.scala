/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.extension.composite

import java.io._
import java.net._

import org.sireum.extension._
import org.sireum.kiasan.extension._
import org.sireum.kiasan.extension.composite._
import org.sireum.kiasan.extension.composite.value._
import org.sireum.kiasan.state._
import org.sireum.konkrit.extension._
import org.sireum.konkrit.extension.composite.value._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.pretty._
import org.sireum.pilar.state._
import org.sireum.topi._
import org.sireum.util._
import org.sireum.util.math._

import org.sireum.test.framework._
import org.sireum.test.framework.kiasan._

import org.sireum.test.kiasan.eval._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ValCompositeTestFramework extends TestFramework {
  type S = KiasanStateWithHeap
  type V = Value
  type Re = (KiasanStateWithHeap, Value)
  type R = ISeq[Re]
  type C = ISeq[(S, Boolean)]
  type Se = S
  type SR = ISeq[S]

  def extensions : ISeq[ExtensionCompanion]
  def forceGenerate : Boolean

  val config = KiasanEvaluatorTestUtil.newConfig[S](None, extensions : _*)

  val heapConfig = {
    import EvaluatorHeapConfig._
    config.heapEvalConfig
  }
  val chid = heapConfig.heapId(KonkritValCompositeExtension.HeapIdKey)
  val bkhid = heapConfig.heapId(KiasanValCompositeExtension.BaseHeapIdKey)
  val khid = heapConfig.heapId(KiasanValCompositeExtension.HeapIdKey)

  val cDefVal = MyKonkritValCompositeExtension.defaultValue[S](chid)

  def vprint(s : S)(v : Value) : String = {
    import KonkritIntegerExtension.CI
    import KonkritBooleanExtension.TT
    import KonkritBooleanExtension.FF
    import KiasanBooleanExtension.KB
    import KiasanIntegerExtension.KI
    v match {
      case TT            => "true"
      case FF            => "false"
      case KB(n)         => "b" + n
      case n : IsInteger => n.asInteger.toBigInt.toString
      case KI(n)         => "i" + n
      case rv @ Heap.RV(hid, aid) =>
        implicit val irv = rv
        if (KiasanValCompositeExtension.Record.is(s)) {
          s"KRecord#$aid"
        } else if (KonkritValCompositeExtension.Record.is(s)) {
          s"CRecord#$aid"
        } else if (KiasanValCompositeExtension.Array.is(s)) {
          s"KArray#$aid"
        } else if (KonkritValCompositeExtension.Array.is(s)) {
          s"CArray#$aid"
        } else {
          rv.toString
        }
    }
  }

  def eprint(vprint : V => String)(e : Exp) : String =
    NodePrettyPrinter.print(e, vprint)

  val state = KiasanStateWithHeap(Vector(Vector(), Vector(), Vector()))

  val fixKiasanArray : RewriteFunction = {
    import KiasanValCompositeExtension.Array._

    {
      case Heap.O(m) if m.contains(SYMBOLIC_ARRAY_KEY) =>
        var ca = m(CONCRETE_ARRAY_KEY).asInstanceOf[ILinkedMap[Integer, Value]]
        var sa = m(SYMBOLIC_ARRAY_KEY).asInstanceOf[ILinkedMap[Value, Value]]
        for ((index, value) <- sa)
          index match {
            case i : IsInteger =>
              ca = ca + (i.asInteger -> value)
              sa = sa - index
            case _ =>
          }
        Heap.O(m + (CONCRETE_ARRAY_KEY -> ca) + (SYMBOLIC_ARRAY_KEY -> sa))
    }
  }

  val topi = Topi.create(TopiSolver.Z3, TopiMode.Process, 10000, extensions : _*)

  def check(s : S) = KiasanStateCheck.check(topi, s)

  def checkConcExe(s : S, s0 : S, action : String,
                   mcs : (IMap[String, Value], S)) {
    KiasanStateCheck.checkConcretizedState(topi, s, s0,
      KonkritBooleanExtension.TT, config.evaluator[S, R, C, SR].mainEvaluator, mcs)
  }

  def checkConcExe(s : S, s0 : S, exp : String, v : V,
                   mcs : (IMap[String, Value], S)) =
    KiasanStateCheck.checkConcExe(topi, s, s0, KonkritBooleanExtension.TT,
      config.evaluator[S, R, C, SR].mainEvaluator, exp, v, identity[Exp], Some(mcs)) match {
        case TopiResult.SAT | TopiResult.UNKNOWN =>
        case _ =>
          assert(false, "Expecting SAT or UNKNOWN")
      }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  implicit class IsMatcher(o : Any) {
    def is(other : Any) {
      import KonkritIntegerExtension.CI
      import KonkritBooleanExtension.TT
      import KonkritBooleanExtension.FF
      import KiasanBooleanExtension.KB
      import KiasanIntegerExtension.KI
      (o, other) match {
        case (TT, b : Boolean) => b should equal(true)
        case (FF, b : Boolean) => b should equal(false)
        case (CI(n), m : Int)  => n should equal(SireumNumber(m))
        case _                 => o should equal(other)
      }
    }

    def is_in(other : Any*) {
      import KonkritIntegerExtension.CI
      import KiasanIntegerExtension.KI
      (o, other) match {
        case (CI(n), t : Traversable[_]) =>
          val col = t.map { e =>
            e match {
              case n : Int => SireumNumber(n)
            }
          }
          col should contain(n)
        case (v @ KI(n), t : Traversable[_]) =>
          val col = t.map { e =>
            e match {
              case ki : KI => ki
            }
          }
          col should contain(v)
      }
    }
  }

  def checkExpected(
    casePrefix : String, title : String, s : S, sr : SR,
    additionalStateText : (S, Option[(IMap[String, Value], S)]) => String) {
    val sw = new StringWriter
    val pw = new PrintWriter(sw)
    val vpr = vprint(s) _
    pw.println(title)
    pw.println
    KiasanStateVisualizer(s, "Before", pw, vpr, eprint(vpr))
    pw.println
    for (post <- sr if post.pathConditions.isEmpty || check(post) != TopiResult.UNSAT) {
      pw.println
      pw.println("----")
      pw.println
      val vp = vprint(post) _
      val swPost = new StringWriter
      KiasanStateVisualizer(post, "After", swPost, vp, eprint(vp))
      var postState = swPost.toString + additionalStateText(post, None)
      if (post.pathConditions.isEmpty) {
        pw.println(postState)
      } else {
        KiasanStateCheck.concretizeState(topi, post,
          None, Some(fixKiasanArray))
        val mcsOpt = KiasanStateCheck.concretizeState(topi, post,
          None, Some(fixKiasanArray))
        val (m, cs) = mcsOpt.get
        val swPostC = new StringWriter
        KiasanStateVisualizer(cs, "After (Semi-Concrete)", swPostC, vp, eprint(vp))
        val postStateC = swPostC + additionalStateText(post, Some(m, cs))
        pw.print(RstUtil.tab(postState, postStateC))
      }
    }
    pw.flush
    val result = sw.toString

    val fname : String = casePrefix.toString + ".rst"
    val fUri = (getClass.getResource("").toURI.toString.
      replace("/bin/", "/src/test/resources/") + "expected/" + fname).
      replace("@", "_").replace(" ", "-").replace("[", "-").replace("]", "-")
    val file = new File(new URI(fUri))
    if (forceGenerate || !file.exists) {
      // write
      val fw = new FileWriter(file)
      try fw.write(result)
      finally fw.close
    } else {
      // compare
      val frUri = (getClass.getResource("").toURI.toString.
        replace("/bin/", "/src/test/resources/") + "result/" + fname).
        replace("@", "_").replace(" ", "-").replace("[", "-").replace("]", "-")
      val fw = new FileWriter(new File(new URI(frUri)))
      try fw.write(result)
      finally fw.close
      val resultLines = StringUtil.readLines(result)
      val (expectedLines, _) = FileUtil.readFileLines(fUri.toString)
      resultLines should equal(expectedLines)
    }
  }
}