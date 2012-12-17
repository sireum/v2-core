package org.sireum.bakar.xml

import org.sireum.util._

object IfExpressionPathEx {
  def unapply(o : org.sireum.bakar.xml.IfExpressionPath) = {
    Some(
      o.getSloc(),
      o.getConditionExpressionQ(),
      o.getDependentExpressionQ()
    )
  }
}