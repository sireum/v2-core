package org.sireum.bakar.xml

import org.sireum.util._

object ParenthesizedExpressionEx {
  def unapply(o : org.sireum.bakar.xml.ParenthesizedExpression) = {
    Some(
      o.getSloc(),
      o.getExpressionParenthesizedQ(),
      o.getType()
    )
  }
}