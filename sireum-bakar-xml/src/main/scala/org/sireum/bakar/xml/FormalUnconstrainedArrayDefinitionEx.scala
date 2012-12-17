package org.sireum.bakar.xml

import org.sireum.util._

object FormalUnconstrainedArrayDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalUnconstrainedArrayDefinition) = {
    Some(
      o.getSloc(),
      o.getIndexSubtypeDefinitionsQl(),
      o.getArrayComponentDefinitionQ()
    )
  }
}