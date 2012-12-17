package org.sireum.bakar.xml

import org.sireum.util._

object DefiningNotOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningNotOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}