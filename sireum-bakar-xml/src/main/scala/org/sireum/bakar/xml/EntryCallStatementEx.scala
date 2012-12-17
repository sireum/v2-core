package org.sireum.bakar.xml

import org.sireum.util._

object EntryCallStatementEx {
  def unapply(o : org.sireum.bakar.xml.EntryCallStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getCalledNameQ(),
      o.getCallStatementParametersQl()
    )
  }
}