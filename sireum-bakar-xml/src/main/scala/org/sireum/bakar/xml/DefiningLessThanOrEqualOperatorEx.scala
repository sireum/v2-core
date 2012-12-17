package org.sireum.bakar.xml

import org.sireum.util._

object DefiningLessThanOrEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningLessThanOrEqualOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}