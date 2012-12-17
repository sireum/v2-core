package org.sireum.bakar.xml

import org.sireum.util._

object RaiseStatementEx {
  def unapply(o : org.sireum.bakar.xml.RaiseStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getRaisedExceptionQ(),
      o.getAssociatedMessageQ()
    )
  }
}