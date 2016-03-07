/*
Copyright (c) 2014-2015 Fengguo Wei & Sankardas Roy, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/
package org.sireum.option

import org.sireum.util._

/**
 * @author <a href="mailto:fgwei@k-state.edu">Fengguo Wei</a>
 */
@Mode(command = "amandroid",
  desc = "Sireum Amandroid Modules",
  header = """
Sireum Amandroid
(c) 2015-2016, Argus Laboratory - University of South Florida, SAnToS Laboratories - Kansas State University
""")
case class SireumAmandroidMode(
  decompile: SireumAmandroidDecompileMode = SireumAmandroidDecompileMode(),
  cryptoMisuse: SireumAmandroidCryptoMisuseMode = SireumAmandroidCryptoMisuseMode(),
  taintAnalysis: SireumAmandroidTaintAnalysisMode = SireumAmandroidTaintAnalysisMode(),
  concurrent: SireumAmandroidConcurrentMode = SireumAmandroidConcurrentMode(),
  genCallGraph: SireumAmandroidGenGraphMode = SireumAmandroidGenGraphMode())

@Mode(command = "concurrent",
  desc = "Sireum Amandroid Concurrent Modules",
  header = """
Sireum Amandroid Concurrent
(c) 2015-2016, Argus Laboratory - University of South Florida, SAnToS Laboratories - Kansas State University
""")
case class SireumAmandroidConcurrentMode(
  stage: SireumAmandroidStagingMode = SireumAmandroidStagingMode())
  
object AnalysisModule extends Enum {
  sealed abstract class Type extends EnumElem
  object DATA_LEAKAGE extends Type
  object INTENT_INJECTION extends Type
  object PASSWORD_TRACKING extends Type

  def elements = ivector(DATA_LEAKAGE, INTENT_INJECTION, PASSWORD_TRACKING)
}

//object MessageLevel extends Enum {
//  sealed abstract class Type extends EnumElem
//  object NO extends Type
//  object CRITICAL extends Type
//  object INFO extends Type
//  object VERBOSE extends Type
//
//  def elements = ivector(NO, CRITICAL, INFO, VERBOSE)
//}

object GraphFormat extends Enum {
  sealed abstract class Type extends EnumElem
  object GraphML extends Type
  object GML extends Type

  def elements = ivector(GraphML, GML)
}

object GraphType extends Enum {
  sealed abstract class Type extends EnumElem
  object FULL extends Type
  object SIMPLE_CALL extends Type
  object DETAILED_CALL extends Type
  object API extends Type

  def elements = ivector(FULL, SIMPLE_CALL, DETAILED_CALL, API)
}

@Main(value = "decompile",
  className = "org.sireum.amandroid.cli.DecompilerCli",
  featureName = "Sireum Amandroid Cli",
  desc = "Decompile Apk file")
case class SireumAmandroidDecompileMode(
  general: SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
    
  @Arg(index = 0, value = "Source file/dir") var srcFile: String = "",

  @OptionalArg(index = 1, value = "Output dir") var outFile: String = ".")

@Main(value = "cryptoMisuse",
  className = "org.sireum.amandroid.cli.CryptoMisuseCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Detecting crypto API misuse")
case class SireumAmandroidCryptoMisuseMode(

  general: SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
  analysis: SireumAmandroidAnalysisGroup = SireumAmandroidAnalysisGroup(),

  @Arg(index = 0, value = "Source file/dir") var srcFile: String = "")

@Main(value = "taintAnalysis",
  className = "org.sireum.amandroid.cli.TaintAnalyzeCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Perform taint analysis on Apks.")
case class SireumAmandroidTaintAnalysisMode(

  general: SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
  analysis: SireumAmandroidAnalysisGroup = SireumAmandroidAnalysisGroup(),

  @Option(shortKey = "mo", longKey = "module", desc = "Analysis module to use.") var module: AnalysisModule.Type = AnalysisModule.DATA_LEAKAGE,
  @Arg(index = 0, value = "Source file/dir") var srcFile: String = "")

@Main(value = "staging",
  className = "org.sireum.amandroid.cli.concurrent.StagingCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Generate Apk info and points-to information and store it")
case class SireumAmandroidStagingMode(

  general: SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
  analysis: SireumAmandroidAnalysisGroup = SireumAmandroidAnalysisGroup(),

  @Arg(index = 0, value = "Source file/dir") var srcFile: String = "")

@Main(value = "genGraph",
  className = "org.sireum.amandroid.cli.GenGraphCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Generate graph for Apks.")
case class SireumAmandroidGenGraphMode(

  general: SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
  analysis: SireumAmandroidAnalysisGroup = SireumAmandroidAnalysisGroup(),

  @Option(shortKey = "f", longKey = "format", desc = "Graph output format.") var format: GraphFormat.Type = GraphFormat.GraphML,
  @Option(shortKey = "gt", longKey = "graph-type", desc = "Type of the graph.") var graphtyp: GraphType.Type = GraphType.FULL,
  @Option(shortKey = "h", longKey = "header", desc = "Header for nodes and edges.") var header: String = "",
  @Arg(index = 0, value = "Source file/dir") var srcFile: String = "")

@Group("General Options")
case class SireumAmandroidGeneralGroup(
  @Option(shortKey = "m", longKey = "memory", desc = "Max memory (GB).") var mem: Int = 4,

  @Option(shortKey = "d", longKey = "debug", desc = "Output debug information") var debug: Boolean = false)

@Group("Analysis Options")
case class SireumAmandroidAnalysisGroup(
  @Option(shortKey = "to", longKey = "timeout", desc = "Timeout (minute)") var timeout: Int = 10,

  @Option(shortKey = "o", longKey = "outdir", desc = "Output directory path") var outdir: String = ".")
