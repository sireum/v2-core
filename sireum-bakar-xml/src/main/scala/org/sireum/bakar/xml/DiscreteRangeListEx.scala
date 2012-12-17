package org.sireum.bakar.xml

import org.sireum.util._

object DiscreteRangeListEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteRangeList) = {
    Some(
      o.getDiscreteRanges()
    )
  }
}