package org.sireum.bakar.xml

import org.sireum.util._

object ModularTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.ModularTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getModStaticExpressionQ()
    )
  }
}