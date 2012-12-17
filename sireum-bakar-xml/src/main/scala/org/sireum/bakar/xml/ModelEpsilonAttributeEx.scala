package org.sireum.bakar.xml

import org.sireum.util._

object ModelEpsilonAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModelEpsilonAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}