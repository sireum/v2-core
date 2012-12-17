package org.sireum.bakar.xml

import org.sireum.util._

object AcceptStatementEx {
  def unapply(o : org.sireum.bakar.xml.AcceptStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getAcceptEntryDirectNameQ(),
      o.getAcceptEntryIndexQ(),
      o.getAcceptParametersQl(),
      o.getAcceptBodyStatementsQl(),
      o.getAcceptBodyExceptionHandlersQl()
    )
  }
}