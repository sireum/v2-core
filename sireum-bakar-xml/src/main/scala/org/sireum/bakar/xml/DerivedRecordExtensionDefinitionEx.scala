package org.sireum.bakar.xml

import org.sireum.util._

object DerivedRecordExtensionDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DerivedRecordExtensionDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getParentSubtypeIndicationQ(),
      o.getDefinitionInterfaceListQl(),
      o.getRecordDefinitionQ()
    )
  }
}