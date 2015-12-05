package org.sireum.cli

import org.sireum.option._
import org.sireum.util._
import java.io.PrintWriter

object SireumCli extends App {

  args.foreach(c => print(c + " "))
  println()

  val opt = new SireumCli().parse(args)

  import java.io._
  opt.printTags(new PrintWriter(new OutputStreamWriter(scala.Console.out)),
      new PrintWriter(new OutputStreamWriter(scala.Console.err)))
}

class SireumCli {
  val result = new CliResult()
  val properties = {
    import java.io._
    import java.util._
    val ps = new Properties
    val f = new File(System.getProperty("SIREUM_HOME"), "sireum.properties")
    if (f.exists) {
      val fr = new FileReader(f)
      try ps.load(fr)
      finally fr.close
    }
    ps
  }

  def parse(args: Seq[String]) : CliResult = {
    parseSireumMode(args, 0)
    return result  
  }

  def parseModeHelper(parentMode : String, modes : Seq[String],
                      args : Seq[String], i : Int)(f : String => Unit) {
    val mode = args(i)
    val modeMatches =
      if (modes.exists(_ == mode)) ivector(mode)
      else modes.filter(_.startsWith(mode))
    if (modeMatches.size == 1) f(modeMatches(0))
    else {
      val lineSep = System.getProperty("line.separator")
      addErrorTag(mode + " is not a mode of " + parentMode)
      if (modeMatches.length > 0) {
        val sb = new StringBuilder("Did you mean one of the following modes?")
        sb.append(lineSep)
        for (mm <- modeMatches) {
          sb.append("  ")
          sb.append(mm)
          sb.append(lineSep)
        }
        addInfoTag(sb.toString)
      }
    }
  }

def parseSireumAmandroidCryptoMisuseMode(args : Seq[String], i : Int) {
  val opt = SireumAmandroidCryptoMisuseMode()
  val keys = List[String]("-h", "--help", "-o", "--outdir", "-to", "--timeout", "-d", "--debug", "-m", "--memory")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.analysis.outdir = {
    val v = properties.getProperty(keyPrefix + "outdir")
    if (v != null) process("outdir", v, keys, opt.analysis.outdir).get.asInstanceOf[String]
    else opt.analysis.outdir
  }

  opt.analysis.timeout = {
    val v = properties.getProperty(keyPrefix + "timeout")
    if (v != null) process("timeout", v, keys, opt.analysis.timeout).get.asInstanceOf[Int]
    else opt.analysis.timeout
  }

  opt.general.debug = {
    val v = properties.getProperty(keyPrefix + "debug")
    if (v != null) process("debug", v, keys, opt.general.debug).get.asInstanceOf[Boolean]
    else opt.general.debug
  }

  opt.general.mem = {
    val v = properties.getProperty(keyPrefix + "memory")
    if (v != null) process("memory", v, keys, opt.general.mem).get.asInstanceOf[Int]
    else opt.general.mem
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum amandroid cryptoMisuse [options] <Source file/dir> 

where the available options are:

-h | --help

Analysis Options
  -o  | --outdir  Output directory path [Default: "${opt.analysis.outdir}"]
  -to | --timeout Timeout (minute) [Default: ${opt.analysis.timeout}]   

General Options
  -d | --debug  Output debug information 
  -m | --memory Max memory (GB). [Default: ${opt.general.mem}]   
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.CryptoMisuseCli"
    result.featureName = "Sireum Amandroid Cli:Amandroid.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-o" | "--outdir" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--outdir"
                  r = r || s == "-o"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--outdir"
                seenopts += "-o"
              }
              val v = process(args(j), args(j + 1), keys, "." )
              if(result.status){
                opt.analysis.outdir  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-to" | "--timeout" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--timeout"
                  r = r || s == "-to"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--timeout"
                seenopts += "-to"
              }
              val v = process(args(j), args(j + 1), keys, 10 )
              if(result.status){
                opt.analysis.timeout  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-d" | "--debug" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--debug"
                  r = r || s == "-d"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--debug"
                seenopts += "-d"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.general.debug  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-m" | "--memory" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--memory"
                  r = r || s == "-m"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--memory"
                seenopts += "-m"
              }
              val v = process(args(j), args(j + 1), keys, 4 )
              if(result.status){
                opt.general.mem  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.srcFile  = v.get.asInstanceOf[java.lang.String]
              }

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 1) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumAmandroidDecompileMode(args : Seq[String], i : Int) {
  val opt = SireumAmandroidDecompileMode()
  val keys = List[String]("-h", "--help", "-d", "--debug", "-m", "--memory")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.general.debug = {
    val v = properties.getProperty(keyPrefix + "debug")
    if (v != null) process("debug", v, keys, opt.general.debug).get.asInstanceOf[Boolean]
    else opt.general.debug
  }

  opt.general.mem = {
    val v = properties.getProperty(keyPrefix + "memory")
    if (v != null) process("memory", v, keys, opt.general.mem).get.asInstanceOf[Int]
    else opt.general.mem
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum amandroid decompile [options] <Source file/dir> [<Output dir>]

where the available options are:

-h | --help

General Options
  -d | --debug  Output debug information 
  -m | --memory Max memory (GB). [Default: ${opt.general.mem}]   
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.DecompilerCli"
    result.featureName = "Sireum Amandroid Cli"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-d" | "--debug" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--debug"
                  r = r || s == "-d"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--debug"
                seenopts += "-d"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.general.debug  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-m" | "--memory" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--memory"
                  r = r || s == "-m"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--memory"
                seenopts += "-m"
              }
              val v = process(args(j), args(j + 1), keys, 4 )
              if(result.status){
                opt.general.mem  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.srcFile  = v.get.asInstanceOf[java.lang.String]
              }
            case 1 => 
              val v = process(args(j), args(j), keys, "." )
              if(result.status){
                opt.outFile  = v.get.asInstanceOf[java.lang.String]
              }

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 1) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumAmandroidGenGraphMode(args : Seq[String], i : Int) {
  val opt = SireumAmandroidGenGraphMode()
  val keys = List[String]("-h", "--help", "-f", "--format", "-gt", "--graph-type", "-h", "--header", "-o", "--outdir", "-to", "--timeout", "-d", "--debug", "-m", "--memory")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.format = {
    val v = properties.getProperty(keyPrefix + "format")
    if (v != null) process("format", v, keys, opt.format).get.asInstanceOf[org.sireum.option.GraphFormat.Type]
    else opt.format
  }

  opt.graphtyp = {
    val v = properties.getProperty(keyPrefix + "graph-type")
    if (v != null) process("graph-type", v, keys, opt.graphtyp).get.asInstanceOf[org.sireum.option.GraphType.Type]
    else opt.graphtyp
  }

  opt.header = {
    val v = properties.getProperty(keyPrefix + "header")
    if (v != null) process("header", v, keys, opt.header).get.asInstanceOf[String]
    else opt.header
  }

  opt.analysis.outdir = {
    val v = properties.getProperty(keyPrefix + "outdir")
    if (v != null) process("outdir", v, keys, opt.analysis.outdir).get.asInstanceOf[String]
    else opt.analysis.outdir
  }

  opt.analysis.timeout = {
    val v = properties.getProperty(keyPrefix + "timeout")
    if (v != null) process("timeout", v, keys, opt.analysis.timeout).get.asInstanceOf[Int]
    else opt.analysis.timeout
  }

  opt.general.debug = {
    val v = properties.getProperty(keyPrefix + "debug")
    if (v != null) process("debug", v, keys, opt.general.debug).get.asInstanceOf[Boolean]
    else opt.general.debug
  }

  opt.general.mem = {
    val v = properties.getProperty(keyPrefix + "memory")
    if (v != null) process("memory", v, keys, opt.general.mem).get.asInstanceOf[Int]
    else opt.general.mem
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum amandroid genGraph [options] <Source file/dir> 

where the available options are:

-h | --help
-f  | --format     Graph output format. [Default: ${opt.format.toString.dropRight(1)}, Choices: (GML,
                   GraphML)]
-gt | --graph-type Type of the graph. [Default: ${opt.graphtyp.toString.dropRight(1)}, Choices: (API,
                   DETAILED_CALL, SIMPLE_CALL, FULL)]
-h  | --header     Header for nodes and edges. [Default: "${opt.header}"]

Analysis Options
  -o  | --outdir  Output directory path [Default: "${opt.analysis.outdir}"]
  -to | --timeout Timeout (minute) [Default: ${opt.analysis.timeout}]   

General Options
  -d | --debug  Output debug information 
  -m | --memory Max memory (GB). [Default: ${opt.general.mem}]   
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.GenGraphCli"
    result.featureName = "Sireum Amandroid Cli:Amandroid.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-f" | "--format" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--format"
                  r = r || s == "-f"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--format"
                seenopts += "-f"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.GraphFormat.GraphML )
              if(result.status){
                opt.format  = v.get.asInstanceOf[org.sireum.option.GraphFormat.Type]
                j += 1
              }
            case "-gt" | "--graph-type" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--graph-type"
                  r = r || s == "-gt"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--graph-type"
                seenopts += "-gt"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.GraphType.FULL )
              if(result.status){
                opt.graphtyp  = v.get.asInstanceOf[org.sireum.option.GraphType.Type]
                j += 1
              }
            case "-h" | "--header" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--header"
                  r = r || s == "-h"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--header"
                seenopts += "-h"
              }
              val v = process(args(j), args(j + 1), keys, "" )
              if(result.status){
                opt.header  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-o" | "--outdir" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--outdir"
                  r = r || s == "-o"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--outdir"
                seenopts += "-o"
              }
              val v = process(args(j), args(j + 1), keys, "." )
              if(result.status){
                opt.analysis.outdir  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-to" | "--timeout" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--timeout"
                  r = r || s == "-to"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--timeout"
                seenopts += "-to"
              }
              val v = process(args(j), args(j + 1), keys, 10 )
              if(result.status){
                opt.analysis.timeout  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-d" | "--debug" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--debug"
                  r = r || s == "-d"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--debug"
                seenopts += "-d"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.general.debug  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-m" | "--memory" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--memory"
                  r = r || s == "-m"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--memory"
                seenopts += "-m"
              }
              val v = process(args(j), args(j + 1), keys, 4 )
              if(result.status){
                opt.general.mem  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.srcFile  = v.get.asInstanceOf[java.lang.String]
              }

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 1) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumAmandroidTaintAnalysisMode(args : Seq[String], i : Int) {
  val opt = SireumAmandroidTaintAnalysisMode()
  val keys = List[String]("-h", "--help", "-mo", "--module", "-o", "--outdir", "-to", "--timeout", "-d", "--debug", "-m", "--memory")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.module = {
    val v = properties.getProperty(keyPrefix + "module")
    if (v != null) process("module", v, keys, opt.module).get.asInstanceOf[org.sireum.option.AnalysisModule.Type]
    else opt.module
  }

  opt.analysis.outdir = {
    val v = properties.getProperty(keyPrefix + "outdir")
    if (v != null) process("outdir", v, keys, opt.analysis.outdir).get.asInstanceOf[String]
    else opt.analysis.outdir
  }

  opt.analysis.timeout = {
    val v = properties.getProperty(keyPrefix + "timeout")
    if (v != null) process("timeout", v, keys, opt.analysis.timeout).get.asInstanceOf[Int]
    else opt.analysis.timeout
  }

  opt.general.debug = {
    val v = properties.getProperty(keyPrefix + "debug")
    if (v != null) process("debug", v, keys, opt.general.debug).get.asInstanceOf[Boolean]
    else opt.general.debug
  }

  opt.general.mem = {
    val v = properties.getProperty(keyPrefix + "memory")
    if (v != null) process("memory", v, keys, opt.general.mem).get.asInstanceOf[Int]
    else opt.general.mem
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum amandroid taintAnalysis [options] <Source file/dir> 

where the available options are:

-h | --help
-mo | --module Analysis module to use. [Default: ${opt.module.toString.dropRight(1)},
               Choices: (PASSWORD_TRACKING, INTENT_INJECTION, DATA_LEAKAGE)]

Analysis Options
  -o  | --outdir  Output directory path [Default: "${opt.analysis.outdir}"]
  -to | --timeout Timeout (minute) [Default: ${opt.analysis.timeout}]   

General Options
  -d | --debug  Output debug information 
  -m | --memory Max memory (GB). [Default: ${opt.general.mem}]   
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.TaintAnalyzeCli"
    result.featureName = "Sireum Amandroid Cli:Amandroid.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-mo" | "--module" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--module"
                  r = r || s == "-mo"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--module"
                seenopts += "-mo"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.AnalysisModule.DATA_LEAKAGE )
              if(result.status){
                opt.module  = v.get.asInstanceOf[org.sireum.option.AnalysisModule.Type]
                j += 1
              }
            case "-o" | "--outdir" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--outdir"
                  r = r || s == "-o"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--outdir"
                seenopts += "-o"
              }
              val v = process(args(j), args(j + 1), keys, "." )
              if(result.status){
                opt.analysis.outdir  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-to" | "--timeout" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--timeout"
                  r = r || s == "-to"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--timeout"
                seenopts += "-to"
              }
              val v = process(args(j), args(j + 1), keys, 10 )
              if(result.status){
                opt.analysis.timeout  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-d" | "--debug" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--debug"
                  r = r || s == "-d"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--debug"
                seenopts += "-d"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.general.debug  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-m" | "--memory" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--memory"
                  r = r || s == "-m"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--memory"
                seenopts += "-m"
              }
              val v = process(args(j), args(j + 1), keys, 4 )
              if(result.status){
                opt.general.mem  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.srcFile  = v.get.asInstanceOf[java.lang.String]
              }

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 1) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumAmandroidMode(args : Seq[String], i : Int) {
  if (i == args.length) {
    addInfoTag(
"""
Sireum Amandroid
(c) 2015-2016, Argus Laboratory - University of South Florida, SAnToS Laboratories - Kansas State University
""".trim
+ "\n\n" + 
"""
Available Modes:
  cryptoMisuse  Detecting crypto API misuse
  decompile     Decompile Apk file
  genGraph      Generate graph for Apks.
  taintAnalysis Perform taint analysis on Apks.
""".trim
)
  } else {
    parseModeHelper("amandroid", Seq("cryptoMisuse", "decompile", "genGraph", "taintAnalysis"), args, i) {
      _ match {
        case "cryptoMisuse" =>
          parseSireumAmandroidCryptoMisuseMode(args, i + 1)
        case "decompile" =>
          parseSireumAmandroidDecompileMode(args, i + 1)
        case "genGraph" =>
          parseSireumAmandroidGenGraphMode(args, i + 1)
        case "taintAnalysis" =>
          parseSireumAmandroidTaintAnalysisMode(args, i + 1)
      }
    }
  }
}  

