package org.sireum.bakar.xml

import org.sireum.util._

object DiscriminantSpecificationListEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantSpecificationList) = {
    Some(
      o.getDiscriminantSpecifications()
    )
  }
}