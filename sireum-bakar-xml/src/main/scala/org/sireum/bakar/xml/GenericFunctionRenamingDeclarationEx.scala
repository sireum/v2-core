package org.sireum.bakar.xml

import org.sireum.util._

object GenericFunctionRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericFunctionRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}