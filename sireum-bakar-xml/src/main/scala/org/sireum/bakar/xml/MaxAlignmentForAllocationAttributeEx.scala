package org.sireum.bakar.xml

import org.sireum.util._

object MaxAlignmentForAllocationAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MaxAlignmentForAllocationAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}