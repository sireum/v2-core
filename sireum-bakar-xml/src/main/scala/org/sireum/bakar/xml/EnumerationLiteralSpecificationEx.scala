package org.sireum.bakar.xml

import org.sireum.util._

object EnumerationLiteralSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.EnumerationLiteralSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl()
    )
  }
}