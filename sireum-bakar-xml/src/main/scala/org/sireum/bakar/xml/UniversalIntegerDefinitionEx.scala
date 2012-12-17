package org.sireum.bakar.xml

import org.sireum.util._

object UniversalIntegerDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.UniversalIntegerDefinition) = {
    Some(
      o.getSloc()
    )
  }
}