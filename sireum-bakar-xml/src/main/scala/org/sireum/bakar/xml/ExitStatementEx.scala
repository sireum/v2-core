package org.sireum.bakar.xml

import org.sireum.util._

object ExitStatementEx {
  def unapply(o : org.sireum.bakar.xml.ExitStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getExitLoopNameQ(),
      o.getExitConditionQ()
    )
  }
}