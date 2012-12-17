package org.sireum.bakar.xml

import org.sireum.util._

object FractionAttributeEx {
  def unapply(o : org.sireum.bakar.xml.FractionAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}