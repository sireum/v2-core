package org.sireum.bakar.xml

import org.sireum.util._

object ElseExpressionPathEx {
  def unapply(o : org.sireum.bakar.xml.ElseExpressionPath) = {
    Some(
      o.getSloc(),
      o.getDependentExpressionQ()
    )
  }
}