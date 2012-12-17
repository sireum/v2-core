package org.sireum.bakar.xml

import org.sireum.util._

object DefiningXorOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningXorOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}