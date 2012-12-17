package org.sireum.bakar.xml

import org.sireum.util._

object DeferredConstantDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.DeferredConstantDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getObjectDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}