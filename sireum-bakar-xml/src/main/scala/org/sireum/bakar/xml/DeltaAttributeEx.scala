package org.sireum.bakar.xml

import org.sireum.util._

object DeltaAttributeEx {
  def unapply(o : org.sireum.bakar.xml.DeltaAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}