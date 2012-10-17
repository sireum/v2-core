package org.sireum.cli.launcher

import java.io._
import org.sireum.option._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object EclipseLauncher {
  def run(elmode : LaunchEclipseMode) {
    new EclipseLauncher().execute
  }

  def run(elmode : LaunchSireumDevMode) {
    new EclipseLauncher().execute
  }

  def run(elmode : LaunchCompilerDevMode) {
    new EclipseLauncher().execute
  }

  def run(elmode : LaunchBakarV1Mode) {
    new EclipseLauncher().execute
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class EclipseLauncher {
  def execute {
    var eclipseRelPath = "apps/eclipse/classic/eclipse"
    OsArchUtil.detect match {
      case OsArch.Mac32 | OsArch.Mac64 =>
      case OsArch.Linux32 | OsArch.Linux64 => 
        eclipseRelPath += ".sh"
      case OsArch.Win32 | OsArch.Win64 =>
        eclipseRelPath += ".bat"
      case _ =>
        scala.Console.err.println("Unsupported platform")
        scala.Console.err.flush
        sys.exit(-1)
    }
    val eclipse = new File(System.getenv("SIREUM_HOME"), eclipseRelPath).getCanonicalPath
    val e = new Exec
    e.run(-1, ilist(eclipse), None)
  }
}