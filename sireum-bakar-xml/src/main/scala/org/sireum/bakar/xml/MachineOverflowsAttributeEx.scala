package org.sireum.bakar.xml

import org.sireum.util._

object MachineOverflowsAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineOverflowsAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}