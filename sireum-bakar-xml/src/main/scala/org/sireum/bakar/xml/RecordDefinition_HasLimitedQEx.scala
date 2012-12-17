package org.sireum.bakar.xml

import org.sireum.util._

object RecordDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.RecordDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}