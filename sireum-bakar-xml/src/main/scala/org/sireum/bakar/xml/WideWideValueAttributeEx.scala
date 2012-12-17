package org.sireum.bakar.xml

import org.sireum.util._

object WideWideValueAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideWideValueAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}