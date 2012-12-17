package org.sireum.bakar.xml

import org.sireum.util._

object FunctionBodyStubEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyStub) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getResultProfileQ(),
      o.getAspectSpecificationsQl()
    )
  }
}