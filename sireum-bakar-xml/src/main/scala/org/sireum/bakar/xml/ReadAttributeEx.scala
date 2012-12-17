package org.sireum.bakar.xml

import org.sireum.util._

object ReadAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ReadAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}