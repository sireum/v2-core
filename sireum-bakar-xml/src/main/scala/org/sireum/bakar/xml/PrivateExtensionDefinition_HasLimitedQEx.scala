package org.sireum.bakar.xml

import org.sireum.util._

object PrivateExtensionDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.PrivateExtensionDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}