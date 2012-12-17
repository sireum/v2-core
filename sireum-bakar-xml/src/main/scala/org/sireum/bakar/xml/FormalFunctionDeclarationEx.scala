package org.sireum.bakar.xml

import org.sireum.util._

object FormalFunctionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalFunctionDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getResultProfileQ(),
      o.getFormalSubprogramDefaultQ(),
      o.getHasAbstractQ(),
      o.getAspectSpecificationsQl()
    )
  }
}