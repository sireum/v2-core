package org.sireum.bakar.xml

import org.sireum.util._

object DerivedRecordExtensionDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.DerivedRecordExtensionDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}