package org.sireum.bakar.xml

import org.sireum.util._

object ConstantDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ConstantDeclaration) = {
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