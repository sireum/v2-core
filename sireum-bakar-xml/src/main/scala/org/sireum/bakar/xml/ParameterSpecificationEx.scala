package org.sireum.bakar.xml

import org.sireum.util._

object ParameterSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.ParameterSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getHasNullExclusionQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ(),
      o.getMode()
    )
  }
}