package org.sireum.bakar.xml

import org.sireum.util._

object DerivedTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.DerivedTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}