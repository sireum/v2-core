package org.sireum.bakar.xml

import org.sireum.util._

object LastAttributeEx {
  def unapply(o : org.sireum.bakar.xml.LastAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}