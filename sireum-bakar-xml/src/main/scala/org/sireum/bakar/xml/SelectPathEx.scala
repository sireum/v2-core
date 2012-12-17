package org.sireum.bakar.xml

import org.sireum.util._

object SelectPathEx {
  def unapply(o : org.sireum.bakar.xml.SelectPath) = {
    Some(
      o.getSloc(),
      o.getGuardQ(),
      o.getSequenceOfStatementsQl()
    )
  }
}