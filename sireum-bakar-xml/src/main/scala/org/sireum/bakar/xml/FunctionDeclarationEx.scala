package org.sireum.bakar.xml

import org.sireum.util._

object FunctionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FunctionDeclaration) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getResultProfileQ(),
      o.getHasAbstractQ(),
      o.getAspectSpecificationsQl()
    )
  }
}