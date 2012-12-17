package org.sireum.bakar.xml

import org.sireum.util._

object SynchronizedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.SynchronizedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}