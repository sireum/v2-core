package org.sireum.bakar.xml

import org.sireum.util._

object MultiplyOperatorEx {
  def unapply(o : org.sireum.bakar.xml.MultiplyOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}