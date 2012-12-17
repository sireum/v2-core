package org.sireum.bakar.xml

import org.sireum.util._

object ModelSmallAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModelSmallAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}