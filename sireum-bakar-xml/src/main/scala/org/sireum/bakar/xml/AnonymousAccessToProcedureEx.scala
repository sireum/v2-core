package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToProcedureEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}