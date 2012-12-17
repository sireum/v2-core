package org.sireum.bakar.xml

import org.sireum.util._

object SubtypeIndication_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.SubtypeIndication.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}