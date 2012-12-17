package org.sireum.bakar.xml

import org.sireum.util._

object FirstAttributeEx {
  def unapply(o : org.sireum.bakar.xml.FirstAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}