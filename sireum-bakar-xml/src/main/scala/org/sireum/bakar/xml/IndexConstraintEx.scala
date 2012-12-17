package org.sireum.bakar.xml

import org.sireum.util._

object IndexConstraintEx {
  def unapply(o : org.sireum.bakar.xml.IndexConstraint) = {
    Some(
      o.getSloc(),
      o.getDiscreteRangesQl()
    )
  }
}