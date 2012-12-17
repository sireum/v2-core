package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToProtectedProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProtectedProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}