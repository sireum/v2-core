package org.sireum.bakar.xml

import org.sireum.util._

object SuppressPragmaEx {
  def unapply(o : org.sireum.bakar.xml.SuppressPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}