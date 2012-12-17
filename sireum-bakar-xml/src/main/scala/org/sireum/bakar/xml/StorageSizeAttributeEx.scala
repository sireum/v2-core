package org.sireum.bakar.xml

import org.sireum.util._

object StorageSizeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.StorageSizeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}