package org.sireum.bakar.xml

import org.sireum.util._

object AbstractEx {
  def unapply(o : org.sireum.bakar.xml.Abstract) = {
    Some(
      o.getSloc()
    )
  }
}