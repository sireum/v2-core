package org.sireum.bakar.xml

import org.sireum.util._

object OrElseShortCircuitEx {
  def unapply(o : org.sireum.bakar.xml.OrElseShortCircuit) = {
    Some(
      o.getSloc(),
      o.getShortCircuitOperationLeftExpressionQ(),
      o.getShortCircuitOperationRightExpressionQ(),
      o.getType()
    )
  }
}