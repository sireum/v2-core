package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToConstantEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToConstant) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}