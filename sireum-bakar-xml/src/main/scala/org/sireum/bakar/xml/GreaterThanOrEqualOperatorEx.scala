package org.sireum.bakar.xml

import org.sireum.util._

object GreaterThanOrEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.GreaterThanOrEqualOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}