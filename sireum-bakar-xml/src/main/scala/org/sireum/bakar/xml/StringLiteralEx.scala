package org.sireum.bakar.xml

import org.sireum.util._

object StringLiteralEx {
  def unapply(o : org.sireum.bakar.xml.StringLiteral) = {
    Some(
      o.getSloc(),
      o.getLitVal(),
      o.getType()
    )
  }
}