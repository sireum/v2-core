package org.sireum.bakar.xml

import org.sireum.util._

object PrivateTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.PrivateTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ()
    )
  }
}