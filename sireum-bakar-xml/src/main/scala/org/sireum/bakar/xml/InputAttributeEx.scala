package org.sireum.bakar.xml

import org.sireum.util._

object InputAttributeEx {
  def unapply(o : org.sireum.bakar.xml.InputAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}