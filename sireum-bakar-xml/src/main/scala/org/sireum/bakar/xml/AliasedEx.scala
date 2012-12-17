package org.sireum.bakar.xml

import org.sireum.util._

object AliasedEx {
  def unapply(o : org.sireum.bakar.xml.Aliased) = {
    Some(
      o.getSloc()
    )
  }
}