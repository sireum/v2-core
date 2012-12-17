package org.sireum.bakar.xml

import org.sireum.util._

object AccessAttributeEx {
  def unapply(o : org.sireum.bakar.xml.AccessAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}