package org.sireum.bakar.xml

import org.sireum.util._

object FormalLimitedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.FormalLimitedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}