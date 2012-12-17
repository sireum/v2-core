package org.sireum.bakar.xml

import org.sireum.util._

object DefiningCharacterLiteralEx {
  def unapply(o : org.sireum.bakar.xml.DefiningCharacterLiteral) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}