package org.sireum.bakar.xml

import org.sireum.util._

object IncompleteTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.IncompleteTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getAspectSpecificationsQl()
    )
  }
}