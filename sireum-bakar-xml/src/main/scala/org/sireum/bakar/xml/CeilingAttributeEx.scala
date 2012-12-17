package org.sireum.bakar.xml

import org.sireum.util._

object CeilingAttributeEx {
  def unapply(o : org.sireum.bakar.xml.CeilingAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}