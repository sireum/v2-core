package org.sireum.bakar.xml

import org.sireum.util._

object WidthAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WidthAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}