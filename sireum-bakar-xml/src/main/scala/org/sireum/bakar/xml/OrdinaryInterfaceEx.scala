package org.sireum.bakar.xml

import org.sireum.util._

object OrdinaryInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.OrdinaryInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}