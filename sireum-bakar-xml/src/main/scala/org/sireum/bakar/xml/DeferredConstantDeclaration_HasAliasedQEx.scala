package org.sireum.bakar.xml

import org.sireum.util._

object DeferredConstantDeclaration_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.DeferredConstantDeclaration.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}