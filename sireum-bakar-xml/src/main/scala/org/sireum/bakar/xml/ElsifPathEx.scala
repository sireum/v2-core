package org.sireum.bakar.xml

import org.sireum.util._

object ElsifPathEx {
  def unapply(o : org.sireum.bakar.xml.ElsifPath) = {
    Some(
      o.getSloc(),
      o.getConditionExpressionQ(),
      o.getSequenceOfStatementsQl()
    )
  }
}