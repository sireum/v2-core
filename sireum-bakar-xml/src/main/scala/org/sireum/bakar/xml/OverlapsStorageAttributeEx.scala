package org.sireum.bakar.xml

import org.sireum.util._

object OverlapsStorageAttributeEx {
  def unapply(o : org.sireum.bakar.xml.OverlapsStorageAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}