package org.sireum.bakar.xml

import org.sireum.util._

object RecordTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.RecordTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getRecordDefinitionQ()
    )
  }
}