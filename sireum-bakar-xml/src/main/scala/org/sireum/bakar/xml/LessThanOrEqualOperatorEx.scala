package org.sireum.bakar.xml

import org.sireum.util._

object LessThanOrEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.LessThanOrEqualOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}