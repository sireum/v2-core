package org.sireum.bakar.xml

import org.sireum.util._

object RootRealDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.RootRealDefinition) = {
    Some(
      o.getSloc()
    )
  }
}