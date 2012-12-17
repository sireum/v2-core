package org.sireum.bakar.xml

import org.sireum.util._

object ComponentDefinition_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ComponentDefinition.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}