package org.sireum.bakar.xml

import org.sireum.util._

object FloorAttributeEx {
  def unapply(o : org.sireum.bakar.xml.FloorAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}