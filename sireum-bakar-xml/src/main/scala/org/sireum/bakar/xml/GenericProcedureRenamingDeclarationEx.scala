package org.sireum.bakar.xml

import org.sireum.util._

object GenericProcedureRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericProcedureRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}