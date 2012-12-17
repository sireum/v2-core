package org.sireum.bakar.xml

import org.sireum.util._

object ExponentAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ExponentAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}