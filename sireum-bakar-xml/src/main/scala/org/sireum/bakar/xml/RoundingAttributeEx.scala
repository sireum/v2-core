package org.sireum.bakar.xml

import org.sireum.util._

object RoundingAttributeEx {
  def unapply(o : org.sireum.bakar.xml.RoundingAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}