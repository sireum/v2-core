package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToProcedureEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}