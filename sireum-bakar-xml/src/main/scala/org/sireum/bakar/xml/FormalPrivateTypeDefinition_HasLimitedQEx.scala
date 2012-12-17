package org.sireum.bakar.xml

import org.sireum.util._

object FormalPrivateTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.FormalPrivateTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}