package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToConstantEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToConstant) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAnonymousAccessToObjectSubtypeMarkQ()
    )
  }
}