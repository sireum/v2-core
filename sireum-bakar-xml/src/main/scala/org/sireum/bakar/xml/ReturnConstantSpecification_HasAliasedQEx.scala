package org.sireum.bakar.xml

import org.sireum.util._

object ReturnConstantSpecification_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ReturnConstantSpecification.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}