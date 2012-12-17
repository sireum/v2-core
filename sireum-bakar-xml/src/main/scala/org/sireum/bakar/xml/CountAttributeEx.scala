package org.sireum.bakar.xml

import org.sireum.util._

object CountAttributeEx {
  def unapply(o : org.sireum.bakar.xml.CountAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}