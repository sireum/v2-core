package org.sireum.bakar.xml

import org.sireum.util._

object ClassAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ClassAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}