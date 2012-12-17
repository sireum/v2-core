package org.sireum.bakar.xml

import org.sireum.util._

object SubtypeIndicationEx {
  def unapply(o : org.sireum.bakar.xml.SubtypeIndication) = {
    Some(
      o.getSloc(),
      o.getHasAliasedQ(),
      o.getHasNullExclusionQ(),
      o.getSubtypeMarkQ(),
      o.getSubtypeConstraintQ()
    )
  }
}