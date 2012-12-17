package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureBodyStubEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureBodyStub) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getAspectSpecificationsQl()
    )
  }
}