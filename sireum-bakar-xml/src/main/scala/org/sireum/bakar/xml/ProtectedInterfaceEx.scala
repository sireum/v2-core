package org.sireum.bakar.xml

import org.sireum.util._

object ProtectedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.ProtectedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}