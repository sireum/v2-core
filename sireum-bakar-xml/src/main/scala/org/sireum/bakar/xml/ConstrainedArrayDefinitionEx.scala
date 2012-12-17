package org.sireum.bakar.xml

import org.sireum.util._

object ConstrainedArrayDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.ConstrainedArrayDefinition) = {
    Some(
      o.getSloc(),
      o.getDiscreteSubtypeDefinitionsQl(),
      o.getArrayComponentDefinitionQ()
    )
  }
}