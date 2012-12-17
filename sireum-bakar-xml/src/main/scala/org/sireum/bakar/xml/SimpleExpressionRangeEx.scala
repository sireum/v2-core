package org.sireum.bakar.xml

import org.sireum.util._

object SimpleExpressionRangeEx {
  def unapply(o : org.sireum.bakar.xml.SimpleExpressionRange) = {
    Some(
      o.getSloc(),
      o.getLowerBoundQ(),
      o.getUpperBoundQ()
    )
  }
}