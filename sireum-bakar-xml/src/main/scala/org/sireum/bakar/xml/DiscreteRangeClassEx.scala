package org.sireum.bakar.xml

import org.sireum.util._

object DiscreteRangeClassEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteRangeClass) = {
    Some(
      o.getDiscreteRange()
    )
  }
}