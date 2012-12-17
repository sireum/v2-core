package org.sireum.bakar.xml

import org.sireum.util._

object ReturnVariableSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.ReturnVariableSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ()
    )
  }
}