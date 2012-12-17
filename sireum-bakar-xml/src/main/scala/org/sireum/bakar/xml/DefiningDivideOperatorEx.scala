package org.sireum.bakar.xml

import org.sireum.util._

object DefiningDivideOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningDivideOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}