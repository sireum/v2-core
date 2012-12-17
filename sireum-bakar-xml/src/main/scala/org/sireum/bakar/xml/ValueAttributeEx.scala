package org.sireum.bakar.xml

import org.sireum.util._

object ValueAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ValueAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}