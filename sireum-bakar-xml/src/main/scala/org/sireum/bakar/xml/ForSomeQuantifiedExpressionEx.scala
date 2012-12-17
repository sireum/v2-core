package org.sireum.bakar.xml

import org.sireum.util._

object ForSomeQuantifiedExpressionEx {
  def unapply(o : org.sireum.bakar.xml.ForSomeQuantifiedExpression) = {
    Some(
      o.getSloc(),
      o.getIteratorSpecificationQ(),
      o.getPredicateQ(),
      o.getType()
    )
  }
}