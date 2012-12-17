package org.sireum.bakar.xml

import org.sireum.util._

object AccessToConstant_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToConstant.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}