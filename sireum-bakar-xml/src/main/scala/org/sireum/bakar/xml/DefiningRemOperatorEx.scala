package org.sireum.bakar.xml

import org.sireum.util._

object DefiningRemOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningRemOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}