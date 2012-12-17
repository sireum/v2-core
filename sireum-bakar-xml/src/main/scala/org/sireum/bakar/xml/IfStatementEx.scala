package org.sireum.bakar.xml

import org.sireum.util._

object IfStatementEx {
  def unapply(o : org.sireum.bakar.xml.IfStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementPathsQl()
    )
  }
}