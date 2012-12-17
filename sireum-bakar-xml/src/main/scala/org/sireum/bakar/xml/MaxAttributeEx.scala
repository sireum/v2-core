package org.sireum.bakar.xml

import org.sireum.util._

object MaxAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MaxAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}