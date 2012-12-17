package org.sireum.bakar.xml

import org.sireum.util._

object FormalTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getTypeDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}