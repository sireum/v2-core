package org.sireum.bakar.xml

import org.sireum.util._

object FormalPrivateTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalPrivateTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ()
    )
  }
}