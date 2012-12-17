package org.sireum.bakar.xml

import org.sireum.util._

object AccessToFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToFunction.HasNullExclusionQ) = {
    Some(
      o.getNullExclusion(),
      o.getNotAnElement()
    )
  }
}