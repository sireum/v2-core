package org.sireum.bakar.xml

import org.sireum.util._

object KnownDiscriminantPartEx {
  def unapply(o : org.sireum.bakar.xml.KnownDiscriminantPart) = {
    Some(
      o.getSloc(),
      o.getDiscriminantsQl()
    )
  }
}