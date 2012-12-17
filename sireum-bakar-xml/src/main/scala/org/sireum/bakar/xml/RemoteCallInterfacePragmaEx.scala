package org.sireum.bakar.xml

import org.sireum.util._

object RemoteCallInterfacePragmaEx {
  def unapply(o : org.sireum.bakar.xml.RemoteCallInterfacePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}