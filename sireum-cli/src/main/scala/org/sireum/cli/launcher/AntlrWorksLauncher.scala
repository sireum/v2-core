package org.sireum.cli.launcher

import java.io._
import org.sireum.option._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object AntlrWorksLauncher {
  def run(elmode : LaunchAntlrWorksMode) {
    new AntlrWorksLauncher().execute
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class AntlrWorksLauncher {
  def execute {
    var antlrRelPath = "apps/antlr/works/"
    OsArchUtil.detect match {
      case OsArch.Mac32 | OsArch.Mac64 =>
        antlrRelPath = antlrRelPath + "ANTLRWorks.app/Contents/Resources/Java/antlrworks.jar"
      case _ =>
        antlrRelPath = antlrRelPath + "antlrworks.jar"
    }
    val antlrworks = new File(System.getenv("SIREUM_HOME"), antlrRelPath).getCanonicalPath
    val e = new Exec
    OsArchUtil.detect match {
      case OsArch.Mac32 | OsArch.Mac64 =>
        e.run(-1, ivector("java", "-Xmx1024m", "-jar", antlrworks), None)
      case _ =>
        e.run(-1, ivector("java", "-jar", antlrworks), None)
    }
  }
}