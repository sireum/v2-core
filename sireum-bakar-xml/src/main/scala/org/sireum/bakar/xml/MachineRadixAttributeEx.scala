package org.sireum.bakar.xml

import org.sireum.util._

object MachineRadixAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineRadixAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}