package org.sireum.bakar.xml

import org.sireum.util._

object TimedEntryCallStatementEx {
  def unapply(o : org.sireum.bakar.xml.TimedEntryCallStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementPathsQl()
    )
  }
}