package org.sireum.bakar.xml

import org.sireum.util._

object UnknownAttributeEx {
  def unapply(o : org.sireum.bakar.xml.UnknownAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}