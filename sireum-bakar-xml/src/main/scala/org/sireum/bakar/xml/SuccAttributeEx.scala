package org.sireum.bakar.xml

import org.sireum.util._

object SuccAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SuccAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}