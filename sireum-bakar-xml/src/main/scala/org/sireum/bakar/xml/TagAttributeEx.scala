package org.sireum.bakar.xml

import org.sireum.util._

object TagAttributeEx {
  def unapply(o : org.sireum.bakar.xml.TagAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}