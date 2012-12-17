package org.sireum.bakar.xml

import org.sireum.util._

object NullProcedureDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.NullProcedureDeclaration) = {
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