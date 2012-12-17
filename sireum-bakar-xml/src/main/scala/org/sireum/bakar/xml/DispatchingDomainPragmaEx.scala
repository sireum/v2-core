package org.sireum.bakar.xml

import org.sireum.util._

object DispatchingDomainPragmaEx {
  def unapply(o : org.sireum.bakar.xml.DispatchingDomainPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}