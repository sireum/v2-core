package org.sireum.bakar.xml

import org.sireum.util._

object ConcatenateOperatorEx {
  def unapply(o : org.sireum.bakar.xml.ConcatenateOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}