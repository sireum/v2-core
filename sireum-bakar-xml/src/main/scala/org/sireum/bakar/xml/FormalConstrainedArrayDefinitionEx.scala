package org.sireum.bakar.xml

import org.sireum.util._

object FormalConstrainedArrayDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalConstrainedArrayDefinition) = {
    Some(
      o.getSloc(),
      o.getDiscreteSubtypeDefinitionsQl(),
      o.getArrayComponentDefinitionQ()
    )
  }
}