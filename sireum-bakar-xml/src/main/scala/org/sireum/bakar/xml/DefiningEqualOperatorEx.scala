package org.sireum.bakar.xml

import org.sireum.util._

object DefiningEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningEqualOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}