package org.sireum.bakar.xml

import org.sireum.util._

object GeneralizedIteratorSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.GeneralizedIteratorSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasReverseQ(),
      o.getIterationSchemeNameQ()
    )
  }
}