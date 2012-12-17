package org.sireum.bakar.xml

import org.sireum.util._

object IdentifierEx {
  def unapply(o : org.sireum.bakar.xml.Identifier) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}