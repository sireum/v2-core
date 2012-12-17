package org.sireum.bakar.xml

import org.sireum.util._

object PrivateTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.PrivateTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}