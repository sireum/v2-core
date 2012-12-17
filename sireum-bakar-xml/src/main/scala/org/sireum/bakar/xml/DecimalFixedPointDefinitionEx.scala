package org.sireum.bakar.xml

import org.sireum.util._

object DecimalFixedPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DecimalFixedPointDefinition) = {
    Some(
      o.getSloc(),
      o.getDeltaExpressionQ(),
      o.getDigitsExpressionQ(),
      o.getRealRangeConstraintQ()
    )
  }
}