package org.sireum.bakar.xml

import org.sireum.util._

object DiscreteSimpleExpressionRangeAsSubtypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteSimpleExpressionRangeAsSubtypeDefinition) = {
    Some(
      o.getSloc(),
      o.getLowerBoundQ(),
      o.getUpperBoundQ()
    )
  }
}