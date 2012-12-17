package org.sireum.bakar.xml

import org.sireum.util._

object QualifiedExpressionEx {
  def unapply(o : org.sireum.bakar.xml.QualifiedExpression) = {
    Some(
      o.getSloc(),
      o.getConvertedOrQualifiedSubtypeMarkQ(),
      o.getConvertedOrQualifiedExpressionQ(),
      o.getType()
    )
  }
}