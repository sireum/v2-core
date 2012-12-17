package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToProtectedFunctionEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProtectedFunction) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getAccessToFunctionResultProfileQ()
    )
  }
}