package org.sireum.bakar.xml

import org.sireum.util._

object TaggedPrivateTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.TaggedPrivateTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}