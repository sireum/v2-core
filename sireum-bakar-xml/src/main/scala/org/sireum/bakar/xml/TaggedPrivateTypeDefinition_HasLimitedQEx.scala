package org.sireum.bakar.xml

import org.sireum.util._

object TaggedPrivateTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.TaggedPrivateTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}