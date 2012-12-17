package org.sireum.bakar.xml

import org.sireum.util._

object WideWidthAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideWidthAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}