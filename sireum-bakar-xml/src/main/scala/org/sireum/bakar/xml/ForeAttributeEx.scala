package org.sireum.bakar.xml

import org.sireum.util._

object ForeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ForeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}