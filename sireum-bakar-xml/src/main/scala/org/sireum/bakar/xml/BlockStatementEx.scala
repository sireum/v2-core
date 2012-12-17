package org.sireum.bakar.xml

import org.sireum.util._

object BlockStatementEx {
  def unapply(o : org.sireum.bakar.xml.BlockStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementIdentifierQ(),
      o.getBlockDeclarativeItemsQl(),
      o.getBlockStatementsQl(),
      o.getBlockExceptionHandlersQl()
    )
  }
}