package org.sireum.bakar.xml

import org.sireum.util._

object DefiningModOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningModOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}