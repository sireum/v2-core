package org.sireum.bakar.xml

import org.sireum.util._

object WideValueAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideValueAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}