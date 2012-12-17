package org.sireum.bakar.xml

import org.sireum.util._

object FormalPoolSpecificAccessToVariableEx {
  def unapply(o : org.sireum.bakar.xml.FormalPoolSpecificAccessToVariable) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}