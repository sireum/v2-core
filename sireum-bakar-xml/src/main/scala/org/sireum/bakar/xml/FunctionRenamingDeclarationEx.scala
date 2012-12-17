package org.sireum.bakar.xml

import org.sireum.util._

object FunctionRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FunctionRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getResultProfileQ(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}