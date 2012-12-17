package org.sireum.bakar.xml

import org.sireum.util._

object GenericPackageRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericPackageRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}