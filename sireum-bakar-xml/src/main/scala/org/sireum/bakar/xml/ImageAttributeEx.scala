package org.sireum.bakar.xml

import org.sireum.util._

object ImageAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ImageAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}