package org.sireum.bakar.xml

import org.sireum.util._

object XorOperatorEx {
  def unapply(o : org.sireum.bakar.xml.XorOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}