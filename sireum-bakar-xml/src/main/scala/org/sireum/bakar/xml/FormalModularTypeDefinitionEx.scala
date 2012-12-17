package org.sireum.bakar.xml

import org.sireum.util._

object FormalModularTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalModularTypeDefinition) = {
    Some(
      o.getSloc()
    )
  }
}