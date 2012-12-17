package org.sireum.bakar.xml

import org.sireum.util._

object LoopParameterSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.LoopParameterSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasReverseQ(),
      o.getSpecificationSubtypeDefinitionQ()
    )
  }
}