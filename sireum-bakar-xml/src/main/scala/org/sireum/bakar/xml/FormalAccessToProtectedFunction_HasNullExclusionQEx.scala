package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToProtectedFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProtectedFunction.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}