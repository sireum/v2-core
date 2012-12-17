package org.sireum.bakar.xml

import org.sireum.util._

object RemainderAttributeEx {
  def unapply(o : org.sireum.bakar.xml.RemainderAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}