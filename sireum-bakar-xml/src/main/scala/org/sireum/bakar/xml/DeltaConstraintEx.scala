package org.sireum.bakar.xml

import org.sireum.util._

object DeltaConstraintEx {
  def unapply(o : org.sireum.bakar.xml.DeltaConstraint) = {
    Some(
      o.getSloc(),
      o.getDeltaExpressionQ(),
      o.getRealRangeConstraintQ()
    )
  }
}