package org.sireum.bakar.xml

import org.sireum.util._

object ModelEminAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModelEminAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}