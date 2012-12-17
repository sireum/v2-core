package org.sireum.bakar.xml

import org.sireum.util._

object DefiningAbsOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningAbsOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}