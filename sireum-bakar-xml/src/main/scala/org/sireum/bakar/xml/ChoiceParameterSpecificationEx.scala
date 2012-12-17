package org.sireum.bakar.xml

import org.sireum.util._

object ChoiceParameterSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.ChoiceParameterSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl()
    )
  }
}