package org.sireum.bakar.xml

import org.sireum.util._

object ComponentSizeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ComponentSizeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}