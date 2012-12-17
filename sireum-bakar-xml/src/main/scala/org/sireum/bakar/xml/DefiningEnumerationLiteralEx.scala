package org.sireum.bakar.xml

import org.sireum.util._

object DefiningEnumerationLiteralEx {
  def unapply(o : org.sireum.bakar.xml.DefiningEnumerationLiteral) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}