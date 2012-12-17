package org.sireum.bakar.xml

import org.sireum.util._

object ProtectedDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.ProtectedDefinition) = {
    Some(
      o.getSloc(),
      o.getVisiblePartItemsQl(),
      o.getPrivatePartItemsQl()
    )
  }
}