package org.sireum.bakar.xml

import org.sireum.util._

object VariantEx {
  def unapply(o : org.sireum.bakar.xml.Variant) = {
    Some(
      o.getSloc(),
      o.getVariantChoicesQl(),
      o.getRecordComponentsQl()
    )
  }
}