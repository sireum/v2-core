package org.sireum.bakar.xml

import org.sireum.util._

object ElementIteratorSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.ElementIteratorSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getSubtypeIndicationQ(),
      o.getHasReverseQ(),
      o.getIterationSchemeNameQ()
    )
  }
}