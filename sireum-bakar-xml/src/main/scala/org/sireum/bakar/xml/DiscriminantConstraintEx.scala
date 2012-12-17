package org.sireum.bakar.xml

import org.sireum.util._

object DiscriminantConstraintEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantConstraint) = {
    Some(
      o.getSloc(),
      o.getDiscriminantAssociationsQl()
    )
  }
}