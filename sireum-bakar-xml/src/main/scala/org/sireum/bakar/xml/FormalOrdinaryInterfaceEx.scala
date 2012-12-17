package org.sireum.bakar.xml

import org.sireum.util._

object FormalOrdinaryInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.FormalOrdinaryInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}