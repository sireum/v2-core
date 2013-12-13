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
  analyze : SireumAmandroidAnalyzeMode = SireumAmandroidAnalyzeMode())

object DumpSource extends Enum {
  sealed abstract class Type extends EnumElem
  object APK extends Type
  object DEX extends Type
  object DIR extends Type

  def elements = ivector(APK, DEX, DIR)
}
  
@Main(value = "decompile",
  className = "org.sireum.amandroid.android.decompile.Decompiler",
  featureName = "Sireum Amandroid Alir",
  desc = "Dump .dex file and translate it to Pilar format")
case class SireumAmandroidDecompileMode(
  @Option(shortKey = "t", longKey = "type", desc = "The type of the file you want to dump.")
  var typ : DumpSource.Type = DumpSource.APK,

  @Arg(index = 0, value = "src-file")
  var srcFile : String = "",

  @OptionalArg(index = 1, value = "Output file")
  var outFile : String = "")
 
object AnalyzeSource extends Enum {
  sealed abstract class Type extends EnumElem
  object APK extends Type
  object DIR extends Type

  def elements = ivector(APK, DIR)
}
  
@Main(value = "analyze",
  className = "org.sireum.amandroid.alir.Analyze",
  featureName = "Sireum Amandroid Alir",
  desc = "Analyze Android apk and output the result")
case class SireumAmandroidAnalyzeMode(
  @Option(shortKey = "t", longKey = "type", desc = "The type of the file you want to dump.")
  var typ : AnalyzeSource.Type = AnalyzeSource.APK,

  @Arg(index = 0, value = "src-file")
  var srcFile : String = "",

  @OptionalArg(index = 1, value = "Output Dir")
  var outFile : String = "")
  