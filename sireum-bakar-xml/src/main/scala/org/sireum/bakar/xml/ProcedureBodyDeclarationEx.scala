package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureBodyDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureBodyDeclaration) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getAspectSpecificationsQl(),
      o.getBodyDeclarativeItemsQl(),
      o.getBodyStatementsQl(),
      o.getBodyExceptionHandlersQl()
    )
  }
}