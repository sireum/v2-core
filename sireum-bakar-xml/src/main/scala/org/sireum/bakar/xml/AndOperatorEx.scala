package org.sireum.bakar.xml

import org.sireum.util._

object AndOperatorEx {
  def unapply(o : org.sireum.bakar.xml.AndOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}