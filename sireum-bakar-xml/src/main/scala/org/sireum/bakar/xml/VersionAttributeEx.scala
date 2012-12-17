package org.sireum.bakar.xml

import org.sireum.util._

object VersionAttributeEx {
  def unapply(o : org.sireum.bakar.xml.VersionAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}