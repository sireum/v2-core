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

def parseSireumXMode(args : Seq[String], i : Int) {
  if (i == args.length) {
    addInfoTag(
"""
Sireum X
""".trim
)
  }}  

def parseSireumDistroMode(args : Seq[String], i : Int) {
  if (i == args.length) {
    addInfoTag(
"""
Sireum Distro
""".trim
)
  }}  

def parseLaunchBakarV1Mode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum launch bakar [options]  

where the available options are:

-h | --help
-j | --jvmopts Options for Java [Separator: ",", Default: "-Xms128m,-Xmx1024m"]
--args         Arguments for Eclipse (accepts all following string arguments) 
""".trim) 
  }
{
    val opt = LaunchBakarV1Mode()
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.EclipseLauncher"
    result.featureName = "BakarV1.sapp"
    val keys = List[String]("-h", "--help", "--args", "-j", "--jvmopts")
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
                else args.slice(j + 1, args.length).toList
              j = args.length
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
  def usage {
    addInfoTag(
"""
Usage:
  sireum launch eclipse [options]  

where the available options are:

-h | --help
-j | --jvmopts Options for Java [Separator: ",", Default: "-Xms128m,-Xmx1024m"]
--args         Arguments for Eclipse (accepts all following string arguments) 
""".trim) 
  }
{
    val opt = LaunchEclipseMode()
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.EclipseLauncher"
    result.featureName = "EclipseBase.sapp"
    val keys = List[String]("-h", "--help", "--args", "-j", "--jvmopts")
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
                else args.slice(j + 1, args.length).toList
              j = args.length
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
  def usage {
    addInfoTag(
"""
Usage:
  sireum launch sireumdev [options]  

where the available options are:

