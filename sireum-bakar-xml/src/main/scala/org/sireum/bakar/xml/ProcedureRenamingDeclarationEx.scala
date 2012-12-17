package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}