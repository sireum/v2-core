package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToVariableEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToVariable) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAnonymousAccessToObjectSubtypeMarkQ()
    )
  }
}