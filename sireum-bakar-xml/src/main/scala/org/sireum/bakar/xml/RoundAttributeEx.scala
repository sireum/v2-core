package org.sireum.bakar.xml

import org.sireum.util._

object RoundAttributeEx {
  def unapply(o : org.sireum.bakar.xml.RoundAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}