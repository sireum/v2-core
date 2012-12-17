package org.sireum.bakar.xml

import org.sireum.util._

object ExpressionListEx {
  def unapply(o : org.sireum.bakar.xml.ExpressionList) = {
    Some(
      o.getExpressions()
    )
  }
}