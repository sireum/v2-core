package org.sireum.bakar.xml

import org.sireum.util._

object TaggedRecordTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.TaggedRecordTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}