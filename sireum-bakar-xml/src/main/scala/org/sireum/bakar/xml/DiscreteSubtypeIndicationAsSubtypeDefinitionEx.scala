package org.sireum.bakar.xml

import org.sireum.util._

object DiscreteSubtypeIndicationAsSubtypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteSubtypeIndicationAsSubtypeDefinition) = {
    Some(
      o.getSloc(),
      o.getSubtypeMarkQ(),
      o.getSubtypeConstraintQ()
    )
  }
}