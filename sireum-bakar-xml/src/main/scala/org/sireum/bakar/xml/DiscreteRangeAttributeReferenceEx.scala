package org.sireum.bakar.xml

import org.sireum.util._

object DiscreteRangeAttributeReferenceEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteRangeAttributeReference) = {
    Some(
      o.getSloc(),
      o.getRangeAttributeQ()
    )
  }
}