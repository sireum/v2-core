package org.sireum.bakar.xml

import org.sireum.util._

object AbsOperatorEx {
  def unapply(o : org.sireum.bakar.xml.AbsOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}