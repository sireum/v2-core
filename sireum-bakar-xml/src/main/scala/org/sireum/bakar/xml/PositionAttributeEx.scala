package org.sireum.bakar.xml

import org.sireum.util._

object PositionAttributeEx {
  def unapply(o : org.sireum.bakar.xml.PositionAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}