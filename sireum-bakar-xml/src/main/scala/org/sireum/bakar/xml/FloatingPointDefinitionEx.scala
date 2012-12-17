package org.sireum.bakar.xml

import org.sireum.util._

object FloatingPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FloatingPointDefinition) = {
    Some(
      o.getSloc(),
      o.getDigitsExpressionQ(),
      o.getRealRangeConstraintQ()
    )
  }
}