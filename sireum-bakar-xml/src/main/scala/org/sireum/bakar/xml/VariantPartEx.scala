package org.sireum.bakar.xml

import org.sireum.util._

object VariantPartEx {
  def unapply(o : org.sireum.bakar.xml.VariantPart) = {
    Some(
      o.getSloc(),
      o.getDiscriminantDirectNameQ(),
      o.getVariantsQl()
    )
  }
}