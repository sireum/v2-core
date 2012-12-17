package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToProtectedProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProtectedProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}