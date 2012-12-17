package org.sireum.bakar.xml

import org.sireum.util._

object AccessToProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}