package org.sireum.bakar.xml

import org.sireum.util._

object TaggedRecordTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.TaggedRecordTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getRecordDefinitionQ()
    )
  }
}