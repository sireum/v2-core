/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.topi

import org.sireum.pilar.ast._
import org.sireum.util._
import java.io._
import org.sireum.topi.process._
import org.sireum.pilar.state._
import org.sireum.extension._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Topi {
  def create(solver : TopiSolver.Type,
             mode : TopiMode.Type,
             waitTime : Long,
             extensions : ExtensionCompanion*) : Topi = {
    (solver, mode) match {
      case (TopiSolver.Z3, TopiMode.Process) =>
        new Z3Process(TopiSolver.Z3.exeFilePath, waitTime,
          TopiProcess.mine(solver, mode, extensions : _*) : _*)
    }
  }

  case class Boolean(value : scala.Boolean) extends Value with IsBoolean {
    def asBoolean = value
  }

  import org.sireum.util.math._

  case class Integer(value : org.sireum.util.math.Integer) extends Value with IsInteger {
    def asInteger = value
  }

  case class Rational(value : org.sireum.util.math.Rational) extends Value with IsRational {
    def asRational = value
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait TopiState extends Immutable

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Topi {
  def newState : TopiState
  def compile(conjuncts : Iterable[Exp], ts : TopiState = newState) : TopiState
  def check(ts : TopiState) : TopiResult.Type
  def getModel(ts : TopiState) : Option[IMap[String, Value]]

  def check(conjuncts : Iterable[Exp]) : TopiResult.Type
  def getModel(conjuncts : Iterable[Exp]) : Option[IMap[String, Value]]
  def stateRewriter(m : IMap[String, Value]) : RewriteFunction
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object TopiSolver extends Enum {
  sealed abstract class Type extends EnumElem
  object Z3 extends Type {
    def exeFilePath : String = {
      var z3 : Option[String] = None

      val sireumHome = System.getenv("SIREUM_HOME")
      if (sireumHome != null) {
        var z3Path = "apps/z3/bin/z3"
        OsArchUtil.detect match {
          case OsArch.Win32 | OsArch.Win64 => z3Path += ".exe"
          case _                           =>
        }
        val f = new File(sireumHome, z3Path)
        if (f.canExecute)
          z3 = Some(f.getAbsolutePath)
      }

      if (z3.isEmpty) {
        val z3Path = System.getenv("Z3")
        if (z3Path != null)
          z3 = Some(new File(z3Path).getAbsolutePath)
      }

      if (z3.isEmpty)
        z3 = Some("z3")

      z3.get
    }
  }

  def elements = ivector(Z3)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object TopiMode extends Enum {
  sealed abstract class Type extends EnumElem
  object Process extends Type

  def elements = ivector(Process)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object TopiResult extends Enum {
  sealed abstract class Type extends EnumElem
  object SAT extends Type
  object UNSAT extends Type
  object UNKNOWN extends Type
  object TIMEOUT extends Type

  def elements = ivector(SAT, UNSAT, UNKNOWN, TIMEOUT)
}