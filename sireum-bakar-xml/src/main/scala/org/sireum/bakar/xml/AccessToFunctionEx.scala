package org.sireum.bakar.xml

import org.sireum.util._

object AccessToFunctionEx {
  def unapply(o : org.sireum.bakar.xml.AccessToFunction) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getAccessToFunctionResultProfileQ()
    )
  }
}