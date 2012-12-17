package org.sireum.bakar.xml

import org.sireum.util._

object ReturnStatementEx {
  def unapply(o : org.sireum.bakar.xml.ReturnStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getReturnExpressionQ()
    )
  }
}