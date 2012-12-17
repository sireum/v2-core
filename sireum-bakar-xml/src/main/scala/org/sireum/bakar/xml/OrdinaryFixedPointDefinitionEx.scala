package org.sireum.bakar.xml

import org.sireum.util._

object OrdinaryFixedPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.OrdinaryFixedPointDefinition) = {
    Some(
      o.getSloc(),
      o.getDeltaExpressionQ(),
      o.getRealRangeConstraintQ()
    )
  }
}