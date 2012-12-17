package org.sireum.bakar.xml

import org.sireum.util._

object OrPathEx {
  def unapply(o : org.sireum.bakar.xml.OrPath) = {
    Some(
      o.getSloc(),
      o.getGuardQ(),
      o.getSequenceOfStatementsQl()
    )
  }
}