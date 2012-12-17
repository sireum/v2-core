package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureCallStatementEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureCallStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getCalledNameQ(),
      o.getCallStatementParametersQl(),
      o.getIsPrefixNotationQ()
    )
  }
}