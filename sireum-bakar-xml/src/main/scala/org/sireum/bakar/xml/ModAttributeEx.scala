package org.sireum.bakar.xml

import org.sireum.util._

object ModAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}