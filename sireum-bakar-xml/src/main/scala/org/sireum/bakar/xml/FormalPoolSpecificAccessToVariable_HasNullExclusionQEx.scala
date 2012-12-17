package org.sireum.bakar.xml

import org.sireum.util._

object FormalPoolSpecificAccessToVariable_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalPoolSpecificAccessToVariable.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}