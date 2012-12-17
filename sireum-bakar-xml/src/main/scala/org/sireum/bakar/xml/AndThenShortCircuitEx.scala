package org.sireum.bakar.xml

import org.sireum.util._

object AndThenShortCircuitEx {
  def unapply(o : org.sireum.bakar.xml.AndThenShortCircuit) = {
    Some(
      o.getSloc(),
      o.getShortCircuitOperationLeftExpressionQ(),
      o.getShortCircuitOperationRightExpressionQ(),
      o.getType()
    )
  }
}