package org.sireum.bakar.xml

import org.sireum.util._

object AccessToVariableEx {
  def unapply(o : org.sireum.bakar.xml.AccessToVariable) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}