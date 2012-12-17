package org.sireum.bakar.xml

import org.sireum.util._

object VariantListEx {
  def unapply(o : org.sireum.bakar.xml.VariantList) = {
    Some(
      o.getVariants()
    )
  }
}