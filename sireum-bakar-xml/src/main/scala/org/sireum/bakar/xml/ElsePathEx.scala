package org.sireum.bakar.xml

import org.sireum.util._

object ElsePathEx {
  def unapply(o : org.sireum.bakar.xml.ElsePath) = {
    Some(
      o.getSloc(),
      o.getSequenceOfStatementsQl()
    )
  }
}