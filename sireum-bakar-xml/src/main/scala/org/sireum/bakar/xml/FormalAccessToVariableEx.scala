package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToVariableEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToVariable) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}