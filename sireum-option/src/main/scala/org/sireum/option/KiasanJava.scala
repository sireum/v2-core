/*
Copyright (c) 2011-2014 Robby, Kansas State University.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
*/

package org.sireum.option

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "java", header = """
Sireum/Kiasan for Java
(c) 2012-2015, SAnToS Laboratory, Kansas State University
""")
case class KiasanJavaMode(
  check : KiasanJavaCheck = KiasanJavaCheck(),
  report : KiasanJavaReport = KiasanJavaReport())

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "check", className = "UNKNOWN", featureName = "UNKNOWN")
case class KiasanJavaCheck(
  analysis : KiasanJavaCheckAnalysisGroup = KiasanJavaCheckAnalysisGroup(),
  solver : KiasanJavaCheckSmtSolverGroup = KiasanJavaCheckSmtSolverGroup(),

  @Option(shortKey = "z", longKey = "zz", desc = "Hi") var z : String = "",

  @Arg(index = 0, value = "class-name") //
  var packageName : String = "",

  @OptionalArg(index = 1, value = "method-name") //
  var methodName : String = "")

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Group("Analysis Options")
case class KiasanJavaCheckAnalysisGroup(
    @Option(shortKey = "kb", longKey = "ref-bound", desc = "Reference chain bound") //
    var kBound : Int = 3,

    @Option(shortKey = "ab", longKey = "array-bound", desc = "Array element chain bound") //
    var kArrayBound : Int = 3,

    @Option(shortKey = "lb", longKey = "loop-bound", desc = "Loop bound") //
    var loopBound : Int = 10,

    @Option(shortKey = "cb", longKey = "call-bound", desc = "Call chain bound") //
    var callBound : Int = 10,

    @Option(shortKey = "t", longKey = "timeout", desc = "Timeout (minutes)") //
    var timeout : Int = 10) {
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object SmtSolver extends Enum {
  sealed abstract class Type extends EnumElem
  object Z3 extends Type
  object Yices extends Type

  def elements = ivector(Z3, Yices)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Group("SMT Solver Options")
case class KiasanJavaCheckSmtSolverGroup(
  @Option(shortKey = "smt", longKey = "smt-solver", desc = "SMT Solver") //
  var solver : SmtSolver.Type = SmtSolver.Z3,

  @Option(shortKey = "smtt", longKey = "smt-timeout", desc = "Timeout (minutes)") //
  var timeout : Int = 10,

  @Option(shortKey = "native", longKey = "smt-native", desc = "Use native binding") //
  var native : Boolean = false)

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "report")
case class KiasanJavaReport()
