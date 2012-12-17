package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}