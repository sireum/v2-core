package org.sireum.bakar.xml

import org.sireum.util._

object RangeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.RangeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}