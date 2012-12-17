package org.sireum.bakar.xml

import org.sireum.util._

object AspectSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.AspectSpecification) = {
    Some(
      o.getSloc(),
      o.getAspectMarkQ(),
      o.getAspectDefinitionQ()
    )
  }
}