package org.sireum.bakar.xml

import org.sireum.util._

object FormalSynchronizedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.FormalSynchronizedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}