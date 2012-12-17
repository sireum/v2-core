package org.sireum.bakar.xml

import org.sireum.util._

object DefiningConcatenateOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningConcatenateOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}