/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
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

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Topi {
  def create(config : TopiConfig) : Topi = {
    (config.solver, config.mode) match {
      case (TopiSolver.Z3, TopiMode.Process) =>
        new Z3Process(config.exeFile, config.waitTime, config.backEndParts : _*)
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
trait TopiConfig {
  def solver : TopiSolver.Type
  def mode : TopiMode.Type
  def waitTime : Long
  def exeFile : File
  def backEndParts : ISeq[TopiProcess.BackEndPart]
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
    def exeFile : File = {
      var z3 : Option[File] = None

      val sireumHome = System.getenv("SIREUM_HOME")
      if (sireumHome != null) {
        var z3Path = "apps/z3/bin/z3"
        OsArchUtil.detect match {
          case OsArch.Win32 | OsArch.Win64 => z3Path += ".exe"
          case _                           =>
        }
        val f = new File(sireumHome, z3Path)
        if (f.canExecute)
          z3 = Some(f)
      }

      if (z3.isEmpty) {
        val z3Path = System.getenv("Z3")
        if (z3Path != null)
          z3 = Some(new File(z3Path))
      }

      if (z3.isEmpty)
        z3 = Some(new File("z3"))

      z3.get
    }
  }

  def elements = List(Z3)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object TopiMode extends Enum {
  sealed abstract class Type extends EnumElem
  object Process extends Type

  def elements = List(Process)
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

  def elements = List(SAT, UNSAT, UNKNOWN, TIMEOUT)
}