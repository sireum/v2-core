package org.sireum.bakar.xml

import org.sireum.util._

object ComponentDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.ComponentDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAliasedQ(),
      o.getComponentDefinitionViewQ()
    )
  }
}