def parseSireumBakarProgramMode(args : Seq[String], i : Int) {
  val opt = SireumBakarProgramMode()
  val keys = List[String]("-h", "--help", "-g", "--gnatpath", "-p", "--program")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.gnatpath = {
    val v = properties.getProperty(keyPrefix + "gnatpath")
    if (v != null) process("gnatpath", v, keys, opt.gnatpath).get.asInstanceOf[String]
    else opt.gnatpath
  }

  opt.typ = {
    val v = properties.getProperty(keyPrefix + "program")
    if (v != null) process("program", v, keys, opt.typ).get.asInstanceOf[org.sireum.option.ProgramTarget.Type]
    else opt.typ
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum bakar program [options] <src-files> [<Output file>]

where the available options are:

-h | --help
-g | --gnatpath path to bin directory of GNAT [Default: "${opt.gnatpath}"]
-p | --program   [Default: ${opt.typ.toString.dropRight(1)}, Choices: (Java, Ocaml, Coq)]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.bakar.tools.BakarProgram"
    result.featureName = "Sireum Bakar Tools:Gnat.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-g" | "--gnatpath" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--gnatpath"
                  r = r || s == "-g"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--gnatpath"
                seenopts += "-g"
              }
              val v = process(args(j), args(j + 1), keys, "" )
              if(result.status){
                opt.gnatpath  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-p" | "--program" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--program"
                  r = r || s == "-p"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--program"
                seenopts += "-p"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.ProgramTarget.Coq )
              if(result.status){
                opt.typ  = v.get.asInstanceOf[org.sireum.option.ProgramTarget.Type]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, ivectorEmpty[String] )
              if(result.status){
                opt.srcFiles  = v.get.asInstanceOf[ISeq[String]]
              }
            case 1 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.outFile  = v.get.asInstanceOf[java.lang.String]
              }

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 1) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumBakarTypeMode(args : Seq[String], i : Int) {
  val opt = SireumBakarTypeMode()
  val keys = List[String]("-h", "--help", "-t", "--type")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.typ = {
    val v = properties.getProperty(keyPrefix + "type")
    if (v != null) process("type", v, keys, opt.typ).get.asInstanceOf[org.sireum.option.TypeTarget.Type]
    else opt.typ
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum bakar type [options]  [<Output file>]

where the available options are:

-h | --help
-t | --type  [Default: ${opt.typ.toString.dropRight(1)}, Choices: (Ocaml, Coq)]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.bakar.tools.BakarType"
    result.featureName = "Sireum Bakar Tools:Gnat.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-t" | "--type" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--type"
                  r = r || s == "-t"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--type"
                seenopts += "-t"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.TypeTarget.Coq )
              if(result.status){
                opt.typ  = v.get.asInstanceOf[org.sireum.option.TypeTarget.Type]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.outFile  = v.get.asInstanceOf[java.lang.String]
              }

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 0) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumBakarMode(args : Seq[String], i : Int) {
  if (i == args.length) {
    addInfoTag(
"""
Sireum for Spark
(c) 2012-2015, SAnToS Laboratory, Kansas State University
""".trim
+ "\n\n" + 
"""
Available Modes:
  program  Translation of Spark/Ada Programs
  type     Generate Type Definitions
""".trim
)
  } else {
    parseModeHelper("bakar", Seq("program", "type"), args, i) {
      _ match {
        case "program" =>
          parseSireumBakarProgramMode(args, i + 1)
        case "type" =>
          parseSireumBakarTypeMode(args, i + 1)
      }
    }
  }
}  

