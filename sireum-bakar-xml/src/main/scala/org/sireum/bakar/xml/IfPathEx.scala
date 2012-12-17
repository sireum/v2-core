package org.sireum.bakar.xml

import org.sireum.util._

object IfPathEx {
  def unapply(o : org.sireum.bakar.xml.IfPath) = {
    Some(
      o.getSloc(),
      o.getConditionExpressionQ(),
      o.getSequenceOfStatementsQl()
    )
  }
}