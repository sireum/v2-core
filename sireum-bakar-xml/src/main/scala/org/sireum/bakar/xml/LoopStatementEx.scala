package org.sireum.bakar.xml

import org.sireum.util._

object LoopStatementEx {
  def unapply(o : org.sireum.bakar.xml.LoopStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementIdentifierQ(),
      o.getLoopStatementsQl()
    )
  }
}