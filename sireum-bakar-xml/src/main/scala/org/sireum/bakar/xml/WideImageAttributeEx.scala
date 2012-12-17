package org.sireum.bakar.xml

import org.sireum.util._

object WideImageAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideImageAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}