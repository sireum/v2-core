package org.sireum.bakar.xml

import org.sireum.util._

object ComposeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ComposeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}