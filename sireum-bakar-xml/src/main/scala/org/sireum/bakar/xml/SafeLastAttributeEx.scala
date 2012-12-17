package org.sireum.bakar.xml

import org.sireum.util._

object SafeLastAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SafeLastAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}