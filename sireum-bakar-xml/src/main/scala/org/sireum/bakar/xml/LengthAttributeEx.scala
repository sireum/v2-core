package org.sireum.bakar.xml

import org.sireum.util._

object LengthAttributeEx {
  def unapply(o : org.sireum.bakar.xml.LengthAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}