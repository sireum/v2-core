package org.sireum.bakar.xml

import org.sireum.util._

object ModulusAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModulusAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}