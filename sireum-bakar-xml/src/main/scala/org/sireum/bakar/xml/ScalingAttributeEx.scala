package org.sireum.bakar.xml

import org.sireum.util._

object ScalingAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ScalingAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}