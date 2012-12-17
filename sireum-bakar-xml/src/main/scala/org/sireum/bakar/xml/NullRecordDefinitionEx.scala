package org.sireum.bakar.xml

import org.sireum.util._

object NullRecordDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.NullRecordDefinition) = {
    Some(
      o.getSloc()
    )
  }
}