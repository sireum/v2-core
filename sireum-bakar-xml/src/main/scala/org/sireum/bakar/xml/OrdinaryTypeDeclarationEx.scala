package org.sireum.bakar.xml

import org.sireum.util._

object OrdinaryTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.OrdinaryTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getTypeDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}