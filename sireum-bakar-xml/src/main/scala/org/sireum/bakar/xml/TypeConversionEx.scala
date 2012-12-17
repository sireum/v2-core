package org.sireum.bakar.xml

import org.sireum.util._

object TypeConversionEx {
  def unapply(o : org.sireum.bakar.xml.TypeConversion) = {
    Some(
      o.getSloc(),
      o.getConvertedOrQualifiedSubtypeMarkQ(),
      o.getConvertedOrQualifiedExpressionQ(),
      o.getType()
    )
  }
}