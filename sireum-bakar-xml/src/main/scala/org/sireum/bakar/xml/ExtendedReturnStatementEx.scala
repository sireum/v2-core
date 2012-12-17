package org.sireum.bakar.xml

import org.sireum.util._

object ExtendedReturnStatementEx {
  def unapply(o : org.sireum.bakar.xml.ExtendedReturnStatement) = {
    Some(
      o.getSloc(),
      o.getReturnObjectDeclarationQ(),
      o.getExtendedReturnStatementsQl(),
      o.getExtendedReturnExceptionHandlersQl()
    )
  }
}