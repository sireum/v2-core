package org.sireum.bakar.xml

import org.sireum.util._

object ExplicitDereferenceEx {
  def unapply(o : org.sireum.bakar.xml.ExplicitDereference) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getType()
    )
  }
}