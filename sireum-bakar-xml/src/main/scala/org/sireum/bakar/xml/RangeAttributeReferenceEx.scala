package org.sireum.bakar.xml

import org.sireum.util._

object RangeAttributeReferenceEx {
  def unapply(o : org.sireum.bakar.xml.RangeAttributeReference) = {
    Some(
      o.getSloc(),
      o.getRangeAttributeQ()
    )
  }
}