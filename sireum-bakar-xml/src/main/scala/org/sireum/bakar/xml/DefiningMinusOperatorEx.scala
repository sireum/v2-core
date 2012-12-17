package org.sireum.bakar.xml

import org.sireum.util._

object DefiningMinusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningMinusOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}