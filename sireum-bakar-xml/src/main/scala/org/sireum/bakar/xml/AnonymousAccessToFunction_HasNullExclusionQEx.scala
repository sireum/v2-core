package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToFunction.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}