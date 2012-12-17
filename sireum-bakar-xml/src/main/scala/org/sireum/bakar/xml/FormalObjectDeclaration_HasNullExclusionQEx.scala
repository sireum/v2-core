package org.sireum.bakar.xml

import org.sireum.util._

object FormalObjectDeclaration_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalObjectDeclaration.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}