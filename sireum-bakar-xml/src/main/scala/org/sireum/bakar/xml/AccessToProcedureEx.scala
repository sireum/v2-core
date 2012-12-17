package org.sireum.bakar.xml

import org.sireum.util._

object AccessToProcedureEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}