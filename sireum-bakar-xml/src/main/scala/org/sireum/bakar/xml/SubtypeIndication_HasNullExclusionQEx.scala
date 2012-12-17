package org.sireum.bakar.xml

import org.sireum.util._

object SubtypeIndication_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.SubtypeIndication.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}