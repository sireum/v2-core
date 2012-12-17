package org.sireum.bakar.xml

import org.sireum.util._

object EnumerationTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.EnumerationTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getEnumerationLiteralDeclarationsQl()
    )
  }
}