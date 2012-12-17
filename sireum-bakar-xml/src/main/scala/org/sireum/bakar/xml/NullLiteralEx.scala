package org.sireum.bakar.xml

import org.sireum.util._

object NullLiteralEx {
  def unapply(o : org.sireum.bakar.xml.NullLiteral) = {
    Some(
      o.getSloc(),
      o.getType()
    )
  }
}