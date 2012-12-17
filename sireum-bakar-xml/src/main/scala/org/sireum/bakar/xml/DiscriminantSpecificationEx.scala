package org.sireum.bakar.xml

import org.sireum.util._

object DiscriminantSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasNullExclusionQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ()
    )
  }
}