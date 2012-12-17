package org.sireum.bakar.xml

import org.sireum.util._

object TaggedPrivateTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.TaggedPrivateTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ()
    )
  }
}