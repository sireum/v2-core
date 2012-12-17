package org.sireum.bakar.xml

import org.sireum.util._

object BaseAttributeEx {
  def unapply(o : org.sireum.bakar.xml.BaseAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}