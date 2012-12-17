package org.sireum.bakar.xml

import org.sireum.util._

object UnsuppressPragmaEx {
  def unapply(o : org.sireum.bakar.xml.UnsuppressPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}