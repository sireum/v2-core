package org.sireum.bakar.xml

import org.sireum.util._

object ComponentDeclaration_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ComponentDeclaration.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}