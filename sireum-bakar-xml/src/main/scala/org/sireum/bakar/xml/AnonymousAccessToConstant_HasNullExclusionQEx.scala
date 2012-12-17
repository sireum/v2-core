package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToConstant_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToConstant.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}