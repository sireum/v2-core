package org.sireum.bakar.xml

import org.sireum.util._

object AccessToProtectedProcedureEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProtectedProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}