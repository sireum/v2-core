package org.sireum.bakar.xml

import org.sireum.util._

object ExternalTagAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ExternalTagAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}