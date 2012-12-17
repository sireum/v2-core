package org.sireum.bakar.xml

import org.sireum.util._

object PriorityAttributeEx {
  def unapply(o : org.sireum.bakar.xml.PriorityAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}