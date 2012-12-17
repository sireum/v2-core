package org.sireum.bakar.xml

import org.sireum.util._

object RecordAggregateEx {
  def unapply(o : org.sireum.bakar.xml.RecordAggregate) = {
    Some(
      o.getSloc(),
      o.getRecordComponentAssociationsQl(),
      o.getType()
    )
  }
}