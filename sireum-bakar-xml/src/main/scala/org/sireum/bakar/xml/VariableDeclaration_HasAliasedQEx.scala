package org.sireum.bakar.xml

import org.sireum.util._

object VariableDeclaration_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.VariableDeclaration.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}