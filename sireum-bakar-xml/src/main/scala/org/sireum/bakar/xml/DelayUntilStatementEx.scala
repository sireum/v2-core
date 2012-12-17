package org.sireum.bakar.xml

import org.sireum.util._

object DelayUntilStatementEx {
  def unapply(o : org.sireum.bakar.xml.DelayUntilStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getDelayExpressionQ()
    )
  }
}