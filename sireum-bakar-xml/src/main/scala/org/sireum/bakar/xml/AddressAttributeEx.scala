package org.sireum.bakar.xml

import org.sireum.util._

object AddressAttributeEx {
  def unapply(o : org.sireum.bakar.xml.AddressAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}