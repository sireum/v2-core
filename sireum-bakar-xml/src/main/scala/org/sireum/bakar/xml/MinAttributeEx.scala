package org.sireum.bakar.xml

import org.sireum.util._

object MinAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MinAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}