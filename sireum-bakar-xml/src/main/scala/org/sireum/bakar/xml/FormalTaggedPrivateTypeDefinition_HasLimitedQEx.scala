package org.sireum.bakar.xml

import org.sireum.util._

object FormalTaggedPrivateTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}