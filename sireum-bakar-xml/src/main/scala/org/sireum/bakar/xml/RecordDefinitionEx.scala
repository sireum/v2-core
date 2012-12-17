package org.sireum.bakar.xml

import org.sireum.util._

object RecordDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.RecordDefinition) = {
    Some(
      o.getSloc(),
      o.getHasLimitedQ(),
      o.getRecordComponentsQl()
    )
  }
}