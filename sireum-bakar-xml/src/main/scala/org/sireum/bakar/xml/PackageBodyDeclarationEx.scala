package org.sireum.bakar.xml

import org.sireum.util._

object PackageBodyDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.PackageBodyDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getBodyDeclarativeItemsQl(),
      o.getBodyStatementsQl(),
      o.getBodyExceptionHandlersQl()
    )
  }
}