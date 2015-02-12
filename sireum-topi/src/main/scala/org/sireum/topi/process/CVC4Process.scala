/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.topi.process

import org.sireum.pilar.ast._
import org.sireum.topi._
import java.io._
import org.sireum.util._
import org.sireum.pilar.state._
import org.sireum.util.sexp.ast._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class CVC4Process(cvc4 : String, waitTime : Long, trans : TopiProcess.BackEndPart*) extends Topi {
  val (process, reader, writer) = {
    import java.io._
      def append(s : Seq[String], a : String) = if (waitTime > 0) s :+ a else s
    val args =
      OsArchUtil.detect match {
        case OsArch.Win64 | OsArch.Win32 =>
        //append(ivector(cvc4, "/L", "smt2"), s"/T:$waitTime")
          // Cannot push when not solving incrementally (use --incremental)
          ivector(cvc4, "/i", "/L", "smt2")
        case _ =>
          ivector(cvc4, "-i", "-L", "smt2")
      }
    val processBuilder = new ProcessBuilder(args.asInstanceOf[Seq[String]] : _*)
    processBuilder.redirectErrorStream(true)
    val proc = processBuilder.start()
    val reader = new BufferedReader(new InputStreamReader(proc.getInputStream))
    val writer = new OutputStreamWriter(proc.getOutputStream)
    (proc, reader, writer)
  }

  def check(script : Iterable[String], opt : Option[String]) : ISeq[TopiResult.Type] = {
    var r = ivectorEmpty[TopiResult.Type]
    send(script, opt) { line =>
      line.trim match {
        case "sat"     => r :+= TopiResult.SAT
        case "unsat"   => r :+= TopiResult.UNSAT
        case "unknown" => r :+= TopiResult.UNKNOWN
        case s =>
          scala.Console.err.println(script)
          scala.Console.err.println(s)
          scala.Console.err.flush
          assert(false)
          sys.error("Unexpected result")
      }
    }
    r
  }

  def send(script : Iterable[String], opt : Option[String])(f : String => Unit) {
    writer.write("(set-logic QF_AUFLIA)\n") // most powerful logic: AUFNIRA
    writer.write("(push 1)\n")
    script.foreach { writer.write(_) }
    if (opt.isDefined)
      writer.write(opt.get) // opt is "(check-sat)"
    writer.write("(pop 1)\n") 
    writer.write("(echo \"end\")\n") 
    writer.flush
    
    val sb = new StringBuilder()
    var line : String = null
    while ({ line = reader.readLine; line.trim != "\"end\"" }) {
      f(line)
    }
  }

  override def close() {
    writer.write("(exit)\n")
    writer.flush
    process.waitFor
  }

  def stateRewriter(m : IMap[String, Value]) =
    PartialFunctionUtil.orElses[Any, Any](trans.map { _.stateRewriter(m) })

  val newState = CVC4Process.State()
  val tran = PartialFunctionUtil.orElses[(TopiProcess.TypeCounters, Exp), (TopiProcess.TypeCounters, String)](trans.map { _.expTranslator })

  def check(cconjuncts : Iterable[Iterable[Exp]]) : Iterable[TopiResult.Type] = {
    val s = for (conjuncts <- cconjuncts) yield {
      val sb = new StringBuilder()
      var tc = imapEmpty[ResourceUri, Int]
      sb.append("(push 1)\n")
      for (c <- conjuncts) {
        val (tc2, s) = tran(tc, c)
        sb.append(s)
        tc = tc2
      }
      sb.append("(check-sat)\n(pop 1)\n")
      sb.toString
    }
    if (s.isEmpty) return cconjuncts.map(x => TopiResult.SAT)
    check(s, None)
  }

  def compile(conjuncts : Iterable[Exp], ts : TopiState) : CVC4Process.State =
    ts match {
      case CVC4Process.State(s, m) =>
        assert(s.size <= conjuncts.size)
        var tc = m
        val s2 = for (c <- conjuncts.drop(s.size)) yield {
          assert(tran.isDefinedAt(m, c), c.toString)
          val (tc2, s) = tran(tc, c)
          tc = tc2
          s
        }

        CVC4Process.State(s ++ s2, tc) 
    }

  def check(ts : TopiState) : TopiResult.Type =
    ts match {
      case CVC4Process.State(script, _) =>
        val r = check(script, Some("(check-sat)\n"))
        assert(r.length == 1)
        r(0)
    }

  def check(conjuncts : Iterable[Exp]) : TopiResult.Type =
    check(compile(conjuncts))

  def getModel(ts : TopiState) : Option[IMap[String, Value]] =
    ts match {
      case CVC4Process.State(script, _) =>
        val sb = new StringBuilder
        send(script, Some("(check-sat)\n(get-model)\n")) { line =>
          sb.append(line)
          sb.append('\n')
        }
        Some(CVC4Process.parseModel(sb.toString))
    }

  def getModel(conjuncts : Iterable[Exp]) : Option[IMap[String, Value]] =
    getModel(compile(conjuncts))
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object CVC4Process {
  case class State(cvc4String : ISeq[String] = ilistEmpty,
                   m : TopiProcess.TypeCounters = imapEmpty) extends TopiState

  def parseModel(model : String) : IMap[String, Value] = {
    val m = mmapEmpty[ResourceUri, Value]
    val e = SExprAst.build(model)
    Visitor.build({
      // (define-fun varName () varBasicType varValue)
      case ListSExpr(
        Seq(AtomSExpr("define-fun"), AtomSExpr(varName), ListSExpr(Seq()),
          AtomSExpr(varBasicType), varValue)) =>
        parseValue(varBasicType, varValue) match {
          case Some(v) => m(varName) = v
          case _       =>
        }
        false
    })(e)
    m.toMap
  }

  def parseValue(varBasicType : String, varValue : SExprAstNode) : Option[Value] = {
    import org.sireum.util.math._

    (varBasicType, varValue) match {
      case ("Bool", AtomSExpr(b)) =>
        Some(if (b == "false") Topi.Boolean(false) else Topi.Boolean(true))
      case ("Int", AtomSExpr(i)) =>
        Some(Topi.Integer(SireumNumber(BigInt(i)).pack))
      case ("Int", ListSExpr(Seq(AtomSExpr("-"), AtomSExpr(i)))) =>
        Some(Topi.Integer(SireumNumber(BigInt('-' + i)).pack))
      case ("Real", node : SExprAstNode) =>
        val (isPos, s) =
          node match {
            case AtomSExpr(s)                                 => (true, s)
            case ListSExpr(Seq(AtomSExpr("-"), AtomSExpr(s))) => (false, s)
          }
        val a = s.split('.')
        val c = a(0)
        val n = a(1)
        val d = new StringBuilder("1")
        for (i <- 0 until a(1).length)
          d.append('0')
        Some(Topi.Rational(SireumNumber(isPos, SireumNumber(BigInt(c)).pack,
          SireumNumber(BigInt(n)).pack, SireumNumber(BigInt(d.toString)).pack)))
      case _ =>
        None
    }
  }
}