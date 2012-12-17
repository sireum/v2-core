package org.sireum.bakar.xml

import org.sireum.util._

object WideWideWidthAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideWideWidthAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}