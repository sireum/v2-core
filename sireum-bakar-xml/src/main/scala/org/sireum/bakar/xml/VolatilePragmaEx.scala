package org.sireum.bakar.xml

import org.sireum.util._

object VolatilePragmaEx {
  def unapply(o : org.sireum.bakar.xml.VolatilePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}