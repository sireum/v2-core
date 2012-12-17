package org.sireum.bakar.xml

import org.sireum.util._

object RealLiteralEx {
  def unapply(o : org.sireum.bakar.xml.RealLiteral) = {
    Some(
      o.getSloc(),
      o.getLitVal(),
      o.getType()
    )
  }
}