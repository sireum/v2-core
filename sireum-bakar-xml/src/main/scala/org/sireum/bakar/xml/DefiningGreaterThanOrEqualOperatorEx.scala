package org.sireum.bakar.xml

import org.sireum.util._

object DefiningGreaterThanOrEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningGreaterThanOrEqualOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}