package org.sireum.bakar.xml

import org.sireum.util._

object DerivedTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.DerivedTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}