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
final class Z3Process(z3 : String, waitTime : Long, trans : TopiProcess.BackEndPart*) extends Topi {
  def stateRewriter(m : IMap[String, Value]) =
    PartialFunctionUtil.orElses[Any, Any](trans.map { _.stateRewriter(m) })

  val newState = Z3Process.State()
  val tran = PartialFunctionUtil.orElses[(TopiProcess.TypeCounters, Exp), (TopiProcess.TypeCounters, String)](trans.map { _.expTranslator })

  def check(cconjuncts : Iterable[Iterable[Exp]]) : Iterable[TopiResult.Type] = {
    val sb = new StringBuilder()
    for (conjuncts <- cconjuncts) {
      sb.append("(push)\n")

      var tc = imapEmpty[ResourceUri, Int]
      for (c <- conjuncts) {
        val (tc2, s) = tran(tc, c)
        sb.append(s)
        tc = tc2
      }
      sb.append("(check-sat)\n")
      sb.append("(pop)\n")
    }
    if (sb.length == 0) return cconjuncts.map(x => TopiResult.SAT)
    sb.append("(exit)\n")
    val script = sb.toString
    exec(script) match {
      case Exec.Timeout =>
        ivector(TopiResult.TIMEOUT)
      case Exec.StringResult(s, _) =>
        import java.io._
        val lnr = new LineNumberReader(new StringReader(s))
        var line = lnr.readLine
        var r = ivectorEmpty[TopiResult.Type]
        while (line != null) {
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
          line = lnr.readLine
        }
        r
      case Exec.ExceptionRaised(ex) =>
        throw ex
    }
  }

  def compile(conjuncts : Iterable[Exp], ts : TopiState) : Z3Process.State =
    ts match {
      case Z3Process.State(s, m) =>
        val sb = new StringBuilder(s)

        var tc = m
        for (c <- conjuncts) {
          assert(tran.isDefinedAt(m, c), c.toString)
          val (tc2, s) = tran(tc, c)
          sb.append(s)
          tc = tc2
        }

        Z3Process.State(sb.toString, tc)
    }

  def exec(script : String) = {
    val args =
      OsArchUtil.detect match {
        case OsArch.Win64 | OsArch.Win32 => ivector(z3, "/smt2", "/in")
        case _                           => ivector(z3, "-smt2", "-in")
      }

    val e = new Exec
    e.run(waitTime, args, Some(script))
  }

  def check(ts : TopiState) : TopiResult.Type =
    ts match {
      case Z3Process.State(script, _) =>
        exec(script + "(check-sat)\n(exit)\n") match {
          case Exec.Timeout =>
            TopiResult.TIMEOUT
          case Exec.StringResult(s, _) =>
            s.trim match {
              case "sat"     => TopiResult.SAT
              case "unsat"   => TopiResult.UNSAT
              case "unknown" => TopiResult.UNKNOWN
              case s =>
                scala.Console.err.println(script)
                scala.Console.err.println(s)
                scala.Console.err.flush
                assert(false)
                sys.error("Unexpected result")
            }
          case Exec.ExceptionRaised(ex) =>
            throw ex
        }
    }

  def check(conjuncts : Iterable[Exp]) : TopiResult.Type =
    check(compile(conjuncts))

  def getModel(ts : TopiState) : Option[IMap[String, Value]] =
    ts match {
      case Z3Process.State(script, _) =>
        exec(script + "(check-sat)\n(get-model)\n(exit)\n") match {
          case Exec.Timeout             => None
          case Exec.StringResult(s, _)  => Some(Z3Process.parseModel(s))
          case Exec.ExceptionRaised(ex) => throw ex
        }
    }

  def getModel(conjuncts : Iterable[Exp]) : Option[IMap[String, Value]] =
    getModel(compile(conjuncts))
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Z3Process {
  case class State(z3String : String = "",
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