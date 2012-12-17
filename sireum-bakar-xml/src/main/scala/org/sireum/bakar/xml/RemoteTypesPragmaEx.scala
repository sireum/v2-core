package org.sireum.bakar.xml

import org.sireum.util._

object RemoteTypesPragmaEx {
  def unapply(o : org.sireum.bakar.xml.RemoteTypesPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}