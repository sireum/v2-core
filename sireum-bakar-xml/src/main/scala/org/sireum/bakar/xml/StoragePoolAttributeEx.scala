package org.sireum.bakar.xml

import org.sireum.util._

object StoragePoolAttributeEx {
  def unapply(o : org.sireum.bakar.xml.StoragePoolAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}