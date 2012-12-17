package org.sireum.bakar.xml

import org.sireum.util._

object UnconstrainedArrayDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.UnconstrainedArrayDefinition) = {
    Some(
      o.getSloc(),
      o.getIndexSubtypeDefinitionsQl(),
      o.getArrayComponentDefinitionQ()
    )
  }
}