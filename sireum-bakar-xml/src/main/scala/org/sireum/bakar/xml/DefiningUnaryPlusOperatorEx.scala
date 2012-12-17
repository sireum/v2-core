package org.sireum.bakar.xml

import org.sireum.util._

object DefiningUnaryPlusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningUnaryPlusOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}