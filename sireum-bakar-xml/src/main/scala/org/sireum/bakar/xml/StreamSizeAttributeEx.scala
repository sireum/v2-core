package org.sireum.bakar.xml

import org.sireum.util._

object StreamSizeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.StreamSizeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}