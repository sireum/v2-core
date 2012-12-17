package org.sireum.bakar.xml

import org.sireum.util._

object ForLoopStatementEx {
  def unapply(o : org.sireum.bakar.xml.ForLoopStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementIdentifierQ(),
      o.getForLoopParameterSpecificationQ(),
      o.getLoopStatementsQl()
    )
  }
}