package org.sireum.bakar.xml

import org.sireum.util._

object DefiningLessThanOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningLessThanOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}