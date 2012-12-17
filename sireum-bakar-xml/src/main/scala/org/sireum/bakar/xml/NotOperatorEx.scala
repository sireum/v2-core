package org.sireum.bakar.xml

import org.sireum.util._

object NotOperatorEx {
  def unapply(o : org.sireum.bakar.xml.NotOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}