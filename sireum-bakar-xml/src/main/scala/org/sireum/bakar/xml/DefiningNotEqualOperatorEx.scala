package org.sireum.bakar.xml

import org.sireum.util._

object DefiningNotEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningNotEqualOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}