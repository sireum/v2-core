package org.sireum.bakar.xml

import org.sireum.util._

object LimitedEx {
  def unapply(o : org.sireum.bakar.xml.Limited) = {
    Some(
      o.getSloc()
    )
  }
}