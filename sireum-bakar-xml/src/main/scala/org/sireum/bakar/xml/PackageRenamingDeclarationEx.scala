package org.sireum.bakar.xml

import org.sireum.util._

object PackageRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.PackageRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}