package org.sireum.bakar.xml

import org.sireum.util._

object SizeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SizeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}