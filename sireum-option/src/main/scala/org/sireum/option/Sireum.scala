/*
Copyright (c) 2011-2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.option

import org.sireum.util._
import EmptySireumMode._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 * @author <a href="mailto:fgwei@k-state.edu">Fengguo Wei</a>
 */
@Mode(command = "sireum", header = """
Sireum: A Software Analysis Platform
(c) 2011-2014, SAnToS Laboratory, Kansas State University
""")
case class SireumMode(
  //  bogor : BogorMode = BogorMode(),
  //  kiasan : KiasanMode = KiasanMode(),
  distro : SireumDistroMode = SireumDistroMode(),
  launch : SireumLaunchMode = SireumLaunchMode(),
  tools : SireumToolsMode = SireumToolsMode(),
  bakar : SireumBakarMode = SireumBakarMode(),
  amandroid : SireumAmandroidMode = SireumAmandroidMode(),
  x : SireumXMode = SireumXMode())

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "x", header = "Sireum X", desc = "Sireum Experimental Features", listed = false)
case class SireumXMode(
//  amandroid : SireumAmandroidMode = internal(SireumAmandroidMode())
)

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "bogor", header = """
Sireum/Bogor: A Software Model Checking Framework
(c) 2012-2014, SAnToS Laboratory, Kansas State University
""")
case class BogorMode()

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "kiasan", header = """
Sireum/Kiasan: A Symbolic Execution Framework
(c) 2012-2014, SAnToS Laboratory, Kansas State University
""")
case class KiasanMode(
  java : KiasanJavaMode = KiasanJavaMode(),
  bakar : KiasanBakarMode = KiasanBakarMode(),
  eval : KiasanEvalMode = KiasanEvalMode())

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "tools", header = "Sireum Tools", desc = "Sireum Development Tools")
case class SireumToolsMode(
  pipeline : PipelineMode = PipelineMode(),
  cligen : CliGenMode = CliGenMode(),
  antlr : TreeVisitorGenMode = TreeVisitorGenMode(),
  sapper : SapperMode = SapperMode(),
  upickler : UPicklerMode = UPicklerMode(),
  jvm : JVMMode = JVMMode())

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "distro", header = "Sireum Distro", desc = "Sireum Package Manager")
case class SireumDistroMode()

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "launch", header = "Sireum Launcher", desc = "Sireum Launcher")
case class SireumLaunchMode(
  eclipse : LaunchEclipseMode = LaunchEclipseMode(),
  sireumdev : LaunchSireumDevMode = LaunchSireumDevMode(),
  bakar : LaunchBakarV1Mode = LaunchBakarV1Mode(),
  antlrworks : LaunchAntlrWorksMode = LaunchAntlrWorksMode(),
  server : LaunchServerMode = internal(LaunchServerMode()),
  symbologics : LaunchSymbologicsMode = internal(LaunchSymbologicsMode()),
  bkserver : LaunchBakarKiasanServerMode = internal(LaunchBakarKiasanServerMode()),
  bakarv1gps : LaunchBakarV1GpsMode = LaunchBakarV1GpsMode(),
  bakargps : LaunchBakarGpsMode = LaunchBakarGpsMode(),
  osate : LaunchOsateMode = LaunchOsateMode())

abstract class LaunchEclipseAppMode {
  def jvmopts : ISeq[String]
  def args : ISeq[String]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "eclipse", className = "org.sireum.cli.launcher.EclipseLauncher", featureName = "EclipseBase.sapp",
  desc = "Launch Eclipse")
case class LaunchEclipseMode(
  @Option(shortKey = "j", longKey = "jvmopts", desc = "Options for Java") //
  var jvmopts : ISeq[String] = ivector("-Xms128m", "-Xmx1024m"),
  @Option(longKey = "args", desc = "Arguments for Eclipse", isRaw = true) // 
  var args : ISeq[String] = ivectorEmpty) extends LaunchEclipseAppMode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "sireumdev", className = "org.sireum.cli.launcher.EclipseLauncher", featureName = "SireumDev.sapp",
  desc = "Launch Eclipse with Sireum Dev Plugins")
case class LaunchSireumDevMode(
  @Option(shortKey = "j", longKey = "jvmopts", desc = "Options for Java") //
  var jvmopts : ISeq[String] = ivector("-Xms128m", "-Xmx1024m"),
  @Option(longKey = "args", desc = "Arguments for Eclipse", isRaw = true) //
  var args : ISeq[String] = ivectorEmpty) extends LaunchEclipseAppMode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "bakar", className = "org.sireum.cli.launcher.EclipseLauncher", featureName = "BakarV1.sapp",
  desc = "Launch Eclipse with Bakar Plugins")
