package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToProtectedProcedureEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProtectedProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}