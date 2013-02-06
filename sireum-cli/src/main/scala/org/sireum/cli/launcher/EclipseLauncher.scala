package org.sireum.cli.launcher

import java.io._
import org.sireum.option._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object EclipseLauncher {
  def run(elmode : LaunchEclipseMode) {
    new EclipseLauncher().execute(elmode)
  }

  def run(elmode : LaunchSireumDevMode) {
    new EclipseLauncher().execute(elmode)
  }

  def run(elmode : LaunchCompilerDevMode) {
    new EclipseLauncher().execute(elmode)
  }

  def run(elmode : LaunchBakarV1Mode) {
    new EclipseLauncher().execute(elmode)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class EclipseLauncher {
  def execute(opt : LaunchEclipseAppMode) {
    val osArch = OsArchUtil.detect
    val exeExt = if (osArch == OsArch.Win32 || osArch == OsArch.Win64) ".exe" else ""
    val sireumHome = System.getenv("SIREUM_HOME")
    val javaHomeDir = new File(sireumHome, "apps/platform/java")
    var javaOptions = opt.jvmopts.toList
    var java =
      if (javaHomeDir.exists) new File(javaHomeDir, "bin/java" + exeExt).getCanonicalPath
      else "java"
    val launcherVersion = "1.3.0.v20120522-1813"
    val launcherJarRelPath = "apps/eclipse/classic/plugins/org.eclipse.equinox.launcher_" + launcherVersion + ".jar"
    val launcherJar = new File(sireumHome, launcherJarRelPath).getCanonicalPath
    val launcherArgs =
      ivector(
        "-Dosgi.requiredJavaVersion=1.5",
        "-jar", launcherJar,
        "-showsplash", "org.eclipse.platform",
        "--launcher.defaultAction", "openFile") ++
        (if (java == "java") ivectorEmpty else ivector("-vm", java))

    val cmd =
      osArch match {
        case OsArch.Mac32 | OsArch.Mac64 =>
          java :: javaOptions ++
            ivector(
              "-Xdock:icon=../Resources/Eclipse.icns",
              "-XstartOnFirstThread",
              "-Dorg.eclipse.swt.internal.carbon.smallFonts") ++
              launcherArgs ++ opt.args
        case OsArch.Linux32 | OsArch.Linux64 | OsArch.Win32 | OsArch.Win64 =>
          java :: javaOptions ++ launcherArgs ++ opt.args
        case _ =>
          scala.Console.err.println("Unsupported platform")
          scala.Console.err.flush
          sys.exit(-1)
      }
    val e = new Exec
    e.run(-1, cmd, None)
  }
}