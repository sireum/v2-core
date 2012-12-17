package org.sireum.bakar.xml

import org.sireum.util._

object NotEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.NotEqualOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}