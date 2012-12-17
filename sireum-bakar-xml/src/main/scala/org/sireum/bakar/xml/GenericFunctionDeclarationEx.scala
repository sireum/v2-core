package org.sireum.bakar.xml

import org.sireum.util._

object GenericFunctionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericFunctionDeclaration) = {
    Some(
      o.getSloc(),
      o.getGenericFormalPartQl(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getResultProfileQ(),
      o.getAspectSpecificationsQl()
    )
  }
}