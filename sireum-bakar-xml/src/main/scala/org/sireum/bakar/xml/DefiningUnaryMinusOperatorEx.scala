package org.sireum.bakar.xml

import org.sireum.util._

object DefiningUnaryMinusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningUnaryMinusOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}