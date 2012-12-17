package org.sireum.bakar.xml

import org.sireum.util._

object ReverseEx {
  def unapply(o : org.sireum.bakar.xml.Reverse) = {
    Some(
      o.getSloc()
    )
  }
}