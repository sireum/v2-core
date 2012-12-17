package org.sireum.bakar.xml

import org.sireum.util._

object AllocationFromQualifiedExpressionEx {
  def unapply(o : org.sireum.bakar.xml.AllocationFromQualifiedExpression) = {
    Some(
      o.getSloc(),
      o.getSubpoolNameQ(),
      o.getAllocatorQualifiedExpressionQ(),
      o.getType()
    )
  }
}