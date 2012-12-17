package org.sireum.bakar.xml

import org.sireum.util._

object ProtectedTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ProtectedTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getAspectSpecificationsQl(),
      o.getDeclarationInterfaceListQl(),
      o.getTypeDeclarationViewQ()
    )
  }
}