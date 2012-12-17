package org.sireum.bakar.xml

import org.sireum.util._

object FormalDerivedTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalDerivedTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getSubtypeMarkQ(),
      o.getDefinitionInterfaceListQl(),
      o.getHasPrivateQ()
    )
  }
}