package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToProtectedProcedureEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProtectedProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}