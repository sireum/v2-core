package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToProtectedFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProtectedFunction.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}