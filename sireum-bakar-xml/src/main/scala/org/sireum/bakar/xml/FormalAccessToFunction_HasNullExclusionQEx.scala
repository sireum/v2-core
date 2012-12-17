package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToFunction.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}