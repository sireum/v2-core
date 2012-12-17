package org.sireum.bakar.xml

import org.sireum.util._

object DefiningAndOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningAndOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}