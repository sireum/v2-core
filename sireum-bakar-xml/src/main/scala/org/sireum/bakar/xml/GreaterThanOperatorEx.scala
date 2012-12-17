package org.sireum.bakar.xml

import org.sireum.util._

object GreaterThanOperatorEx {
  def unapply(o : org.sireum.bakar.xml.GreaterThanOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}