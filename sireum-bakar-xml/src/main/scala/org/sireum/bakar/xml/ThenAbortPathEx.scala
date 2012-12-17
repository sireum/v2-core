package org.sireum.bakar.xml

import org.sireum.util._

object ThenAbortPathEx {
  def unapply(o : org.sireum.bakar.xml.ThenAbortPath) = {
    Some(
      o.getSloc(),
      o.getSequenceOfStatementsQl()
    )
  }
}