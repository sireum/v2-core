package org.sireum.bakar.xml

import org.sireum.util._

object ImplementationDefinedAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ImplementationDefinedAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}