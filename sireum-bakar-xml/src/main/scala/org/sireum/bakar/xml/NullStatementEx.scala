package org.sireum.bakar.xml

import org.sireum.util._

object NullStatementEx {
  def unapply(o : org.sireum.bakar.xml.NullStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl()
    )
  }
}