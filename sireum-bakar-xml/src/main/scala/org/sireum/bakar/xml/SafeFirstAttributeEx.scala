package org.sireum.bakar.xml

import org.sireum.util._

object SafeFirstAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SafeFirstAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}