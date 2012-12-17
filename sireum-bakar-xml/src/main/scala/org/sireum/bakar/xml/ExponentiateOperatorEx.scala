package org.sireum.bakar.xml

import org.sireum.util._

object ExponentiateOperatorEx {
  def unapply(o : org.sireum.bakar.xml.ExponentiateOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}