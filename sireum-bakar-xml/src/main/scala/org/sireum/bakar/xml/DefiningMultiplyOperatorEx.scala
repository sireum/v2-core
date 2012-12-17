package org.sireum.bakar.xml

import org.sireum.util._

object DefiningMultiplyOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningMultiplyOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}