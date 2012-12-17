package org.sireum.bakar.xml

import org.sireum.util._

object DiscriminantSpecification_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantSpecification.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}