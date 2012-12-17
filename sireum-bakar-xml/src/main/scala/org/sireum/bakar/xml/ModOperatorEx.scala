package org.sireum.bakar.xml

import org.sireum.util._

object ModOperatorEx {
  def unapply(o : org.sireum.bakar.xml.ModOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}