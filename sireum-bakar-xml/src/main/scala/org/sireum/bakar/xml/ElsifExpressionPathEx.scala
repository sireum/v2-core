package org.sireum.bakar.xml

import org.sireum.util._

object ElsifExpressionPathEx {
  def unapply(o : org.sireum.bakar.xml.ElsifExpressionPath) = {
    Some(
      o.getSloc(),
      o.getConditionExpressionQ(),
      o.getDependentExpressionQ()
    )
  }
}