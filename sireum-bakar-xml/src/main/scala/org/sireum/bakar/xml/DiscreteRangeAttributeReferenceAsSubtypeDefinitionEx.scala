package org.sireum.bakar.xml

import org.sireum.util._

object DiscreteRangeAttributeReferenceAsSubtypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteRangeAttributeReferenceAsSubtypeDefinition) = {
    Some(
      o.getSloc(),
      o.getRangeAttributeQ()
    )
  }
}