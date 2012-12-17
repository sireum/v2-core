package org.sireum.bakar.xml

import org.sireum.util._

object ModelAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModelAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}