def parseSireumDistroMode(args : Seq[String], i : Int) {
  if (i == args.length) {
    addInfoTag(
"""
Sireum Distro
""".trim
)
  }}  

def parseLaunchAntlrWorksMode(args : Seq[String], i : Int) {
  val opt = LaunchAntlrWorksMode()
  val keys = List[String]("-h", "--help", "")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")


  def usage {
    addInfoTag(
s"""
Usage:
  sireum launch antlrworks [options]  

where the available options are:

-h | --help
""".trim) 
  }
{
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.AntlrWorksLauncher"
    result.featureName = "Antlr.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 0) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseLaunchBakarV1Mode(args : Seq[String], i : Int) {
  val opt = LaunchBakarV1Mode()
  val keys = List[String]("-h", "--help", "-j", "--jvmopts", "--args")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.jvmopts = {
    val v = properties.getProperty(keyPrefix + "jvmopts")
    if (v != null) process("jvmopts", v, keys, opt.jvmopts).get.asInstanceOf[ISeq[String]]
    else opt.jvmopts
  }

  opt.args = {
    val v = properties.getProperty(keyPrefix + "args")
    if (v != null) process("args", v, keys, opt.args).get.asInstanceOf[ISeq[String]]
    else opt.args
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum launch bakar [options]  

where the available options are:

-h | --help
-j | --jvmopts Options for Java [Separator: ",", Default: "${opt.jvmopts.mkString(",")}"]
--args         Arguments for Eclipse (accepts all following string arguments) 
""".trim) 
  }
{
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.EclipseLauncher"
    result.featureName = "BakarV1.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-j" | "--jvmopts" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--jvmopts"
                  r = r || s == "-j"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--jvmopts"
                seenopts += "-j"
              }
              val v = process(args(j), args(j + 1), keys, ivectorEmpty[String] )
              if(result.status){
                opt.jvmopts  = v.get.asInstanceOf[ISeq[String]]
                j += 1
              }
            case "--args" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--args"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--args"
              }
              opt.args  = 
                if (j + 1 == args.length) List()
                else args.slice(j + 1, args.length).toVector
              j = args.length
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 0) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseLaunchBakarGpsMode(args : Seq[String], i : Int) {
  val opt = LaunchBakarGpsMode()
  val keys = List[String]("-h", "--help", "")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")


  def usage {
    addInfoTag(
s"""
Usage:
  sireum launch bakargps [options]  

where the available options are:

-h | --help
""".trim) 
  }
{
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.GpsLauncher"
    result.featureName = "BakarGps.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 0) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseLaunchBakarV1GpsMode(args : Seq[String], i : Int) {
  val opt = LaunchBakarV1GpsMode()
  val keys = List[String]("-h", "--help", "")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")


  def usage {
    addInfoTag(
s"""
Usage:
  sireum launch bakarv1gps [options]  

where the available options are:

-h | --help
""".trim) 
  }
{
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.GpsLauncher"
    result.featureName = "BakarGpsV1.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 0) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseLaunchEclipseMode(args : Seq[String], i : Int) {
  val opt = LaunchEclipseMode()
  val keys = List[String]("-h", "--help", "-j", "--jvmopts", "--args")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.jvmopts = {
    val v = properties.getProperty(keyPrefix + "jvmopts")
    if (v != null) process("jvmopts", v, keys, opt.jvmopts).get.asInstanceOf[ISeq[String]]
    else opt.jvmopts
  }

  opt.args = {
    val v = properties.getProperty(keyPrefix + "args")
    if (v != null) process("args", v, keys, opt.args).get.asInstanceOf[ISeq[String]]
    else opt.args
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum launch eclipse [options]  

where the available options are:

-h | --help
-j | --jvmopts Options for Java [Separator: ",", Default: "${opt.jvmopts.mkString(",")}"]
--args         Arguments for Eclipse (accepts all following string arguments) 
""".trim) 
  }
{
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.EclipseLauncher"
    result.featureName = "EclipseBase.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-j" | "--jvmopts" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--jvmopts"
                  r = r || s == "-j"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--jvmopts"
                seenopts += "-j"
              }
              val v = process(args(j), args(j + 1), keys, ivectorEmpty[String] )
              if(result.status){
                opt.jvmopts  = v.get.asInstanceOf[ISeq[String]]
                j += 1
              }
            case "--args" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--args"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--args"
              }
              opt.args  = 
                if (j + 1 == args.length) List()
                else args.slice(j + 1, args.length).toVector
              j = args.length
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 0) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseLaunchSireumDevMode(args : Seq[String], i : Int) {
  val opt = LaunchSireumDevMode()
  val keys = List[String]("-h", "--help", "-j", "--jvmopts", "--args")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.jvmopts = {
    val v = properties.getProperty(keyPrefix + "jvmopts")
    if (v != null) process("jvmopts", v, keys, opt.jvmopts).get.asInstanceOf[ISeq[String]]
    else opt.jvmopts
  }

  opt.args = {
    val v = properties.getProperty(keyPrefix + "args")
    if (v != null) process("args", v, keys, opt.args).get.asInstanceOf[ISeq[String]]
    else opt.args
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum launch sireumdev [options]  

where the available options are:

-h | --help
-j | --jvmopts Options for Java [Separator: ",", Default: "${opt.jvmopts.mkString(",")}"]
--args         Arguments for Eclipse (accepts all following string arguments) 
""".trim) 
  }
{
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.EclipseLauncher"
    result.featureName = "SireumDev.sapp"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-j" | "--jvmopts" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--jvmopts"
                  r = r || s == "-j"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--jvmopts"
                seenopts += "-j"
              }
              val v = process(args(j), args(j + 1), keys, ivectorEmpty[String] )
              if(result.status){
                opt.jvmopts  = v.get.asInstanceOf[ISeq[String]]
                j += 1
              }
            case "--args" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--args"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--args"
              }
              opt.args  = 
                if (j + 1 == args.length) List()
                else args.slice(j + 1, args.length).toVector
              j = args.length
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 0) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumLaunchMode(args : Seq[String], i : Int) {
  if (i == args.length) {
    addInfoTag(
"""
Sireum Launcher
""".trim
+ "\n\n" + 
"""
Available Modes:
  antlrworks  Launch ANTLRWorks
  bakar       Launch Eclipse with Bakar Plugins
  bakargps    Launch Gpswith BakarV2 Plugins
  bakarv1gps  Launch Gps with BakarV1 Plugins
  eclipse     Launch Eclipse
  sireumdev   Launch Eclipse with Sireum Dev Plugins
""".trim
)
  } else {
    parseModeHelper("launch", Seq("antlrworks", "bakar", "bakargps", "bakarv1gps", "eclipse", "sireumdev"), args, i) {
      _ match {
        case "antlrworks" =>
          parseLaunchAntlrWorksMode(args, i + 1)
        case "bakar" =>
          parseLaunchBakarV1Mode(args, i + 1)
        case "bakargps" =>
          parseLaunchBakarGpsMode(args, i + 1)
        case "bakarv1gps" =>
          parseLaunchBakarV1GpsMode(args, i + 1)
        case "eclipse" =>
          parseLaunchEclipseMode(args, i + 1)
        case "sireumdev" =>
          parseLaunchSireumDevMode(args, i + 1)
      }
    }
  }
}  

def parseTreeVisitorGenMode(args : Seq[String], i : Int) {
  val opt = TreeVisitorGenMode()
  val keys = List[String]("-h", "--help", "-c", "--class-name", "-d", "--directory", "-p", "--package")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.className = {
    val v = properties.getProperty(keyPrefix + "class-name")
    if (v != null) process("class-name", v, keys, opt.className).get.asInstanceOf[String]
    else opt.className
  }

  opt.dir = {
    val v = properties.getProperty(keyPrefix + "directory")
    if (v != null) process("directory", v, keys, opt.dir).get.asInstanceOf[String]
    else opt.dir
  }

  opt.packageName = {
    val v = properties.getProperty(keyPrefix + "package")
    if (v != null) process("package", v, keys, opt.packageName).get.asInstanceOf[String]
    else opt.packageName
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum tools antlr [options] <token-file> 

where the available options are:

-h | --help
-c | --class-name Name for the generated class [Default: "${opt.className}"]
-d | --directory  Directory for the generated class [Default: "${opt.dir}"]
-p | --package    Package name for the generated class [Default: "${opt.packageName}"]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.tools.antlr.TreeVisitorGen"
    result.featureName = "Sireum Tools"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-c" | "--class-name" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--class-name"
                  r = r || s == "-c"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--class-name"
                seenopts += "-c"
              }
              val v = process(args(j), args(j + 1), keys, "TreeVisitor" )
              if(result.status){
                opt.className  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-d" | "--directory" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--directory"
                  r = r || s == "-d"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--directory"
                seenopts += "-d"
              }
              val v = process(args(j), args(j + 1), keys, "(parent directory of token file)" )
              if(result.status){
                opt.dir  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-p" | "--package" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--package"
                  r = r || s == "-p"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--package"
                seenopts += "-p"
              }
              val v = process(args(j), args(j + 1), keys, "parser" )
              if(result.status){
                opt.packageName  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.tokenFile  = v.get.asInstanceOf[java.lang.String]
              }

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 1) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseCliGenMode(args : Seq[String], i : Int) {
  val opt = CliGenMode()
  val keys = List[String]("-h", "--help", "-c", "--class-name", "-cp", "--classpath", "-d", "--directory", "-p", "--packages", "--min-col", "--max-col")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.genClassName = {
    val v = properties.getProperty(keyPrefix + "class-name")
    if (v != null) process("class-name", v, keys, opt.genClassName).get.asInstanceOf[String]
    else opt.genClassName
  }

  opt.classpath = {
    val v = properties.getProperty(keyPrefix + "classpath")
    if (v != null) process("classpath", v, keys, opt.classpath).get.asInstanceOf[ISeq[String]]
    else opt.classpath
  }

  opt.dir = {
    val v = properties.getProperty(keyPrefix + "directory")
    if (v != null) process("directory", v, keys, opt.dir).get.asInstanceOf[String]
    else opt.dir
  }

  opt.packages = {
    val v = properties.getProperty(keyPrefix + "packages")
    if (v != null) process("packages", v, keys, opt.packages).get.asInstanceOf[ISeq[String]]
    else opt.packages
  }

  opt.minCol = {
    val v = properties.getProperty(keyPrefix + "min-col")
    if (v != null) process("min-col", v, keys, opt.minCol).get.asInstanceOf[Int]
    else opt.minCol
  }

  opt.maxCol = {
    val v = properties.getProperty(keyPrefix + "max-col")
    if (v != null) process("max-col", v, keys, opt.maxCol).get.asInstanceOf[Int]
    else opt.maxCol
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum tools cligen [options] <class-name> 

where the available options are:

-h | --help
-c  | --class-name Fully qualified name for the generated class [Default: "${opt.genClassName}"]
-cp | --classpath  Classpaths containing the className attribute of Main modes [Separator: ",",
                   Default: "${opt.classpath.mkString(",")}"]
-d  | --directory  Directory where generated class should be saved [Default: "${opt.dir}"]
-p  | --packages   Package name prefixes used to filter which classes to process [Separator: ";",
                   Default: "${opt.packages.mkString(";")}"]
--min-col          Column where description should begin [Default: ${opt.minCol}]
--max-col          Maximum number of characters per line [Default: ${opt.maxCol}]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.cli.gen.CliBuilder"
    result.featureName = "Sireum Tools"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-c" | "--class-name" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--class-name"
                  r = r || s == "-c"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--class-name"
                seenopts += "-c"
              }
              val v = process(args(j), args(j + 1), keys, "Cli" )
              if(result.status){
                opt.genClassName  = v.get.asInstanceOf[java.lang.String]
                result.status &= new org.sireum.option.CliGenOption().genClassNameCheck (opt, result.tags)
                j += 1
              }
            case "-cp" | "--classpath" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--classpath"
                  r = r || s == "-cp"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--classpath"
                seenopts += "-cp"
              }
              val v = process(args(j), args(j + 1), keys, ivectorEmpty[String] )
              if(result.status){
                opt.classpath  = v.get.asInstanceOf[ISeq[String]]
                j += 1
              }
            case "-d" | "--directory" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--directory"
                  r = r || s == "-d"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--directory"
                seenopts += "-d"
              }
              val v = process(args(j), args(j + 1), keys, "." )
              if(result.status){
                opt.dir  = v.get.asInstanceOf[java.lang.String]
                result.status &= new org.sireum.option.CliGenOption().dirCheck (opt, result.tags)
                j += 1
              }
            case "-p" | "--packages" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--packages"
                  r = r || s == "-p"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--packages"
                seenopts += "-p"
              }
              val v = process(args(j), args(j + 1), keys, ivectorEmpty[String] )
              if(result.status){
                opt.packages  = v.get.asInstanceOf[ISeq[String]]
                j += 1
              }
            case "--min-col" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--min-col"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--min-col"
              }
              val v = process(args(j), args(j + 1), keys, 20 )
              if(result.status){
                opt.minCol  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "--max-col" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--max-col"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--max-col"
              }
              val v = process(args(j), args(j + 1), keys, 100 )
              if(result.status){
                opt.maxCol  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.className  = v.get.asInstanceOf[java.lang.String]
                result.status &= new org.sireum.option.CliGenOption().classNameCheck (opt, result.tags)
              }

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 1) {
      addErrorTag("Missing required arguments")
    }

    result.status &= new org.sireum.option.CliGenOption().check (opt, result.tags)
  }
}  

def parseJsGenMode(args : Seq[String], i : Int) {
  val opt = JsGenMode()
  val keys = List[String]("-h", "--help", "-c", "--class-name", "-cp", "--classpath", "-d", "--directory", "-p", "--package")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.genObjectName = {
    val v = properties.getProperty(keyPrefix + "class-name")
    if (v != null) process("class-name", v, keys, opt.genObjectName).get.asInstanceOf[String]
    else opt.genObjectName
  }

  opt.classpath = {
    val v = properties.getProperty(keyPrefix + "classpath")
    if (v != null) process("classpath", v, keys, opt.classpath).get.asInstanceOf[ISeq[String]]
    else opt.classpath
  }

  opt.dir = {
    val v = properties.getProperty(keyPrefix + "directory")
    if (v != null) process("directory", v, keys, opt.dir).get.asInstanceOf[String]
    else opt.dir
  }

  opt.packageName = {
    val v = properties.getProperty(keyPrefix + "package")
    if (v != null) process("package", v, keys, opt.packageName).get.asInstanceOf[String]
    else opt.packageName
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum tools jsgen [options] <class-name> 

where the available options are:

-h | --help
-c  | --class-name Fully qualified name for the generated object [Default: "${opt.genObjectName}"]
-cp | --classpath  Classpaths containing the className attribute of Main modes [Separator: ",",
                   Default: "${opt.classpath.mkString(",")}"]
-d  | --directory  Directory where generated class should be saved [Default: "${opt.dir}"]
-p  | --package    The (optional) package the generated code should go in
                   [Default: "${opt.packageName}"]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.js.casegen.JsConvBuilder"
    result.featureName = "Sireum Tools"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-c" | "--class-name" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--class-name"
                  r = r || s == "-c"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--class-name"
                seenopts += "-c"
              }
              val v = process(args(j), args(j + 1), keys, "JsConv" )
              if(result.status){
                opt.genObjectName  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-cp" | "--classpath" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--classpath"
                  r = r || s == "-cp"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--classpath"
                seenopts += "-cp"
              }
              val v = process(args(j), args(j + 1), keys, ivectorEmpty[String] )
              if(result.status){
                opt.classpath  = v.get.asInstanceOf[ISeq[String]]
                j += 1
              }
            case "-d" | "--directory" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--directory"
                  r = r || s == "-d"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--directory"
                seenopts += "-d"
              }
              val v = process(args(j), args(j + 1), keys, "." )
              if(result.status){
                opt.dir  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-p" | "--package" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--package"
                  r = r || s == "-p"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--package"
                seenopts += "-p"
              }
              val v = process(args(j), args(j + 1), keys, "" )
              if(result.status){
                opt.packageName  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, ivectorEmpty[String] )
              if(result.status){
                opt.classNames  = v.get.asInstanceOf[ISeq[String]]
              }

            case _ =>
              addErrorTag("Too many arguments starting at " + args(j))
          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 1) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseJVMMode(args : Seq[String], i : Int) {
  val opt = JVMMode()
  val keys = List[String]("-h", "--help", "-d", "--directory")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.dir = {
    val v = properties.getProperty(keyPrefix + "directory")
    if (v != null) process("directory", v, keys, opt.dir).get.asInstanceOf[String]
    else opt.dir
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum tools jvm [options] <classes> 

where the available options are:

-h | --help
-d | --directory Output Directory [Default: "${opt.dir}"]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.jvm.translator.Translator"
    result.featureName = "Sireum Tools"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-d" | "--directory" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--directory"
                  r = r || s == "-d"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--directory"
                seenopts += "-d"
              }
              val v = process(args(j), args(j + 1), keys, "(current direcory)" )
              if(result.status){
                opt.dir  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case _ => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.classes  :+= v.get.asInstanceOf[java.lang.String]
              }

          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 0) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parsePipelineMode(args : Seq[String], i : Int) {
  val opt = PipelineMode()
  val keys = List[String]("-h", "--help", "-d", "--directory", "-gcn", "--generated-class-name", "-ts", "--type-substitutions")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.dir = {
    val v = properties.getProperty(keyPrefix + "directory")
    if (v != null) process("directory", v, keys, opt.dir).get.asInstanceOf[String]
    else opt.dir
  }

  opt.genClassName = {
    val v = properties.getProperty(keyPrefix + "generated-class-name")
    if (v != null) process("generated-class-name", v, keys, opt.genClassName).get.asInstanceOf[String]
    else opt.genClassName
  }

  opt.typeSubstitutions = {
    val v = properties.getProperty(keyPrefix + "type-substitutions")
    if (v != null) process("type-substitutions", v, keys, opt.typeSubstitutions).get.asInstanceOf[ISeq[String]]
    else opt.typeSubstitutions
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum tools pipeline [options] <class-names> 

where the available options are:

-h | --help
-d   | --directory   Directory where generated class should be saved [Default: "${opt.dir}"]
-gcn | --generated-class-name 
                     Name for the generated class [Default: "${opt.genClassName}"]
-ts  | --type-substitutions 
                     Pairs of fully qualified type names separated by '/' (e.g.
                     java.lang.Boolean/scala.Boolean) [Separator: ",",
                     Default: "${opt.typeSubstitutions.mkString(",")}"]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.pipeline.gen.ModuleGenerator"
    result.featureName = "Sireum Tools"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-d" | "--directory" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--directory"
                  r = r || s == "-d"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--directory"
                seenopts += "-d"
              }
              val v = process(args(j), args(j + 1), keys, "" )
              if(result.status){
                opt.dir  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-gcn" | "--generated-class-name" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--generated-class-name"
                  r = r || s == "-gcn"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--generated-class-name"
                seenopts += "-gcn"
              }
              val v = process(args(j), args(j + 1), keys, "" )
              if(result.status){
                opt.genClassName  = v.get.asInstanceOf[java.lang.String]
                j += 1
              }
            case "-ts" | "--type-substitutions" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--type-substitutions"
                  r = r || s == "-ts"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--type-substitutions"
                seenopts += "-ts"
              }
              val v = process(args(j), args(j + 1), keys, ivectorEmpty[String] )
              if(result.status){
                opt.typeSubstitutions  = v.get.asInstanceOf[ISeq[String]]
                j += 1
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case _ => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.classNames  :+= v.get.asInstanceOf[java.lang.String]
              }

          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 0) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSapperMode(args : Seq[String], i : Int) {
  val opt = SapperMode()
  val keys = List[String]("-h", "--help", "-x", "--extract")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")

  opt.isExtract = {
    val v = properties.getProperty(keyPrefix + "extract")
    if (v != null) process("extract", v, keys, opt.isExtract).get.asInstanceOf[Boolean]
    else opt.isExtract
  }

  def usage {
    addInfoTag(
s"""
Usage:
  sireum tools sapper [options] <file.sapp> <files> 

where the available options are:

-h | --help
-x | --extract Extract Mode 
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.tools.sapp.Sapper"
    result.featureName = "Sireum Tools"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-x" | "--extract" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--extract"
                  r = r || s == "-x"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--extract"
                seenopts += "-x"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.isExtract  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.sappFile  = v.get.asInstanceOf[java.lang.String]
              }
            case _ => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.files  :+= v.get.asInstanceOf[java.lang.String]
              }

          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 1) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseUPicklerMode(args : Seq[String], i : Int) {
  val opt = UPicklerMode()
  val keys = List[String]("-h", "--help", "")
  val keyPrefix = args.slice(0, i).mkString("", ".", ".")


  def usage {
    addInfoTag(
s"""
Usage:
  sireum tools upickler [options] <package-name> <root-class> <import-classes> <leaf-classes> 

where the available options are:

-h | --help
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    result.options = Some(opt)
    result.className = "org.sireum.tools.upickler.UPickler"
    result.featureName = "Sireum Tools"
    var j = i
    var k = -1
    val seenopts = scala.collection.mutable.ListBuffer.empty[String]

    try {
      while (j < args.length) {
        if(!keys.contains(args(j)) && args(j).startsWith("-")) {
          addErrorTag(args(j) + " is not an option")
        }
        if(k == -1 && keys.contains(args(j))){
          if(!keys.contains(args(j)) && args(j).startsWith("-")) {
            addErrorTag(args(j) + " is not an option")
          }
          args(j) match {
            case "-h" | "--help" => usage; result.status = false
            case _ =>
          }
        } else { 
          k = k + 1
          k match {
            case 0 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.packageName  = v.get.asInstanceOf[java.lang.String]
              }
            case 1 => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.rootClass  = v.get.asInstanceOf[java.lang.String]
              }
            case 2 => 
              val v = process(args(j), args(j), keys, ivectorEmpty[String] )
              if(result.status){
                opt.importClasses  = v.get.asInstanceOf[ISeq[String]]
              }
            case _ => 
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.leafClasses  :+= v.get.asInstanceOf[java.lang.String]
              }

          }
        }
        j = j + 1
      }
    } catch {
      case e: Exception => addErrorTag(e.toString)
    }

    if(k+1 < 3) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumToolsMode(args : Seq[String], i : Int) {
  if (i == args.length) {
    addInfoTag(
"""
Sireum Tools
""".trim
+ "\n\n" + 
"""
Available Modes:
  antlr 
  cligen 
  jsgen 
  jvm 
  pipeline 
  sapper 
  upickler 
""".trim
)
  } else {
    parseModeHelper("tools", Seq("antlr", "cligen", "jsgen", "jvm", "pipeline", "sapper", "upickler"), args, i) {
      _ match {
        case "antlr" =>
          parseTreeVisitorGenMode(args, i + 1)
        case "cligen" =>
          parseCliGenMode(args, i + 1)
        case "jsgen" =>
          parseJsGenMode(args, i + 1)
        case "jvm" =>
          parseJVMMode(args, i + 1)
        case "pipeline" =>
          parsePipelineMode(args, i + 1)
        case "sapper" =>
          parseSapperMode(args, i + 1)
        case "upickler" =>
          parseUPicklerMode(args, i + 1)
      }
    }
  }
}  

def parseSireumXMode(args : Seq[String], i : Int) {
  if (i == args.length) {
    addInfoTag(
"""
Sireum X
""".trim
)
  }}  

def parseSireumMode(args : Seq[String], i : Int) {
  if (i == args.length) {
    addInfoTag(
"""
Sireum: A Software Analysis Platform
(c) 2011-2015, SAnToS Laboratory, Kansas State University
""".trim
+ "\n\n" + 
"""
Available Modes:
  amandroid  Sireum Amandroid Modules
  bakar      Sireum Bakar Tools
  distro     Sireum Package Manager
  launch     Sireum Launcher
  tools      Sireum Development Tools
""".trim
)
  } else {
    parseModeHelper("sireum", Seq("amandroid", "bakar", "distro", "launch", "tools", "x"), args, i) {
      _ match {
        case "amandroid" =>
          parseSireumAmandroidMode(args, i + 1)
        case "bakar" =>
          parseSireumBakarMode(args, i + 1)
        case "distro" =>
          parseSireumDistroMode(args, i + 1)
        case "launch" =>
          parseSireumLaunchMode(args, i + 1)
        case "tools" =>
          parseSireumToolsMode(args, i + 1)
        case "x" =>
          parseSireumXMode(args, i + 1)
      }
    }
  }
}  

  def process(key : String, value : String, keys : Seq[String], clazz : Any) : scala.Option[Any] = {
    var messages = Seq[Tag]()
    var v : scala.Option[Any] = None 

    if (keys.contains(value)) {
      addErrorTag("Error while parsing " + key + ". " + value + " is a key ")
    }

    try {
      clazz match {
        case s : Int =>
          v = Some(Int.box(Integer.parseInt(value)))
        case s : String =>
          v = Some(value)
        case s : java.lang.Boolean =>
          v = Some(true)          
        case s : org.sireum.util.Enum#EnumElem =>
          s.elements collectFirst
            { case e if e.toString.stripSuffix("$").equalsIgnoreCase(value) => e } match {
              case Some(valid) =>
                v = Some(valid)
              case _ =>
                addErrorTag(value + " is not a valid choice for " + key + ".  Select one of [" +
                    s.elements.map{s => s.toString.stripSuffix("$")}.mkString(", ") + "]")
            }
        case s : Seq[_] =>
          v = Some(value.split(",").toVector)
        case _ =>
          addErrorTag("Unknown option type " + clazz)
      }
    } catch {
      case e : Throwable =>
        addErrorTag("Wrong argument type supplied " + e.toString())
    }
    return v
  }

  def addErrorTag(desc : String) {
    result.tags += OptionUtil.genTag(OptionUtil.ErrorMarker, desc)
    result.status = false
  }

  def addInfoTag(desc : String) {
    result.tags += OptionUtil.genTag(OptionUtil.InfoMarker, desc)
  }

  def addWarningTag(desc : String) {
    result.tags += OptionUtil.genTag(OptionUtil.WarningMarker, desc)
  }
}

class CliResult {
  var status : Boolean = true
  var className : String = ""
  var featureName : String = ""
  var options : scala.Option[AnyRef] = None
  val tags : MBuffer[Tag] = marrayEmpty[Tag]

  def printTags(out : PrintWriter, err : PrintWriter) {
    for (t @ InfoTag(mt, Some(desc)) <- tags) {
      mt match {
        case OptionUtil.ErrorMarker =>
          err.println(desc)
          err.flush
        case _ =>
          out.println(desc)
          out.flush
      }
    }
  }
}