package org.sireum.bakar.xml

import org.sireum.util._

object ParameterSpecification_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.ParameterSpecification.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}