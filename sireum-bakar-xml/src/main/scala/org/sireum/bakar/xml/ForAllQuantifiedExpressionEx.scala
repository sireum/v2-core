package org.sireum.bakar.xml

import org.sireum.util._

object ForAllQuantifiedExpressionEx {
  def unapply(o : org.sireum.bakar.xml.ForAllQuantifiedExpression) = {
    Some(
      o.getSloc(),
      o.getIteratorSpecificationQ(),
      o.getPredicateQ(),
      o.getType()
    )
  }
}