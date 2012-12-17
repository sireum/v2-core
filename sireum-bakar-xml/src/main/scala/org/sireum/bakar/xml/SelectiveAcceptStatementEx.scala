package org.sireum.bakar.xml

import org.sireum.util._

object SelectiveAcceptStatementEx {
  def unapply(o : org.sireum.bakar.xml.SelectiveAcceptStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementPathsQl()
    )
  }
}