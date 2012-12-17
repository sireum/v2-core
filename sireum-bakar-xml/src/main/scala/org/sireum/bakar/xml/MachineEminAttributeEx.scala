package org.sireum.bakar.xml

import org.sireum.util._

object MachineEminAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineEminAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}