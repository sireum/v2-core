package org.sireum.bakar.xml

import org.sireum.util._

object AccessToProtectedProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProtectedProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}