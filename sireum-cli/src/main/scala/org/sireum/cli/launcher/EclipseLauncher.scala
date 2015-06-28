package org.sireum.cli.launcher

import java.io._
import org.sireum.option._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object EclipseLauncher {
  def run(elmode: LaunchEclipseMode) {
    new EclipseLauncher().execute(elmode)
  }

  def run(elmode: LaunchSireumDevMode) {
    new EclipseLauncher().execute(elmode)
  }

  def run(elmode: LaunchBakarV1Mode) {
    new EclipseLauncher().execute(elmode)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class EclipseLauncher {
  final val jvmoptsOld = "sireum.launcher.eclipse.jvmopts"
  final val jvmopts = "sireum.launch.eclipse.jvmopts"

  def execute(opt: LaunchEclipseAppMode) {
    val osArch = OsArchUtil.detect
    val exeExt = if (osArch == OsArch.Win32 || osArch == OsArch.Win64) ".exe" else ""
    val sireumHome = System.getenv("SIREUM_HOME")
    val javaHomeDir = new File(sireumHome, "apps/platform/java")
    var javaOptions = opt.jvmopts.toList

    Config.load.get(jvmopts) match {
      case Some(s) => javaOptions = s.split(",").map(_.trim).toList
      case _ =>
        Config.load.get(jvmoptsOld) match {
          case Some(s) => javaOptions = s.split(",").map(_.trim).toList
          case _ =>
        }
    }

    val (cmd, dir) =
      osArch match {
        case OsArch.Mac32 | OsArch.Mac64 | OsArch.Linux32 |
             OsArch.Linux64 | OsArch.Win32 | OsArch.Win64 =>
          val java =
            if (javaHomeDir.exists) new File(javaHomeDir, "bin/java" + exeExt).getCanonicalPath
            else "java"
          val launcherJar =
            try {
              val pluginsDir = new File(sireumHome, "apps/eclipse/jee/plugins")
              pluginsDir.listFiles(new FilenameFilter {
                def accept(dir: File, name: String) = {
                  name.startsWith("org.eclipse.equinox.launcher_")
                }
              })(0).getCanonicalPath
            } catch {
              case ex: Exception =>
                System.err.println("Could not find Eclipse Equinox launcher jar...")
                System.err.flush()
                sys.exit(-1)
            }
          val addArgs =
            if (osArch == OsArch.Mac32 || osArch == OsArch.Mac64)
              ivector("-Xdock:name=Eclipse",
                "-Xdock:icon=" + new File(sireumHome,
                  "apps/eclipse/jee/Eclipse.icns").
                  getAbsolutePath,
                "-XstartOnFirstThread",
                "-Dorg.eclipse.swt.internal.carbon.smallFonts")
            else ivectorEmpty
          val eclipseDir = new File(sireumHome, "apps/eclipse/jee")
          val launcherArgs =
            ivector(
              "-Declipse.filesystem.useNatives=false", // workaround https://bugs.eclipse.org/bugs/show_bug.cgi?id=470153
              "-Declipse.launcher=sireum",
              "-Dosgi.requiredJavaVersion=1.8",
              "-Dosgi.configuration.area.default=" +
                new File(Config.configDir, "Eclipse-Mars/Configuration").getAbsolutePath,
              "-jar", launcherJar,
              "-showsplash", "org.eclipse.platform",
              "--launcher.defaultAction", "openFile") ++
              (if (java == "java") ivectorEmpty else ivector("-vm", java))
          (java :: javaOptions ++ addArgs ++ launcherArgs ++ opt.args,
            Some(eclipseDir))
        case _ =>
          scala.Console.err.println("Unsupported platform")
          scala.Console.err.flush()
          sys.exit(-1)
      }
    var start = true
    while (start) {
      start = false
      val e = new Exec
      e.run(-1, cmd, None, dir) match {
        case Exec.StringResult(_, 23) => start = true
        case Exec.StringResult(x, code) if code != 0 =>
          val text = x.trim
          if (!text.isEmpty) scala.Console.out.println(text)
          scala.Console.out.println(s"Exit Code: $code")
          scala.Console.out.flush()
        case Exec.ExceptionRaised(ex) =>
          ex.printStackTrace()
        case _ =>
      }
    }
  }
}