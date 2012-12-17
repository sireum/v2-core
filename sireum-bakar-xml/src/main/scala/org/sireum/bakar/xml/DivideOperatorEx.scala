package org.sireum.bakar.xml

import org.sireum.util._

object DivideOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DivideOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}