package org.sireum.bakar.xml

import org.sireum.util._

object RecordTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.RecordTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}