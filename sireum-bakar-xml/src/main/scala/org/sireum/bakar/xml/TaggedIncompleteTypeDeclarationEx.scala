package org.sireum.bakar.xml

import org.sireum.util._

object TaggedIncompleteTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.TaggedIncompleteTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getAspectSpecificationsQl()
    )
  }
}