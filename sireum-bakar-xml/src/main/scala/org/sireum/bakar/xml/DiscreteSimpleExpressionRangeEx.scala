package org.sireum.bakar.xml

import org.sireum.util._

object DiscreteSimpleExpressionRangeEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteSimpleExpressionRange) = {
    Some(
      o.getSloc(),
      o.getLowerBoundQ(),
      o.getUpperBoundQ()
    )
  }
}