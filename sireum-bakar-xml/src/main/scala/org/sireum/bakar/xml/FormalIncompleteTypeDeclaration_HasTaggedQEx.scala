package org.sireum.bakar.xml

import org.sireum.util._

object FormalIncompleteTypeDeclaration_HasTaggedQEx {
  def unapply(o : org.sireum.bakar.xml.FormalIncompleteTypeDeclaration.HasTaggedQ) = {
    Some(
      o.getHasTagged()
    )
  }
}