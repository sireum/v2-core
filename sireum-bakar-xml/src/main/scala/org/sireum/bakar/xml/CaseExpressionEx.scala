package org.sireum.bakar.xml

import org.sireum.util._

object CaseExpressionEx {
  def unapply(o : org.sireum.bakar.xml.CaseExpression) = {
    Some(
      o.getSloc(),
      o.getCaseExpressionQ(),
      o.getExpressionPathsQl(),
      o.getType()
    )
  }
}