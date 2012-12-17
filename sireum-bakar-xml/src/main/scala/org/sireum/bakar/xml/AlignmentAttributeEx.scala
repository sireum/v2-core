package org.sireum.bakar.xml

import org.sireum.util._

object AlignmentAttributeEx {
  def unapply(o : org.sireum.bakar.xml.AlignmentAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}