package org.sireum.bakar.xml

import org.sireum.util._

object TaskInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.TaskInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}