package org.sireum.bakar.xml

import org.sireum.util._

object FormalProtectedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.FormalProtectedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}