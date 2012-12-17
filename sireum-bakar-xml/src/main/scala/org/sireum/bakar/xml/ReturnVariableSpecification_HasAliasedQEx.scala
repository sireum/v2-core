package org.sireum.bakar.xml

import org.sireum.util._

object ReturnVariableSpecification_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ReturnVariableSpecification.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}