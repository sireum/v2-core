package org.sireum.option

import org.sireum.util._

/**
 * @author <a href="mailto:fgwei@k-state.edu">Fengguo Wei</a>
 */
@Mode(command = "amandroid",
  desc = "Sireum Amandroid Tools",
  header = """
Sireum for Android
(c) 2012, SAnToS Laboratory, Kansas State University
""")
case class SireumAmandroidMode(
  decompile : SireumAmandroidDecompileMode = SireumAmandroidDecompileMode(),
  taintAnalysis : SireumAmandroidTaintAnalysisMode = SireumAmandroidTaintAnalysisMode())

object DumpSource extends Enum {
  sealed abstract class Type extends EnumElem
  object APK extends Type
  object DEX extends Type
  object DIR extends Type

  def elements = ivector(APK, DEX, DIR)
}
  
@Main(value = "decompile",
  className = "org.sireum.amandroid.android.decompile.DecompilerCli",
  featureName = "Sireum Amandroid Alir",
  desc = "Dump .dex file and translate it to Pilar format")
case class SireumAmandroidDecompileMode(
  @Option(shortKey = "t", longKey = "type", desc = "The type of the file you want to dump.")
  var typ : DumpSource.Type = DumpSource.APK,

  @Arg(index = 0, value = "Source file")
  var srcFile : String = "",

  @OptionalArg(index = 1, value = "Output file")
  var outFile : String = "")
 
object AnalyzeSource extends Enum {
  sealed abstract class Type extends EnumElem
  object APK extends Type
  object DIR extends Type

  def elements = ivector(APK, DIR)
}
  
@Main(value = "taintAnalysis",
  className = "org.sireum.amandroid.alir.TaintAnalyzeCli",
  featureName = "Sireum Amandroid Alir",
  desc = "Analyze Android apk and output the result")
case class SireumAmandroidTaintAnalysisMode(
  @Option(shortKey = "t", longKey = "type", desc = "The type of the file you want to Analyze.")
  var typ : AnalyzeSource.Type = AnalyzeSource.APK,

  @Arg(index = 0, value = "Source file")
  var srcFile : String = "",
  
  @Arg(index = 1, value = "Source&Sink list file")
  var sasFile : String = "",

  @OptionalArg(index = 2, value = "Output Dir")
  var outFile : String = "")
  