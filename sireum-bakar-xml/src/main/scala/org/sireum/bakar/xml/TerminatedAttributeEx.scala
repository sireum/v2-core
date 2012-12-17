package org.sireum.bakar.xml

import org.sireum.util._

object TerminatedAttributeEx {
  def unapply(o : org.sireum.bakar.xml.TerminatedAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}