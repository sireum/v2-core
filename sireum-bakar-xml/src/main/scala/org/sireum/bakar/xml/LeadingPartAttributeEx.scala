package org.sireum.bakar.xml

import org.sireum.util._

object LeadingPartAttributeEx {
  def unapply(o : org.sireum.bakar.xml.LeadingPartAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}