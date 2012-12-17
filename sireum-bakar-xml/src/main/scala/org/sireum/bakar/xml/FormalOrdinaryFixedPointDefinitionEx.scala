package org.sireum.bakar.xml

import org.sireum.util._

object FormalOrdinaryFixedPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalOrdinaryFixedPointDefinition) = {
    Some(
      o.getSloc()
    )
  }
}