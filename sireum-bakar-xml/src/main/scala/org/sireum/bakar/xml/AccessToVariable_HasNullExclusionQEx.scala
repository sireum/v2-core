package org.sireum.bakar.xml

import org.sireum.util._

object AccessToVariable_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToVariable.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}