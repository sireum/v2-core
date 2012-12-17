package org.sireum.bakar.xml

import org.sireum.util._

object SignedZerosAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SignedZerosAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}