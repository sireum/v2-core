package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToProtectedFunctionEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProtectedFunction) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getAccessToFunctionResultProfileQ()
    )
  }
}