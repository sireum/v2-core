package org.sireum.bakar.xml

import org.sireum.util._

object OverridingEx {
  def unapply(o : org.sireum.bakar.xml.Overriding) = {
    Some(
      o.getSloc()
    )
  }
}