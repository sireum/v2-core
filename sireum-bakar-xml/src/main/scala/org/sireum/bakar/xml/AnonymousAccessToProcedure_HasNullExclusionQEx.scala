package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}