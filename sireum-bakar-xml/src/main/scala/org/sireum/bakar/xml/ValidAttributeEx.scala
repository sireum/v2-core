package org.sireum.bakar.xml

import org.sireum.util._

object ValidAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ValidAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}