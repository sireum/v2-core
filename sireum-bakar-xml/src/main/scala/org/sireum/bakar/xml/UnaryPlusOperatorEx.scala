package org.sireum.bakar.xml

import org.sireum.util._

object UnaryPlusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.UnaryPlusOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}