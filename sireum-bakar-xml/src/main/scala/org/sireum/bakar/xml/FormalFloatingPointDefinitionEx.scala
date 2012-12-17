package org.sireum.bakar.xml

import org.sireum.util._

object FormalFloatingPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalFloatingPointDefinition) = {
    Some(
      o.getSloc()
    )
  }
}