package org.sireum.bakar.xml

import org.sireum.util._

object OutputAttributeEx {
  def unapply(o : org.sireum.bakar.xml.OutputAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}