case class LaunchBakarV1Mode(
  @Option(shortKey = "j", longKey = "jvmopts", desc = "Options for Java") //
  var jvmopts : ISeq[String] = ivector("-Xms128m", "-Xmx1024m"),
  @Option(longKey = "args", desc = "Arguments for Eclipse", isRaw = true) // 
  var args : ISeq[String] = ivectorEmpty) extends LaunchEclipseAppMode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "antlrworks", className = "org.sireum.cli.launcher.AntlrWorksLauncher", featureName = "Antlr.sapp",
  desc = "Launch ANTLRWorks")
case class LaunchAntlrWorksMode()

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "server", className = "org.sireum.server.Server", featureName = "Sireum Server", desc = "Launch Server")
case class LaunchServerMode(
  @Option(shortKey = "p", longKey = "port", desc = "Server Port") //
  var port : Int = 8080,
  @Option(shortKey = "q", longKey = "quiet", desc = "Quiet Mode") //
  var quiet : Boolean = false,
  @Option(shortKey = "w", longKey = "workers", desc = "Number of Worker Threads") //
  var workers : Int = 1,
  @Option(shortKey = "l", longKey = "local", desc = "Local Mode") //
  var local : Boolean = true)

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "symbologics", className = "org.sireum.server.SymbologicsServer", featureName = "Sireum Symbologics Server Plugin", desc = "Launch Symbologics")
case class LaunchSymbologicsMode(
  @Option(shortKey = "o", longKey = "host", desc = "Server Host") //
  var host : String = "127.0.0.1",
  @Option(shortKey = "p", longKey = "port", desc = "Server Port") //
  var port : Int = 8080,
  @Option(shortKey = "l", longKey = "local", desc = "Local Mode") //
  var local : Boolean = true)

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "bkserver", className = "org.sireum.server.BakarKiasanServer", featureName = "Sireum Bakar Kiasan Server Plugin", desc = "Launch Bakar Kiasan Server")
case class LaunchBakarKiasanServerMode(
  @Option(shortKey = "o", longKey = "host", desc = "Server Host") //
  var host : String = "127.0.0.1",
  @Option(shortKey = "p", longKey = "port", desc = "Server Port") //
  var port : Int = 8080,
  @Option(shortKey = "i", longKey = "id", desc = "Id") //
  var id : String = "",
  @Option(shortKey = "r", longKey = "remote", desc = "Remote Server Host") //
  var remote : String = "",
  @Option(shortKey = "t", longKey = "remoteport", desc = "Remote Server Port") //
  var remotePort : Int = 80,
  @Option(shortKey = "w", longKey = "workers", desc = "Number of Worker Threads") //
  var workers : Int = 1,
  @Option(shortKey = "l", longKey = "local", desc = "Local Mode") //
  var local : Boolean = true,
  @Option(shortKey = "g", longKey = "gps", desc = "GPS Mode")
  var gpsMode : Boolean = true)

/**
 * @author <a href="mailto:jjedrys@k-state.edu">Jakub Jedryszek</a>
 */
@Main(value = "bakarv1gps", className = "org.sireum.cli.launcher.GpsLauncher", featureName = "BakarGpsV1.sapp",
  desc = "Launch Gps with BakarV1 Plugins")
case class LaunchBakarV1GpsMode()

/**
 * @author <a href="mailto:jjedrys@k-state.edu">Jakub Jedryszek</a>
 */
@Main(value = "bakargps", className = "org.sireum.cli.launcher.GpsLauncher", featureName = "BakarGps.sapp",
  desc = "Launch Gpswith BakarV2 Plugins")
case class LaunchBakarGpsMode()

/**
 * @author <a href="mailto:jjedrys@k-state.edu">Jakub Jedryszek</a>
 */
@Main(value = "osate", className = "org.sireum.cli.launcher.OsateLauncher", featureName = "Osate.sapp",
  desc = "Launch Osate with egit plugin")
case class LaunchOsateMode(
  @Option(shortKey = "j", longKey = "jvmopts", desc = "Options for Java") //
  var jvmopts : ISeq[String] = ivector("-Xms128m", "-Xmx1024m"),
  @Option(longKey = "args", desc = "Arguments for Eclipse", isRaw = true) // 
  var args : ISeq[String] = ivectorEmpty) extends LaunchEclipseAppMode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object EmptySireumMode {
  def internal[T](o : T) : T =
    org.sireum.macros.cc.ite(o, null.asInstanceOf[T])

  def dev[T](o : T) : T =
    org.sireum.macros.cc.ite(System.getenv("SITE_DESC") match {
      case "stable" => false
      case _        => true
    }, o, null.asInstanceOf[T])
}
