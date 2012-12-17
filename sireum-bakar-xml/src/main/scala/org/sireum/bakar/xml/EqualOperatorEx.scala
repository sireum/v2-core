package org.sireum.bakar.xml

import org.sireum.util._

object EqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.EqualOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}