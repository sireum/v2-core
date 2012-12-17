package org.sireum.bakar.xml

import org.sireum.util._

object FormalTaskInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.FormalTaskInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}