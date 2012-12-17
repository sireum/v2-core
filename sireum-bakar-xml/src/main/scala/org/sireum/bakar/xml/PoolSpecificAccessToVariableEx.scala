package org.sireum.bakar.xml

import org.sireum.util._

object PoolSpecificAccessToVariableEx {
  def unapply(o : org.sireum.bakar.xml.PoolSpecificAccessToVariable) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}