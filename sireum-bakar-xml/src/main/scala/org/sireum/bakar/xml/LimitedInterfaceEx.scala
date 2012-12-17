package org.sireum.bakar.xml

import org.sireum.util._

object LimitedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.LimitedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}