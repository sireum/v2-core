package org.sireum.bakar.xml

import org.sireum.util._

object ConditionalEntryCallStatementEx {
  def unapply(o : org.sireum.bakar.xml.ConditionalEntryCallStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementPathsQl()
    )
  }
}