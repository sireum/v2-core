package org.sireum.bakar.xml

import org.sireum.util._

object BitOrderAttributeEx {
  def unapply(o : org.sireum.bakar.xml.BitOrderAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}