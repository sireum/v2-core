package org.sireum.bakar.xml

import org.sireum.util._

object NotAnElementEx {
  def unapply(o : org.sireum.bakar.xml.NotAnElement) = {
    Some(
      o.getSloc()
    )
  }
}