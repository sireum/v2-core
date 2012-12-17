package org.sireum.bakar.xml

import org.sireum.util._

object EnumerationLiteralEx {
  def unapply(o : org.sireum.bakar.xml.EnumerationLiteral) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}