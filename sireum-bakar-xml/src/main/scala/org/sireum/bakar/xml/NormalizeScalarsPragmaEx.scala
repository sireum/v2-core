package org.sireum.bakar.xml

import org.sireum.util._

object NormalizeScalarsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.NormalizeScalarsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}