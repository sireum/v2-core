package org.sireum.bakar.xml

import org.sireum.util._

object UniversalRealDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.UniversalRealDefinition) = {
    Some(
      o.getSloc()
    )
  }
}