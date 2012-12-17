package org.sireum.bakar.xml

import org.sireum.util._

object LastBitAttributeEx {
  def unapply(o : org.sireum.bakar.xml.LastBitAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}