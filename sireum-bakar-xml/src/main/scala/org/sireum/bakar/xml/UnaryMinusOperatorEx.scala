package org.sireum.bakar.xml

import org.sireum.util._

object UnaryMinusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.UnaryMinusOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}