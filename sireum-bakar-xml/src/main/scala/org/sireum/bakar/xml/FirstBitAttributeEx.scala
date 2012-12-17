package org.sireum.bakar.xml

import org.sireum.util._

object FirstBitAttributeEx {
  def unapply(o : org.sireum.bakar.xml.FirstBitAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}