package org.sireum.bakar.xml

import org.sireum.util._

object PrivateExtensionDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.PrivateExtensionDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getAncestorSubtypeIndicationQ(),
      o.getDefinitionInterfaceListQl()
    )
  }
}