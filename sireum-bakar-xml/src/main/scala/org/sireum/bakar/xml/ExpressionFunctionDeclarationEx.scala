package org.sireum.bakar.xml

import org.sireum.util._

object ExpressionFunctionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ExpressionFunctionDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getResultProfileQ(),
      o.getResultExpressionQ(),
      o.getAspectSpecificationsQl()
    )
  }
}