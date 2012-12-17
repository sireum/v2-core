package org.sireum.bakar.xml

import org.sireum.util._

object AccessToConstantEx {
  def unapply(o : org.sireum.bakar.xml.AccessToConstant) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}