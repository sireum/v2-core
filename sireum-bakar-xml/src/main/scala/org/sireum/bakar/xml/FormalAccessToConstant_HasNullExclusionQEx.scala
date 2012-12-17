package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToConstant_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToConstant.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}