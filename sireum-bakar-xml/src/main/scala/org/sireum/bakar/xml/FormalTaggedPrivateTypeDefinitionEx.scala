package org.sireum.bakar.xml

import org.sireum.util._

object FormalTaggedPrivateTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ()
    )
  }
}