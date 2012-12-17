package org.sireum.bakar.xml

import org.sireum.util._

object ParameterSpecificationListEx {
  def unapply(o : org.sireum.bakar.xml.ParameterSpecificationList) = {
    Some(
      o.getParameterSpecifications()
    )
  }
}