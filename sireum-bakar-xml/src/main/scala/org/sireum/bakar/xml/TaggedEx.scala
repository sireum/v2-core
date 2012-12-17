package org.sireum.bakar.xml

import org.sireum.util._

object TaggedEx {
  def unapply(o : org.sireum.bakar.xml.Tagged) = {
    Some(
      o.getSloc()
    )
  }
}