-h | --help
-j | --jvmopts Options for Java [Separator: ",", Default: "-Xms128m,-Xmx1024m"]
--args         Arguments for Eclipse (accepts all following string arguments) 
""".trim) 
  }
{
    val opt = LaunchSireumDevMode()
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.EclipseLauncher"
    result.featureName = "SireumDev.sapp"
    val keys = List[String]("-h", "--help", "--args", "-j", "--jvmopts")
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
                else args.slice(j + 1, args.length).toList
              j = args.length
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

def parseLaunchAntlrWorksMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum launch antlrworks [options]  

where the available options are:

-h | --help
""".trim) 
  }
{
    val opt = LaunchAntlrWorksMode()
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.AntlrWorksLauncher"
    result.featureName = "Antlr.sapp"
    val keys = List[String]("-h", "--help", "")
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
  def usage {
    addInfoTag(
"""
Usage:
  sireum launch bakarv1gps [options]  

where the available options are:

-h | --help
""".trim) 
  }
{
    val opt = LaunchBakarV1GpsMode()
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.GpsLauncher"
    result.featureName = "BakarGpsV1.sapp"
    val keys = List[String]("-h", "--help", "")
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

def parseLaunchBakarGpsMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum launch bakargps [options]  

where the available options are:

-h | --help
""".trim) 
  }
{
    val opt = LaunchBakarGpsMode()
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.GpsLauncher"
    result.featureName = "BakarGps.sapp"
    val keys = List[String]("-h", "--help", "")
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

def parseLaunchOsateMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum launch osate [options]  

where the available options are:

-h | --help
-j | --jvmopts Options for Java [Separator: ",", Default: "-Xms128m,-Xmx1024m"]
--args         Arguments for Eclipse (accepts all following string arguments) 
""".trim) 
  }
{
    val opt = LaunchOsateMode()
    result.options = Some(opt)
    result.className = "org.sireum.cli.launcher.OsateLauncher"
    result.featureName = "Osate.sapp"
    val keys = List[String]("-h", "--help", "--args", "-j", "--jvmopts")
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
                else args.slice(j + 1, args.length).toList
              j = args.length
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
  antlrworks Launch ANTLRWorks
  bakar      Launch Eclipse with Bakar Plugins
  bakargps   Launch Gpswith BakarV2 Plugins
  bakarv1gps Launch Gps with BakarV1 Plugins
  eclipse    Launch Eclipse
  osate      Launch Osate with egit plugin
  sireumdev  Launch Eclipse with Sireum Dev Plugins
""".trim
)
  } else {
    parseModeHelper("launch", Seq("bakar", "eclipse", "sireumdev", "antlrworks", "bakarv1gps", "bakargps", "osate"), args, i) {
      _ match {
        case "bakar" =>
          parseLaunchBakarV1Mode(args, i + 1)
        case "eclipse" =>
          parseLaunchEclipseMode(args, i + 1)
        case "sireumdev" =>
          parseLaunchSireumDevMode(args, i + 1)
        case "antlrworks" =>
          parseLaunchAntlrWorksMode(args, i + 1)
        case "bakarv1gps" =>
          parseLaunchBakarV1GpsMode(args, i + 1)
        case "bakargps" =>
          parseLaunchBakarGpsMode(args, i + 1)
        case "osate" =>
          parseLaunchOsateMode(args, i + 1)
      }
    }
  }
}  

def parsePipelineMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum tools pipeline [options] <class-names> 

where the available options are:

-h | --help
-d   | --directory   Directory where generated class should be saved
                     [Default: ""]
-gcn | --generated-class-name 
                     Name for the generated class [Default: ""]
-ts  | --type-substitutions 
                     Pairs of fully qualified type names separated by '/' (e.g.
                     java.lang.Boolean/scala.Boolean) [Separator: ",",
                     Default: ""]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = PipelineMode()
    result.options = Some(opt)
    result.className = "org.sireum.pipeline.gen.ModuleGenerator"
    result.featureName = "Sireum Tools"
    val keys = List[String]("-h", "--help", "-ts", "--type-substitutions", "-gcn", "--generated-class-name", "-d", "--directory")
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

def parseTreeVisitorGenMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum tools antlr [options] <token-file> 

where the available options are:

-h | --help
-c | --class-name Name for the generated class [Default: "TreeVisitor"]
-d | --directory  Directory for the generated class [Default: "(parent directory
                  of token file)"]
-p | --package    Package name for the generated class [Default: "parser"]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = TreeVisitorGenMode()
    result.options = Some(opt)
    result.className = "org.sireum.tools.antlr.TreeVisitorGen"
    result.featureName = "Sireum Tools"
    val keys = List[String]("-h", "--help", "-c", "--class-name", "-p", "--package", "-d", "--directory")
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

def parseSapperMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
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
    val opt = SapperMode()
    result.options = Some(opt)
    result.className = "org.sireum.tools.sapp.Sapper"
    result.featureName = "Sireum Tools"
    val keys = List[String]("-h", "--help", "-x", "--extract")
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
  def usage {
    addInfoTag(
"""
Usage:
  sireum tools upickler [options] <package-name> <root-class> <import-classes> <leaf-classes> 

where the available options are:

-h | --help
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = UPicklerMode()
    result.options = Some(opt)
    result.className = "org.sireum.tools.upickler.UPickler"
    result.featureName = "Sireum Tools"
    val keys = List[String]("-h", "--help", "")
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

def parseJsGenMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum tools jsgen [options] <class-name> 

where the available options are:

-h | --help
-c  | --class-name Fully qualified name for the generated object
                   [Default: "JsConv"]
-cp | --classpath  Classpaths containing the className attribute of Main modes
                   [Separator: ",", Default: ""]
-d  | --directory  Directory where generated class should be saved [Default: "."]
-p  | --package    The (optional) package the generated code should go in
                   [Default: ""]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = JsGenMode()
    result.options = Some(opt)
    result.className = "org.sireum.js.casegen.JsConvBuilder"
    result.featureName = "Sireum Tools"
    val keys = List[String]("-h", "--help", "-c", "--class-name", "-cp", "--classpath", "-p", "--package", "-d", "--directory")
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
  def usage {
    addInfoTag(
"""
Usage:
  sireum tools jvm [options] <classes> 

where the available options are:

-h | --help
-d | --directory Output Directory [Default: "(current direcory)"]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = JVMMode()
    result.options = Some(opt)
    result.className = "org.sireum.jvm.translator.Translator"
    result.featureName = "Sireum Tools"
    val keys = List[String]("-h", "--help", "-d", "--directory")
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

def parseCliGenMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum tools cligen [options] <class-name> 

where the available options are:

-h | --help
-c  | --class-name Fully qualified name for the generated class [Default: "Cli"]
-cp | --classpath  Classpaths containing the className attribute of Main modes
                   [Separator: ",", Default: ""]
-d  | --directory  Directory where generated class should be saved [Default: "."]
-p  | --packages   Package name prefixes used to filter which classes to process
                   [Separator: ";", Default: ""]
--max-col          Maximum number of characters per line [Default: 80]
--min-col          Column where description should begin [Default: 20]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = CliGenMode()
    result.options = Some(opt)
    result.className = "org.sireum.cli.gen.CliBuilder"
    result.featureName = "Sireum Tools"
    val keys = List[String]("-h", "--help", "-p", "--packages", "-cp", "--classpath", "--min-col", "--max-col", "-c", "--class-name", "-d", "--directory")
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
              val v = process(args(j), args(j + 1), keys, 80 )
              if(result.status){
                opt.maxCol  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
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
    parseModeHelper("tools", Seq("pipeline", "antlr", "sapper", "upickler", "jsgen", "jvm", "cligen"), args, i) {
      _ match {
        case "pipeline" =>
          parsePipelineMode(args, i + 1)
        case "antlr" =>
          parseTreeVisitorGenMode(args, i + 1)
        case "sapper" =>
          parseSapperMode(args, i + 1)
        case "upickler" =>
          parseUPicklerMode(args, i + 1)
        case "jsgen" =>
          parseJsGenMode(args, i + 1)
        case "jvm" =>
          parseJVMMode(args, i + 1)
        case "cligen" =>
          parseCliGenMode(args, i + 1)
      }
    }
  }
}  

def parseSireumBakarTypeMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum bakar type [options]  [<Output file>]

where the available options are:

-h | --help
-t | --type  [Default: Coq, Choices: (Ocaml, Coq)]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = SireumBakarTypeMode()
    result.options = Some(opt)
    result.className = "org.sireum.bakar.tools.BakarType"
    result.featureName = "Sireum Bakar Tools:Gnat.sapp"
    val keys = List[String]("-h", "--help", "-t", "--type")
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

def parseSireumBakarProgramMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum bakar program [options] <src-files> [<Output file>]

where the available options are:

-h | --help
-g | --gnatpath path to bin directory of GNAT [Default: ""]
-p | --program   [Default: Coq, Choices: (Java, Ocaml, Coq)]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = SireumBakarProgramMode()
    result.options = Some(opt)
    result.className = "org.sireum.bakar.tools.BakarProgram"
    result.featureName = "Sireum Bakar Tools:Gnat.sapp"
    val keys = List[String]("-h", "--help", "-p", "--program", "-g", "--gnatpath")
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
    parseModeHelper("bakar", Seq("type", "program"), args, i) {
      _ match {
        case "type" =>
          parseSireumBakarTypeMode(args, i + 1)
        case "program" =>
          parseSireumBakarProgramMode(args, i + 1)
      }
    }
  }
}  

def parseSireumAmandroidDecompileMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum amandroid decompile [options] <Source file> [<Output file>]

where the available options are:

-h | --help
-t | --type The type of the file you want to dump. [Default: APK, Choices: (DIR,
            DEX, APK)]
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = SireumAmandroidDecompileMode()
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.DecompilerCli"
    result.featureName = "Sireum Amandroid Cli:Amandroid.sapp"
    val keys = List[String]("-h", "--help", "-t", "--type")
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
              val v = process(args(j), args(j + 1), keys, org.sireum.option.DumpSource.APK )
              if(result.status){
                opt.typ  = v.get.asInstanceOf[org.sireum.option.DumpSource.Type]
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

def parseSireumAmandroidPasswordTrackingMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum amandroid passwordTracking [options] <Source file> <Sink list file> 

where the available options are:

-h | --help

General Options
  -m   | --memory  Max memory (GB). [Default: 2]
  -msg | --message Message Level. [Default: NO, Choices: (VERBOSE, NORMAL,
                   CRITICAL, NO)]
  -t   | --type    The type of the file you want to analyze. [Default: APK,
                   Choices: (DIR, APK)]   

Analysis Options
  -k  | --k-context Context length [Default: 1]
  -ni | --no-icc    Does not tracking flows via icc 
  -ns | --nostatic  Does not handle static initializer 
  -o  | --outdir    Output directory path [Default: "."]
  -p  | --parallel  Parallel 
  -to | --timeout   Timeout (minute) [Default: 10]   
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = SireumAmandroidPasswordTrackingMode()
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.PasswordTrackingCli"
    result.featureName = "Sireum Amandroid Cli:Amandroid.sapp"
    val keys = List[String]("-h", "--help", "-t", "--type", "-m", "--memory", "-msg", "--message", "-p", "--parallel", "-ns", "--nostatic", "-ni", "--no-icc", "-k", "--k-context", "-to", "--timeout", "-o", "--outdir")
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
              val v = process(args(j), args(j + 1), keys, org.sireum.option.AnalyzeSource.APK )
              if(result.status){
                opt.general.typ  = v.get.asInstanceOf[org.sireum.option.AnalyzeSource.Type]
                j += 1
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
              val v = process(args(j), args(j + 1), keys, 2 )
              if(result.status){
                opt.general.mem  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-msg" | "--message" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--message"
                  r = r || s == "-msg"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--message"
                seenopts += "-msg"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.MessageLevel.NO )
              if(result.status){
                opt.general.msgLevel  = v.get.asInstanceOf[org.sireum.option.MessageLevel.Type]
                j += 1
              }
            case "-p" | "--parallel" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--parallel"
                  r = r || s == "-p"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--parallel"
                seenopts += "-p"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.parallel  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ns" | "--nostatic" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--nostatic"
                  r = r || s == "-ns"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--nostatic"
                seenopts += "-ns"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noStatic  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ni" | "--no-icc" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--no-icc"
                  r = r || s == "-ni"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--no-icc"
                seenopts += "-ni"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noicc  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-k" | "--k-context" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--k-context"
                  r = r || s == "-k"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--k-context"
                seenopts += "-k"
              }
              val v = process(args(j), args(j + 1), keys, 1 )
              if(result.status){
                opt.analysis.k_context  = v.get.asInstanceOf[java.lang.Integer]
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
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.sasFile  = v.get.asInstanceOf[java.lang.String]
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

    if(k+1 < 2) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumAmandroidIntentInjectionMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum amandroid intentInjection [options] <Source file> <Sink list file> 

where the available options are:

-h | --help

General Options
  -m   | --memory  Max memory (GB). [Default: 2]
  -msg | --message Message Level. [Default: NO, Choices: (VERBOSE, NORMAL,
                   CRITICAL, NO)]
  -t   | --type    The type of the file you want to analyze. [Default: APK,
                   Choices: (DIR, APK)]   

Analysis Options
  -k  | --k-context Context length [Default: 1]
  -ni | --no-icc    Does not tracking flows via icc 
  -ns | --nostatic  Does not handle static initializer 
  -o  | --outdir    Output directory path [Default: "."]
  -p  | --parallel  Parallel 
  -to | --timeout   Timeout (minute) [Default: 10]   
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = SireumAmandroidIntentInjectionMode()
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.IntentInjectionCli"
    result.featureName = "Sireum Amandroid Cli:Amandroid.sapp"
    val keys = List[String]("-h", "--help", "-t", "--type", "-m", "--memory", "-msg", "--message", "-p", "--parallel", "-ns", "--nostatic", "-ni", "--no-icc", "-k", "--k-context", "-to", "--timeout", "-o", "--outdir")
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
              val v = process(args(j), args(j + 1), keys, org.sireum.option.AnalyzeSource.APK )
              if(result.status){
                opt.general.typ  = v.get.asInstanceOf[org.sireum.option.AnalyzeSource.Type]
                j += 1
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
              val v = process(args(j), args(j + 1), keys, 2 )
              if(result.status){
                opt.general.mem  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-msg" | "--message" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--message"
                  r = r || s == "-msg"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--message"
                seenopts += "-msg"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.MessageLevel.NO )
              if(result.status){
                opt.general.msgLevel  = v.get.asInstanceOf[org.sireum.option.MessageLevel.Type]
                j += 1
              }
            case "-p" | "--parallel" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--parallel"
                  r = r || s == "-p"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--parallel"
                seenopts += "-p"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.parallel  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ns" | "--nostatic" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--nostatic"
                  r = r || s == "-ns"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--nostatic"
                seenopts += "-ns"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noStatic  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ni" | "--no-icc" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--no-icc"
                  r = r || s == "-ni"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--no-icc"
                seenopts += "-ni"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noicc  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-k" | "--k-context" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--k-context"
                  r = r || s == "-k"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--k-context"
                seenopts += "-k"
              }
              val v = process(args(j), args(j + 1), keys, 1 )
              if(result.status){
                opt.analysis.k_context  = v.get.asInstanceOf[java.lang.Integer]
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
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.sasFile  = v.get.asInstanceOf[java.lang.String]
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

    if(k+1 < 2) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumAmandroidCryptoMisuseMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum amandroid cryptoMisuse [options] <Source file> 

where the available options are:

-h | --help

General Options
  -m   | --memory  Max memory (GB). [Default: 2]
  -msg | --message Message Level. [Default: NO, Choices: (VERBOSE, NORMAL,
                   CRITICAL, NO)]
  -t   | --type    The type of the file you want to analyze. [Default: APK,
                   Choices: (DIR, APK)]   

Analysis Options
  -k  | --k-context Context length [Default: 1]
  -ni | --no-icc    Does not tracking flows via icc 
  -ns | --nostatic  Does not handle static initializer 
  -o  | --outdir    Output directory path [Default: "."]
  -p  | --parallel  Parallel 
  -to | --timeout   Timeout (minute) [Default: 10]   
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = SireumAmandroidCryptoMisuseMode()
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.CryptoMisuseCli"
    result.featureName = "Sireum Amandroid Cli:Amandroid.sapp"
    val keys = List[String]("-h", "--help", "-t", "--type", "-m", "--memory", "-msg", "--message", "-p", "--parallel", "-ns", "--nostatic", "-ni", "--no-icc", "-k", "--k-context", "-to", "--timeout", "-o", "--outdir")
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
              val v = process(args(j), args(j + 1), keys, org.sireum.option.AnalyzeSource.APK )
              if(result.status){
                opt.general.typ  = v.get.asInstanceOf[org.sireum.option.AnalyzeSource.Type]
                j += 1
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
              val v = process(args(j), args(j + 1), keys, 2 )
              if(result.status){
                opt.general.mem  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-msg" | "--message" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--message"
                  r = r || s == "-msg"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--message"
                seenopts += "-msg"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.MessageLevel.NO )
              if(result.status){
                opt.general.msgLevel  = v.get.asInstanceOf[org.sireum.option.MessageLevel.Type]
                j += 1
              }
            case "-p" | "--parallel" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--parallel"
                  r = r || s == "-p"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--parallel"
                seenopts += "-p"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.parallel  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ns" | "--nostatic" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--nostatic"
                  r = r || s == "-ns"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--nostatic"
                seenopts += "-ns"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noStatic  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ni" | "--no-icc" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--no-icc"
                  r = r || s == "-ni"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--no-icc"
                seenopts += "-ni"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noicc  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-k" | "--k-context" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--k-context"
                  r = r || s == "-k"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--k-context"
                seenopts += "-k"
              }
              val v = process(args(j), args(j + 1), keys, 1 )
              if(result.status){
                opt.analysis.k_context  = v.get.asInstanceOf[java.lang.Integer]
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
  def usage {
    addInfoTag(
"""
Usage:
  sireum amandroid taintAnalysis [options] <Source file> <Sink list file> 

where the available options are:

-h | --help

General Options
  -m   | --memory  Max memory (GB). [Default: 2]
  -msg | --message Message Level. [Default: NO, Choices: (VERBOSE, NORMAL,
                   CRITICAL, NO)]
  -t   | --type    The type of the file you want to analyze. [Default: APK,
                   Choices: (DIR, APK)]   

Analysis Options
  -k  | --k-context Context length [Default: 1]
  -ni | --no-icc    Does not tracking flows via icc 
  -ns | --nostatic  Does not handle static initializer 
  -o  | --outdir    Output directory path [Default: "."]
  -p  | --parallel  Parallel 
  -to | --timeout   Timeout (minute) [Default: 10]   
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = SireumAmandroidTaintAnalysisMode()
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.TaintAnalyzeCli"
    result.featureName = "Sireum Amandroid Cli:Amandroid.sapp"
    val keys = List[String]("-h", "--help", "-t", "--type", "-m", "--memory", "-msg", "--message", "-p", "--parallel", "-ns", "--nostatic", "-ni", "--no-icc", "-k", "--k-context", "-to", "--timeout", "-o", "--outdir")
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
              val v = process(args(j), args(j + 1), keys, org.sireum.option.AnalyzeSource.APK )
              if(result.status){
                opt.general.typ  = v.get.asInstanceOf[org.sireum.option.AnalyzeSource.Type]
                j += 1
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
              val v = process(args(j), args(j + 1), keys, 2 )
              if(result.status){
                opt.general.mem  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-msg" | "--message" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--message"
                  r = r || s == "-msg"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--message"
                seenopts += "-msg"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.MessageLevel.NO )
              if(result.status){
                opt.general.msgLevel  = v.get.asInstanceOf[org.sireum.option.MessageLevel.Type]
                j += 1
              }
            case "-p" | "--parallel" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--parallel"
                  r = r || s == "-p"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--parallel"
                seenopts += "-p"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.parallel  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ns" | "--nostatic" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--nostatic"
                  r = r || s == "-ns"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--nostatic"
                seenopts += "-ns"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noStatic  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ni" | "--no-icc" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--no-icc"
                  r = r || s == "-ni"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--no-icc"
                seenopts += "-ni"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noicc  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-k" | "--k-context" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--k-context"
                  r = r || s == "-k"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--k-context"
                seenopts += "-k"
              }
              val v = process(args(j), args(j + 1), keys, 1 )
              if(result.status){
                opt.analysis.k_context  = v.get.asInstanceOf[java.lang.Integer]
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
              val v = process(args(j), args(j), keys, "" )
              if(result.status){
                opt.sasFile  = v.get.asInstanceOf[java.lang.String]
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

    if(k+1 < 2) {
      addErrorTag("Missing required arguments")
    }

  }
}  

def parseSireumAmandroidStagingMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum amandroid staging [options] <Source file> 

where the available options are:

-h | --help

General Options
  -m   | --memory  Max memory (GB). [Default: 2]
  -msg | --message Message Level. [Default: NO, Choices: (VERBOSE, NORMAL,
                   CRITICAL, NO)]
  -t   | --type    The type of the file you want to analyze. [Default: APK,
                   Choices: (DIR, APK)]   

Analysis Options
  -k  | --k-context Context length [Default: 1]
  -ni | --no-icc    Does not tracking flows via icc 
  -ns | --nostatic  Does not handle static initializer 
  -o  | --outdir    Output directory path [Default: "."]
  -p  | --parallel  Parallel 
  -to | --timeout   Timeout (minute) [Default: 10]   
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = SireumAmandroidStagingMode()
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.StagingCli"
    result.featureName = "Sireum Amandroid Cli:Amandroid.sapp"
    val keys = List[String]("-h", "--help", "-t", "--type", "-m", "--memory", "-msg", "--message", "-p", "--parallel", "-ns", "--nostatic", "-ni", "--no-icc", "-k", "--k-context", "-to", "--timeout", "-o", "--outdir")
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
              val v = process(args(j), args(j + 1), keys, org.sireum.option.AnalyzeSource.APK )
              if(result.status){
                opt.general.typ  = v.get.asInstanceOf[org.sireum.option.AnalyzeSource.Type]
                j += 1
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
              val v = process(args(j), args(j + 1), keys, 2 )
              if(result.status){
                opt.general.mem  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-msg" | "--message" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--message"
                  r = r || s == "-msg"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--message"
                seenopts += "-msg"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.MessageLevel.NO )
              if(result.status){
                opt.general.msgLevel  = v.get.asInstanceOf[org.sireum.option.MessageLevel.Type]
                j += 1
              }
            case "-p" | "--parallel" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--parallel"
                  r = r || s == "-p"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--parallel"
                seenopts += "-p"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.parallel  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ns" | "--nostatic" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--nostatic"
                  r = r || s == "-ns"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--nostatic"
                seenopts += "-ns"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noStatic  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ni" | "--no-icc" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--no-icc"
                  r = r || s == "-ni"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--no-icc"
                seenopts += "-ni"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noicc  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-k" | "--k-context" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--k-context"
                  r = r || s == "-k"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--k-context"
                seenopts += "-k"
              }
              val v = process(args(j), args(j + 1), keys, 1 )
              if(result.status){
                opt.analysis.k_context  = v.get.asInstanceOf[java.lang.Integer]
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

def parseSireumAmandroidGenGraphMode(args : Seq[String], i : Int) {
  def usage {
    addInfoTag(
"""
Usage:
  sireum amandroid genGraph [options] <Source file> 

where the available options are:

-h | --help
-f  | --format     Graph output format. [Default: GraphML, Choices: (GML,
                   GraphML)]
-gt | --graph-type Type of the graph. [Default: FULL, Choices: (API,
                   DETAILED_CALL, SIMPLE_CALL, FULL)]
-h  | --header     Type of the graph. [Default: ""]

General Options
  -m   | --memory  Max memory (GB). [Default: 2]
  -msg | --message Message Level. [Default: NO, Choices: (VERBOSE, NORMAL,
                   CRITICAL, NO)]
  -t   | --type    The type of the file you want to analyze. [Default: APK,
                   Choices: (DIR, APK)]   

Analysis Options
  -k  | --k-context Context length [Default: 1]
  -ni | --no-icc    Does not tracking flows via icc 
  -ns | --nostatic  Does not handle static initializer 
  -o  | --outdir    Output directory path [Default: "."]
  -p  | --parallel  Parallel 
  -to | --timeout   Timeout (minute) [Default: 10]   
""".trim) 
  }
  if (i == args.length) {
      usage
    } else {
    val opt = SireumAmandroidGenGraphMode()
    result.options = Some(opt)
    result.className = "org.sireum.amandroid.cli.GenGraphCli"
    result.featureName = "Sireum Amandroid Cli:Amandroid.sapp"
    val keys = List[String]("-h", "--help", "-f", "--format", "-gt", "--graph-type", "-h", "--header", "-t", "--type", "-m", "--memory", "-msg", "--message", "-p", "--parallel", "-ns", "--nostatic", "-ni", "--no-icc", "-k", "--k-context", "-to", "--timeout", "-o", "--outdir")
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
              val v = process(args(j), args(j + 1), keys, org.sireum.option.AnalyzeSource.APK )
              if(result.status){
                opt.general.typ  = v.get.asInstanceOf[org.sireum.option.AnalyzeSource.Type]
                j += 1
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
              val v = process(args(j), args(j + 1), keys, 2 )
              if(result.status){
                opt.general.mem  = v.get.asInstanceOf[java.lang.Integer]
                j += 1
              }
            case "-msg" | "--message" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--message"
                  r = r || s == "-msg"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--message"
                seenopts += "-msg"
              }
              val v = process(args(j), args(j + 1), keys, org.sireum.option.MessageLevel.NO )
              if(result.status){
                opt.general.msgLevel  = v.get.asInstanceOf[org.sireum.option.MessageLevel.Type]
                j += 1
              }
            case "-p" | "--parallel" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--parallel"
                  r = r || s == "-p"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--parallel"
                seenopts += "-p"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.parallel  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ns" | "--nostatic" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--nostatic"
                  r = r || s == "-ns"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--nostatic"
                seenopts += "-ns"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noStatic  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-ni" | "--no-icc" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--no-icc"
                  r = r || s == "-ni"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--no-icc"
                seenopts += "-ni"
              }
              val v = process(args(j), "true", keys, false )
              if(result.status){
                opt.analysis.noicc  = v.get.asInstanceOf[java.lang.Boolean]
              }
            case "-k" | "--k-context" => 

              if(seenopts.exists{s => 
                  var r = false 
                  r = r || s == "--k-context"
                  r = r || s == "-k"
                  r
                }){
                addWarningTag("Option already set: %s".format(args(j)))
              }
              else {
                seenopts += "--k-context"
                seenopts += "-k"
              }
              val v = process(args(j), args(j + 1), keys, 1 )
              if(result.status){
                opt.analysis.k_context  = v.get.asInstanceOf[java.lang.Integer]
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
(c) 2014-2015, Argus & SAnToS Laboratories, Kansas State University
""".trim
+ "\n\n" + 
"""
Available Modes:
  cryptoMisuse      Detecting crypto API misuse
  decompile         Dump .dex file and translate it to Pilar format
  genGraph          Generate Graph for Android apk and store it
  intentInjection   Detecting Intent injection problem
  passwordTracking  Tracking password flow within Android app
  staging           Generate IDFG&DDG for Android apk and store it
  taintAnalysis     Analyze Android apk and output the result
""".trim
)
  } else {
    parseModeHelper("amandroid", Seq("decompile", "passwordTracking", "intentInjection", "cryptoMisuse", "taintAnalysis", "staging", "genGraph"), args, i) {
      _ match {
        case "decompile" =>
          parseSireumAmandroidDecompileMode(args, i + 1)
        case "passwordTracking" =>
          parseSireumAmandroidPasswordTrackingMode(args, i + 1)
        case "intentInjection" =>
          parseSireumAmandroidIntentInjectionMode(args, i + 1)
        case "cryptoMisuse" =>
          parseSireumAmandroidCryptoMisuseMode(args, i + 1)
        case "taintAnalysis" =>
          parseSireumAmandroidTaintAnalysisMode(args, i + 1)
        case "staging" =>
          parseSireumAmandroidStagingMode(args, i + 1)
        case "genGraph" =>
          parseSireumAmandroidGenGraphMode(args, i + 1)
      }
    }
  }
}  

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
  amandroid  Sireum Amandroid Tools
  bakar      Sireum Bakar Tools
  distro     Sireum Package Manager
  launch     Sireum Launcher
  tools      Sireum Development Tools
""".trim
)
  } else {
    parseModeHelper("sireum", Seq("x", "distro", "launch", "tools", "bakar", "amandroid"), args, i) {
      _ match {
        case "x" =>
          parseSireumXMode(args, i + 1)
        case "distro" =>
          parseSireumDistroMode(args, i + 1)
        case "launch" =>
          parseSireumLaunchMode(args, i + 1)
        case "tools" =>
          parseSireumToolsMode(args, i + 1)
        case "bakar" =>
          parseSireumBakarMode(args, i + 1)
        case "amandroid" =>
          parseSireumAmandroidMode(args, i + 1)
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
          v = Some(value.split(",").toList)
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