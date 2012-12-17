package org.sireum.bakar.xml

import org.sireum.util._

object PositionalArrayAggregateEx {
  def unapply(o : org.sireum.bakar.xml.PositionalArrayAggregate) = {
    Some(
      o.getSloc(),
      o.getArrayComponentAssociationsQl(),
      o.getType()
    )
  }
}