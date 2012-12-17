package org.sireum.bakar.xml

import org.sireum.util._

object UnknownDiscriminantPartEx {
  def unapply(o : org.sireum.bakar.xml.UnknownDiscriminantPart) = {
    Some(
      o.getSloc()
    )
  }
}