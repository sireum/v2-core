package org.sireum.bakar.xml

import org.sireum.util._

object UncheckedAccessAttributeEx {
  def unapply(o : org.sireum.bakar.xml.UncheckedAccessAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}