package org.sireum.bakar.xml

import org.sireum.util._

object DiscreteSubtypeDefinitionClassEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteSubtypeDefinitionClass) = {
    Some(
      o.getDiscreteSubtypeDefinition()
    )
  }
}