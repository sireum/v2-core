package org.sireum.bakar.xml

import org.sireum.util._

object BodyVersionAttributeEx {
  def unapply(o : org.sireum.bakar.xml.BodyVersionAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}