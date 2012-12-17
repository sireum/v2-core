package org.sireum.bakar.xml

import org.sireum.util._

object IfExpressionEx {
  def unapply(o : org.sireum.bakar.xml.IfExpression) = {
    Some(
      o.getSloc(),
      o.getExpressionPathsQl(),
      o.getType()
    )
  }
}