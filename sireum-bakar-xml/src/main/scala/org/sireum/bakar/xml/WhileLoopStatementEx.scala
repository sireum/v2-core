package org.sireum.bakar.xml

import org.sireum.util._

object WhileLoopStatementEx {
  def unapply(o : org.sireum.bakar.xml.WhileLoopStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementIdentifierQ(),
      o.getWhileConditionQ(),
      o.getLoopStatementsQl()
    )
  }
}