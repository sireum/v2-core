package org.sireum.bakar.xml

import org.sireum.util._

object DiscardNamesPragmaEx {
  def unapply(o : org.sireum.bakar.xml.DiscardNamesPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}