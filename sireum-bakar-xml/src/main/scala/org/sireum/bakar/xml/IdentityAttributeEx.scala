package org.sireum.bakar.xml

import org.sireum.util._

object IdentityAttributeEx {
  def unapply(o : org.sireum.bakar.xml.IdentityAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}