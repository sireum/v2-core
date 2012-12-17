package org.sireum.bakar.xml

import org.sireum.util._

object ObjectRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ObjectRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasNullExclusionQ(),
      o.getObjectDeclarationViewQ(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}