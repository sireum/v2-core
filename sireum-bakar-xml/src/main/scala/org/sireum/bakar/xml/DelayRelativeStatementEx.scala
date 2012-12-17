package org.sireum.bakar.xml

import org.sireum.util._

object DelayRelativeStatementEx {
  def unapply(o : org.sireum.bakar.xml.DelayRelativeStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getDelayExpressionQ()
    )
  }
}