package org.sireum.bakar.xml

import org.sireum.util._

object TaskDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.TaskDefinition) = {
    Some(
      o.getSloc(),
      o.getVisiblePartItemsQl(),
      o.getPrivatePartItemsQl()
    )
  }
}