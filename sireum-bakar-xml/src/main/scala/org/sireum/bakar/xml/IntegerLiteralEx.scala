package org.sireum.bakar.xml

import org.sireum.util._

object IntegerLiteralEx {
  def unapply(o : org.sireum.bakar.xml.IntegerLiteral) = {
    Some(
      o.getSloc(),
      o.getLitVal(),
      o.getType()
    )
  }
}