package org.sireum.bakar.xml

import org.sireum.util._

object DerivedTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DerivedTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getParentSubtypeIndicationQ()
    )
  }
}