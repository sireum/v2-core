package org.sireum.bakar.xml

import org.sireum.util._

object OrOperatorEx {
  def unapply(o : org.sireum.bakar.xml.OrOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}