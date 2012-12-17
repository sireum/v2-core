package org.sireum.bakar.xml

import org.sireum.util._

object ScaleAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ScaleAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}