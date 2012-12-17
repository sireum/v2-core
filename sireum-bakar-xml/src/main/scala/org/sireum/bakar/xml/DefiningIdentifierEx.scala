package org.sireum.bakar.xml

import org.sireum.util._

object DefiningIdentifierEx {
  def unapply(o : org.sireum.bakar.xml.DefiningIdentifier) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}