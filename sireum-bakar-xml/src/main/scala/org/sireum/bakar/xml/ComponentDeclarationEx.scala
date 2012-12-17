package org.sireum.bakar.xml

import org.sireum.util._

object ComponentDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ComponentDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ(),
      o.getAspectSpecificationsQl()
    )
  }
}