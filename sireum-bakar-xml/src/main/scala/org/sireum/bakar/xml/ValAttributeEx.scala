package org.sireum.bakar.xml

import org.sireum.util._

object ValAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ValAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}