package org.sireum.bakar.xml

import org.sireum.util._

object NullExclusionEx {
  def unapply(o : org.sireum.bakar.xml.NullExclusion) = {
    Some(
      o.getSloc()
    )
  }
}