package org.sireum.bakar.xml

import org.sireum.util._

object FormalIncompleteTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalIncompleteTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getHasTaggedQ(),
      o.getAspectSpecificationsQl()
    )
  }
}