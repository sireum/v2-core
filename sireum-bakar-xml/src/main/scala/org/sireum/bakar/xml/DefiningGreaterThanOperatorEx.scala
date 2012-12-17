package org.sireum.bakar.xml

import org.sireum.util._

object DefiningGreaterThanOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningGreaterThanOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}