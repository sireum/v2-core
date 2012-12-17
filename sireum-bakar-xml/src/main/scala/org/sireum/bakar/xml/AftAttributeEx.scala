package org.sireum.bakar.xml

import org.sireum.util._

object AftAttributeEx {
  def unapply(o : org.sireum.bakar.xml.AftAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}