package org.sireum.bakar.xml

import org.sireum.util._

object UnbiasedRoundingAttributeEx {
  def unapply(o : org.sireum.bakar.xml.UnbiasedRoundingAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}