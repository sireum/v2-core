package org.sireum.bakar.xml

import org.sireum.util._

object EntryIndexSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.EntryIndexSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getSpecificationSubtypeDefinitionQ()
    )
  }
}