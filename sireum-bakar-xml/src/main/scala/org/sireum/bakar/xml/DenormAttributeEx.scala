package org.sireum.bakar.xml

import org.sireum.util._

object DenormAttributeEx {
  def unapply(o : org.sireum.bakar.xml.DenormAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}