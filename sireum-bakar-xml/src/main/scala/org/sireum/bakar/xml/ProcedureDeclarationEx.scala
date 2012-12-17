package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureDeclaration) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getHasAbstractQ(),
      o.getAspectSpecificationsQl()
    )
  }
}