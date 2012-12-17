package org.sireum.bakar.xml

import org.sireum.util._

object FormalPrivateTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FormalPrivateTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}