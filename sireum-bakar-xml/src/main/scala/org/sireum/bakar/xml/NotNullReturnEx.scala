package org.sireum.bakar.xml

import org.sireum.util._

object NotNullReturnEx {
  def unapply(o : org.sireum.bakar.xml.NotNullReturn) = {
    Some(
      o.getSloc()
    )
  }
}