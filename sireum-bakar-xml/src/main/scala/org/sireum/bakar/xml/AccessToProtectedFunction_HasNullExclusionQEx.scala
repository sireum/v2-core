package org.sireum.bakar.xml

import org.sireum.util._

object AccessToProtectedFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProtectedFunction.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}