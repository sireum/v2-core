package org.sireum.bakar.xml

import org.sireum.util._

object VariableDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.VariableDeclaration) = {
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