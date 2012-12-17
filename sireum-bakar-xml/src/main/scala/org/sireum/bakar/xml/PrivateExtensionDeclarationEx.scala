package org.sireum.bakar.xml

import org.sireum.util._

object PrivateExtensionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.PrivateExtensionDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getTypeDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}