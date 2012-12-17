package org.sireum.bakar.xml

import org.sireum.util._

object FormalDiscreteTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalDiscreteTypeDefinition) = {
    Some(
      o.getSloc()
    )
  }
}