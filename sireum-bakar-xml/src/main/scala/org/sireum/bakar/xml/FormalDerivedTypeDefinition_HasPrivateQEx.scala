package org.sireum.bakar.xml

import org.sireum.util._

object FormalDerivedTypeDefinition_HasPrivateQEx {
  def unapply(o : org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasPrivateQ) = {
    Some(
      o.getHasPrivate()
    )
  }
}