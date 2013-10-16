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
    osateRelPath = osateRelPath + "osate.app"
    val osate = new File(System.getenv("SIREUM_HOME"), osateRelPath).getCanonicalPath()
    val e = new Exec
    e.run(-1, ivector(osate), None)
  }
}