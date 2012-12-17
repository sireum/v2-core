package org.sireum.bakar.xml

import org.sireum.util._

object ConstantDeclaration_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ConstantDeclaration.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}