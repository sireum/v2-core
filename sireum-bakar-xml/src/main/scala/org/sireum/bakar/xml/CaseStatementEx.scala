package org.sireum.bakar.xml

import org.sireum.util._

object CaseStatementEx {
  def unapply(o : org.sireum.bakar.xml.CaseStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getCaseExpressionQ(),
      o.getStatementPathsQl()
    )
  }
}