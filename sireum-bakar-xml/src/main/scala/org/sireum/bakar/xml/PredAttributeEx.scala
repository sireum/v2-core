package org.sireum.bakar.xml

import org.sireum.util._

object PredAttributeEx {
  def unapply(o : org.sireum.bakar.xml.PredAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}