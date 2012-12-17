package org.sireum.bakar.xml

import org.sireum.util._

object ConstrainedAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ConstrainedAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}