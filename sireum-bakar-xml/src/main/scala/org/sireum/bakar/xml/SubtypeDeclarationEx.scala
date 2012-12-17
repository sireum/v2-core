package org.sireum.bakar.xml

import org.sireum.util._

object SubtypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.SubtypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getTypeDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}