package org.sireum.bakar.xml

import org.sireum.util._

object FormalDerivedTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}