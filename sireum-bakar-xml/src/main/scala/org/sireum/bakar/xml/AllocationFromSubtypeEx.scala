package org.sireum.bakar.xml

import org.sireum.util._

object AllocationFromSubtypeEx {
  def unapply(o : org.sireum.bakar.xml.AllocationFromSubtype) = {
    Some(
      o.getSloc(),
      o.getSubpoolNameQ(),
      o.getAllocatorSubtypeIndicationQ(),
      o.getType()
    )
  }
}