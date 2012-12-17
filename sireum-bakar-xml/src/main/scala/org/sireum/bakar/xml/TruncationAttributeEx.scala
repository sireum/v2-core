package org.sireum.bakar.xml

import org.sireum.util._

object TruncationAttributeEx {
  def unapply(o : org.sireum.bakar.xml.TruncationAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}