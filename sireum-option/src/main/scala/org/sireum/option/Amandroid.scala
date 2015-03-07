package org.sireum.option

import org.sireum.util._

/**
 * @author <a href="mailto:fgwei@k-state.edu">Fengguo Wei</a>
 */
@Mode(command = "amandroid",
  desc = "Sireum Amandroid Tools",
  header = """
Sireum Amandroid
(c) 2014-2015, Argus & SAnToS Laboratories, Kansas State University
""")
case class SireumAmandroidMode(
  decompile : SireumAmandroidDecompileMode = SireumAmandroidDecompileMode(),
  passwordTracking : SireumAmandroidPasswordTrackingMode = SireumAmandroidPasswordTrackingMode(),
  intentInjection : SireumAmandroidIntentInjectionMode = SireumAmandroidIntentInjectionMode(),
  cryptoMisuse : SireumAmandroidCryptoMisuseMode = SireumAmandroidCryptoMisuseMode(),
  taintAnalysis : SireumAmandroidTaintAnalysisMode = SireumAmandroidTaintAnalysisMode(),
  staging : SireumAmandroidStagingMode = SireumAmandroidStagingMode(),
  genCallGraph : SireumAmandroidGenCallGraphMode = SireumAmandroidGenCallGraphMode())

object DumpSource extends Enum {
  sealed abstract class Type extends EnumElem
  object APK extends Type
  object DEX extends Type
  object DIR extends Type

  def elements = ivector(APK, DEX, DIR)
}

object MessageLevel extends Enum {
  sealed abstract class Type extends EnumElem
  object NO extends Type
  object CRITICAL extends Type
  object NORMAL extends Type
  object VERBOSE extends Type

  def elements = ivector(NO, CRITICAL, NORMAL, VERBOSE)
}

object GraphFormat extends Enum {
  sealed abstract class Type extends EnumElem
  object DOT extends Type
  object GraphML extends Type
  object GML extends Type
  object TEXT extends Type

  def elements = ivector(DOT, GraphML, GML, TEXT)
}

object GraphType extends Enum {
  sealed abstract class Type extends EnumElem
  object FULL extends Type
  object CALL extends Type
  object API extends Type

  def elements = ivector(FULL, CALL, API)
}

@Main(value = "decompile",
  className = "org.sireum.amandroid.cli.DecompilerCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Dump .dex file and translate it to Pilar format")
case class SireumAmandroidDecompileMode(
  @Option(shortKey = "t", longKey = "type", desc = "The type of the file you want to dump.") var typ : DumpSource.Type = DumpSource.APK,

  @Arg(index = 0, value = "Source file") var srcFile : String = "",

  @OptionalArg(index = 1, value = "Output file") var outFile : String = "")

object AnalyzeSource extends Enum {
  sealed abstract class Type extends EnumElem
  object APK extends Type
  object DIR extends Type

  def elements = ivector(APK, DIR)
}

@Main(value = "passwordTracking",
  className = "org.sireum.amandroid.cli.PasswordTrackingCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Tracking password flow within Android app")
case class SireumAmandroidPasswordTrackingMode(

  general : SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
  analysis : SireumAmandroidAnalysisGroup = SireumAmandroidAnalysisGroup(),

  @Arg(index = 0, value = "Source file") var srcFile : String = "",

  @Arg(index = 1, value = "Sink list file") var sasFile : String = "")

@Main(value = "intentInjection",
  className = "org.sireum.amandroid.cli.IntentInjectionCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Detecting Intent injection problem")
case class SireumAmandroidIntentInjectionMode(

  general : SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
  analysis : SireumAmandroidAnalysisGroup = SireumAmandroidAnalysisGroup(),

  @Arg(index = 0, value = "Source file") var srcFile : String = "",

  @Arg(index = 1, value = "Sink list file") var sasFile : String = "")

@Main(value = "cryptoMisuse",
  className = "org.sireum.amandroid.cli.CryptoMisuseCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Detecting crypto API misuse")
case class SireumAmandroidCryptoMisuseMode(

  general : SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
  analysis : SireumAmandroidAnalysisGroup = SireumAmandroidAnalysisGroup(),

  @Arg(index = 0, value = "Source file") var srcFile : String = "")

@Main(value = "taintAnalysis",
  className = "org.sireum.amandroid.cli.TaintAnalyzeCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Analyze Android apk and output the result")
case class SireumAmandroidTaintAnalysisMode(

  general : SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
  analysis : SireumAmandroidAnalysisGroup = SireumAmandroidAnalysisGroup(),

  @Arg(index = 0, value = "Source file") var srcFile : String = "",

  @Arg(index = 1, value = "Sink list file") var sasFile : String = "")

@Main(value = "staging",
  className = "org.sireum.amandroid.cli.StagingCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Generate IDFG&DDG for Android apk and store it")
case class SireumAmandroidStagingMode(

  general : SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
  analysis : SireumAmandroidAnalysisGroup = SireumAmandroidAnalysisGroup(),

  @Arg(index = 0, value = "Source file") var srcFile : String = "")

@Main(value = "genCallGraph",
  className = "org.sireum.amandroid.cli.GenCallGraphCli",
  featureName = "Sireum Amandroid Cli:Amandroid.sapp",
  desc = "Generate CallGraph for Android apk and store it")
case class SireumAmandroidGenCallGraphMode(

  general : SireumAmandroidGeneralGroup = SireumAmandroidGeneralGroup(),
  analysis : SireumAmandroidAnalysisGroup = SireumAmandroidAnalysisGroup(),

  @Option(shortKey = "f", longKey = "format", desc = "Graph output format.") var format : GraphFormat.Type = GraphFormat.TEXT,
  @Option(shortKey = "gt", longKey = "graph-type", desc = "Type of the graph.") var graphtyp : GraphType.Type = GraphType.FULL,
  @Arg(index = 0, value = "Source file") var srcFile : String = "")

@Group("General Options")
case class SireumAmandroidGeneralGroup(
  @Option(shortKey = "t", longKey = "type", desc = "The type of the file you want to analyze.") var typ : AnalyzeSource.Type = AnalyzeSource.APK,

  @Option(shortKey = "m", longKey = "memory", desc = "Max memory (GB).") var mem : Int = 2,

  @Option(shortKey = "msg", longKey = "message", desc = "Message Level.") var msgLevel : MessageLevel.Type = MessageLevel.NO)

@Group("Analysis Options")
case class SireumAmandroidAnalysisGroup(
  @Option(shortKey = "ns", longKey = "nostatic", desc = "Does not handle static initializer") var noStatic : Boolean = false,

  @Option(shortKey = "p", longKey = "parallel", desc = "Parallel") var parallel : Boolean = false,

  @Option(shortKey = "ni", longKey = "no-icc", desc = "Does not tracking flows via icc") var noicc : Boolean = false,

  @Option(shortKey = "k", longKey = "k-context", desc = "Context length") var k_context : Int = 1,

  @Option(shortKey = "to", longKey = "timeout", desc = "Timeout (minute)") var timeout : Int = 10,

  @Option(shortKey = "o", longKey = "outdir", desc = "Output directory path") var outdir : String = ".")
