package org.sireum.bakar.xml

import org.sireum.util._

object UniversalFixedDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.UniversalFixedDefinition) = {
    Some(
      o.getSloc()
    )
  }
}