package org.sireum.bakar.xml

import org.sireum.util._

object MachineMantissaAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineMantissaAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}