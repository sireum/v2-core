package org.sireum.bakar.xml

import org.sireum.util._

object NullComponentEx {
  def unapply(o : org.sireum.bakar.xml.NullComponent) = {
    Some(
      o.getSloc()
    )
  }
}