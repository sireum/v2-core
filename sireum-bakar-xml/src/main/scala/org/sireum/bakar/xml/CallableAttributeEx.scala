package org.sireum.bakar.xml

import org.sireum.util._

object CallableAttributeEx {
  def unapply(o : org.sireum.bakar.xml.CallableAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}