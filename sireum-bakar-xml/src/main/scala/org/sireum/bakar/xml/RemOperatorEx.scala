package org.sireum.bakar.xml

import org.sireum.util._

object RemOperatorEx {
  def unapply(o : org.sireum.bakar.xml.RemOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}