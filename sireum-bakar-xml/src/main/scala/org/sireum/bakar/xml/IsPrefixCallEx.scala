package org.sireum.bakar.xml

import org.sireum.util._

object IsPrefixCallEx {
  def unapply(o : org.sireum.bakar.xml.IsPrefixCall) = {
    Some(
      o.getSloc()
    )
  }
}