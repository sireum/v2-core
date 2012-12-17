package org.sireum.bakar.xml

import org.sireum.util._

object WriteAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WriteAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}