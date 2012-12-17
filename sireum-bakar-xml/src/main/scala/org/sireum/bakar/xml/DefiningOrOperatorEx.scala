package org.sireum.bakar.xml

import org.sireum.util._

object DefiningOrOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningOrOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}