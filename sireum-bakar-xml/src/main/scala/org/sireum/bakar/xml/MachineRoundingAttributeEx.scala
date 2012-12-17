package org.sireum.bakar.xml

import org.sireum.util._

object MachineRoundingAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineRoundingAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}