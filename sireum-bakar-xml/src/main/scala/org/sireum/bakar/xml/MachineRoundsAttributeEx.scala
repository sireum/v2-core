package org.sireum.bakar.xml

import org.sireum.util._

object MachineRoundsAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineRoundsAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}