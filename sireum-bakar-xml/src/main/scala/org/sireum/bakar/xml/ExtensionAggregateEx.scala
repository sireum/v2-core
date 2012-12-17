package org.sireum.bakar.xml

import org.sireum.util._

object ExtensionAggregateEx {
  def unapply(o : org.sireum.bakar.xml.ExtensionAggregate) = {
    Some(
      o.getSloc(),
      o.getExtensionAggregateExpressionQ(),
      o.getRecordComponentAssociationsQl(),
      o.getType()
    )
  }
}