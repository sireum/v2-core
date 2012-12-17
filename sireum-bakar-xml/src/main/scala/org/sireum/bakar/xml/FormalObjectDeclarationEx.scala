package org.sireum.bakar.xml

import org.sireum.util._

object FormalObjectDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalObjectDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasNullExclusionQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ(),
      o.getAspectSpecificationsQl(),
      o.getMode()
    )
  }
}