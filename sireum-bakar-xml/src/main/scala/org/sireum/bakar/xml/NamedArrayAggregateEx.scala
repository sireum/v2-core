package org.sireum.bakar.xml

import org.sireum.util._

object NamedArrayAggregateEx {
  def unapply(o : org.sireum.bakar.xml.NamedArrayAggregate) = {
    Some(
      o.getSloc(),
      o.getArrayComponentAssociationsQl(),
      o.getType()
    )
  }
}