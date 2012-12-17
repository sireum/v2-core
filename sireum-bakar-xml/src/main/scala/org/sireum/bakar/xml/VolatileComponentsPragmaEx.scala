package org.sireum.bakar.xml

import org.sireum.util._

object VolatileComponentsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.VolatileComponentsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}