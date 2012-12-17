package org.sireum.bakar.xml

import org.sireum.util._

object PrivateTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.PrivateTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getTypeDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}