package org.sireum.cli.launcher

import java.io._
import org.sireum.option._
import org.sireum.util._

/**
 * @author <a href="mailto:jjedrys@k-state.edu">Jakub Jedryszek</a>
 */
object OsateLauncher {
  def run(elmode : LaunchOsateMode) {
    new OsateLauncher().execute(elmode)
  }
}

/**
 * @author <a href="mailto:jjedrys@k-state.edu">Jakub Jedryszek</a>
 */
class OsateLauncher {
  def execute(opt : LaunchOsateMode) {    
    val osArch = OsArchUtil.detect
    val exeExt = if (osArch == OsArch.Win32 || osArch == OsArch.Win64) ".exe" else ""
    val sireumHome = System.getenv("SIREUM_HOME")
    val javaHomeDir = new File(sireumHome, "apps/platform/java")
    var javaOptions = opt.jvmopts.toList

    val cmd =
      osArch match {
        case OsArch.Mac32 | OsArch.Mac64 | OsArch.Linux32 |
          OsArch.Linux64 | OsArch.Win32 | OsArch.Win64 =>
          var java =
            if (javaHomeDir.exists) new File(javaHomeDir, "bin/java" + exeExt).getCanonicalPath
            else "java"
          val launcherJar =
            try {
              val pluginsDir = new File(sireumHome, "apps/osate/osate2/plugins")
              pluginsDir.listFiles(new FilenameFilter {
                def accept(dir : File, name : String) = {
                  name.startsWith("org.eclipse.equinox.launcher_")
                }
              })(0).getCanonicalPath
            } catch {
              case ex : Exception =>
                System.err.println("Could not find Eclipse Equinox launcher jar...")
                System.err.flush
                sys.exit(-1)
            }
          val addArgs = if (osArch == OsArch.Mac32 || osArch == OsArch.Mac64)
            ivector("-Xdock:icon=../Resources/Eclipse.icns",
              "-XstartOnFirstThread",
              "-Dorg.eclipse.swt.internal.carbon.smallFonts")
          else ivectorEmpty
          val launcherArgs =
            ivector(
              "-Dosgi.requiredJavaVersion=1.5",
              "-jar", launcherJar,
              "-showsplash", "org.eclipse.platform",
              "--launcher.defaultAction", "openFile") ++
              (if (java == "java") ivectorEmpty else ivector("-vm", java))
          java :: javaOptions ++ addArgs ++ launcherArgs ++ opt.args
        case _ =>
          scala.Console.err.println("Unsupported platform")
          scala.Console.err.flush
          sys.exit(-1)
      }
    var start = true
    while (start) {
      start = false
      val e = new Exec
      e.run(-1, cmd, None) match {
        case Exec.StringResult(_, 23) => start = true
        case _                        =>
      }
    }
  }
}