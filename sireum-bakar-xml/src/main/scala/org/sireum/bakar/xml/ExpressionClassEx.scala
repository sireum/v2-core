package org.sireum.bakar.xml

import org.sireum.util._

object ExpressionClassEx {
  def unapply(o : org.sireum.bakar.xml.ExpressionClass) = {
    Some(
      o.getExpression()
    )
  }
}