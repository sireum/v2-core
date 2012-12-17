package org.sireum.bakar.xml

import org.sireum.util._

object CallerAttributeEx {
  def unapply(o : org.sireum.bakar.xml.CallerAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}