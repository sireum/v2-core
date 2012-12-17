package org.sireum.bakar.xml

import org.sireum.util._

object ReturnConstantSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.ReturnConstantSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ()
    )
  }
}