package org.sireum.bakar.xml

import org.sireum.util._

object DefiningPlusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningPlusOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}