package org.sireum.cli.launcher

import java.io._
import org.sireum.option._
import org.sireum.util._

/**
 * @author <a href="mailto:jjedrys@k-state.edu">Jakub Jedryszek</a>
 */
object OsateLauncher {
  def run(elmode : LaunchOsateMode) {
    new OsateLauncher().execute
  }
}

/**
 * @author <a href="mailto:jjedrys@k-state.edu">Jakub Jedryszek</a>
 */
class OsateLauncher {
  def execute {
    var osateRelPath = "/apps/osate/osate2/"
    val osArch = OsArchUtil.detect
    val extension = if (osArch == OsArch.Win32 || osArch == OsArch.Win64) ".exe" else if (osArch == OsArch.Mac32 || osArch == OsArch.Mac64) ".app" else ""
    osateRelPath = osateRelPath + "osate" + extension
    val osate = new File(System.getenv("SIREUM_HOME"), osateRelPath).getCanonicalPath()
    val e = new Exec
    if (osArch == OsArch.Mac64 || osArch == OsArch.Mac32) {
    	e.run(-1, ivector("open", osate), None)
    } else {
      e.run(-1, ivector(osate), None)
    }
  }
}