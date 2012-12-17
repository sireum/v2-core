package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToFunctionEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToFunction) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getAccessToFunctionResultProfileQ()
    )
  }
}