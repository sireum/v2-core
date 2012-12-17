package org.sireum.bakar.xml

import org.sireum.util._

object DiscreteSubtypeIndicationEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteSubtypeIndication) = {
    Some(
      o.getSloc(),
      o.getSubtypeMarkQ(),
      o.getSubtypeConstraintQ()
    )
  }
}