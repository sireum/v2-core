package org.sireum.bakar.xml

import org.sireum.util._

object MinusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.MinusOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}