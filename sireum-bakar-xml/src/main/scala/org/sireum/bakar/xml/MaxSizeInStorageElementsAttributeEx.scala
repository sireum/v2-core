package org.sireum.bakar.xml

import org.sireum.util._

object MaxSizeInStorageElementsAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MaxSizeInStorageElementsAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}